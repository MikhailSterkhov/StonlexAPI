package ru.stonlex.bukkit.protocollib.entity;

import com.comphenix.protocol.reflect.accessors.Accessors;
import com.comphenix.protocol.reflect.accessors.FieldAccessor;
import com.comphenix.protocol.utility.MinecraftProtocolVersion;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.utility.MinecraftVersion;
import com.comphenix.protocol.wrappers.Vector3F;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import ru.stonlex.bukkit.protocollib.entity.equipment.FakeEntityEquipment;
import ru.stonlex.bukkit.protocollib.packet.AbstractPacket;
import ru.stonlex.bukkit.protocollib.packet.ProtocolPacketFactory;
import ru.stonlex.bukkit.protocollib.packet.entity.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Getter
public abstract class FakeBaseEntity implements Cloneable, FakeEntityClickable {

    public static @NonNull WrappedDataWatcher.Serializer BYTE_SERIALIZER                = WrappedDataWatcher.Registry.get(Byte.class);
    public static @NonNull WrappedDataWatcher.Serializer FLOAT_SERIALIZER               = WrappedDataWatcher.Registry.get(Float.class);
    public static @NonNull WrappedDataWatcher.Serializer INT_SERIALIZER                 = WrappedDataWatcher.Registry.get(Integer.class);
    public static @NonNull WrappedDataWatcher.Serializer STRING_SERIALIZER              = WrappedDataWatcher.Registry.get(String.class);
    public static @NonNull WrappedDataWatcher.Serializer BOOLEAN_SERIALIZER             = WrappedDataWatcher.Registry.get(Boolean.class);

    public static @NonNull WrappedDataWatcher.Serializer CHAT_COMPONENT_SERIALIZER      = WrappedDataWatcher.Registry.getChatComponentSerializer();

    public static @NonNull WrappedDataWatcher.Serializer ITEMSTACK_SERIALIZER           = WrappedDataWatcher.Registry.get(MinecraftReflection.getItemStackClass());
    public static @NonNull WrappedDataWatcher.Serializer ROTATION_SERIALIZER            = WrappedDataWatcher.Registry.get(Vector3F.getMinecraftClass());

    public static @NonNull FieldAccessor ENTITY_ID_ACCESSOR                             = Accessors.getFieldAccessor(MinecraftReflection.getEntityClass(), "entityCount", true);


    protected @NonNull int entityId;
    protected @NonNull int spawnTypeId;

    protected @NonNull EntityType entityType;

    protected FakeEntityScope entityScope;

    protected String customName;
    protected Location location;

    protected ChatColor glowingColor;

    protected @NonNull FakeEntityEquipment entityEquipment      = new FakeEntityEquipment(this);
    protected @NonNull WrappedDataWatcher dataWatcher           = new WrappedDataWatcher();

    protected @NonNull Collection<Player> viewerCollection      = new LinkedHashSet<>();
    protected @NonNull Collection<Player> receiverCollection    = new LinkedHashSet<>();

    protected boolean burning;
    protected boolean sneaking;
    protected boolean sprinting;
    protected boolean invisible;
    protected boolean elytraFlying;
    protected boolean customNameVisible;
    protected boolean noGravity;

    @Setter protected Consumer<Player> clickAction;
    @Setter protected Consumer<Player> attackAction;


    public FakeBaseEntity(int entityId, @NonNull EntityType entityType, @NonNull Location location, @NonNull FakeEntityScope entityScope) {
        this.entityId = entityId;
        this.entityType = entityType;

        this.location = location;
        this.entityScope = entityScope;

        FakeEntityRegistry.INSTANCE.registerEntity(this);
    }

    public FakeBaseEntity(@NonNull EntityType entityType, @NonNull Location location, @NonNull FakeEntityScope entityScope) {
        this(ENTITY_ID_ACCESSOR.get(null) instanceof AtomicInteger ? ((AtomicInteger) ENTITY_ID_ACCESSOR.get(null)).incrementAndGet() : ((int) ENTITY_ID_ACCESSOR.get(null)),
                entityType, location, entityScope);

        // 1.13+ using final AtomicInteger
        if (!(ENTITY_ID_ACCESSOR.get(null) instanceof AtomicInteger)) {
            ENTITY_ID_ACCESSOR.set(null, entityId + 1);
        }
    }

    public FakeBaseEntity(@NonNull EntityType entityType, @NonNull Location location) {
        this(entityType, location, FakeEntityScope.PROTOTYPE);
    }


    public synchronized void setEntityScope(@NonNull FakeEntityScope entityScope) {
        this.entityScope = entityScope;
    }

    public synchronized void spawn() {
        spawn(true);
    }

    public synchronized void spawn(boolean isPublic) {
        if (isPublic) setEntityScope(FakeEntityScope.PUBLIC);

        FakeEntityRegistry.INSTANCE.registerEntity(this);
        addReceivers(Bukkit.getOnlinePlayers().toArray(new Player[0]));
    }

    public synchronized void remove() {
        setEntityScope(FakeEntityScope.PROTOTYPE);

        FakeEntityRegistry.INSTANCE.unregisterEntity(this);
        removeReceivers(receiverCollection.toArray(new Player[0]));
    }

    public synchronized boolean hasReceiver(@NonNull Player player) {
        return player.isOnline() && receiverCollection.stream()
                .map(Player::getName)
                .anyMatch(playerName -> playerName.equalsIgnoreCase(player.getName()));
    }

    public synchronized void addReceivers(@NonNull Player... players) {
        receiverCollection.addAll(Arrays.asList(players));

        addViewers(players);
    }

    public synchronized void removeReceivers(@NonNull Player... players) {
        receiverCollection.removeAll(Arrays.asList(players));

        removeViewers(players);
    }

    public synchronized boolean hasViewer(@NonNull Player player) {
        return player.isOnline() && viewerCollection.stream()
                .map(Player::getName)

                .anyMatch(playerName -> playerName.equalsIgnoreCase(player.getName()));
    }

    public synchronized void addViewers(@NonNull Player... players) {
        for (Player player : players) {
            viewerCollection.add(player);

            sendSpawnPackets(player);
            sendDataWatcherPacket(player);

            entityEquipment.updateEquipmentPacket(player);
        }
    }

    public synchronized void removeViewers(@NonNull Player... players) {
        for (Player player : players) {

            sendDestroyPackets(player);
            viewerCollection.remove(player);
        }
    }


    public synchronized void sendSpawnPackets(@NonNull Player player) {
        for (AbstractPacket abstractPacket : getSpawnPackets())
            abstractPacket.sendPacket(player);


        int aquaticVersion = MinecraftProtocolVersion.getVersion(MinecraftVersion.AQUATIC_UPDATE);
        int currentVersion = MinecraftProtocolVersion.getCurrentVersion();

        if (currentVersion >= aquaticVersion) {
            sendDataWatcherPacket(player);
        }
    }

    public synchronized void sendDestroyPackets(@NonNull Player player) {
        for (AbstractPacket abstractPacket : getDestroyPackets())
            abstractPacket.sendPacket(player);
    }


    public synchronized Collection<AbstractPacket> getSpawnPackets() {
        return Collections.singletonList(
                ProtocolPacketFactory.createSpawnEntityPacket(entityId, getSpawnTypeId(), entityType, location));
    }

    public synchronized Collection<AbstractPacket> getDestroyPackets() {
        return Collections.singletonList(
                ProtocolPacketFactory.createEntityDestroyPacket(entityId));
    }

    public synchronized void teleport(@NonNull Location location) {
        this.location = location;

        viewerCollection.forEach(this::sendTeleportPacket);
    }

    public synchronized void look(@NonNull Player player, @NonNull Location location) {
        Vector vector = location.clone().subtract(this.location).toVector().normalize();

        this.location.setDirection(vector);

        location.setYaw(this.location.getYaw());
        location.setPitch(this.location.getPitch());

        sendEntityLookPacket(player);
        sendHeadRotationPacket(player);
    }

    public synchronized void look(@NonNull Player player) {
        look(player, player.getLocation());
    }

    public synchronized void look(@NonNull Location location) {
        viewerCollection.forEach(player -> look(player, location));
    }

    public synchronized void look(@NonNull Player player, float yaw, float pitch) {
        location.setYaw(yaw);
        location.setPitch(pitch);

        sendEntityLookPacket(player);
        sendHeadRotationPacket(player);
    }

    public synchronized void look(float yaw, float pitch) {
        location.setYaw(yaw);
        location.setPitch(pitch);

        for (Player receiver : getViewerCollection()) {
            sendEntityLookPacket(receiver);
            sendHeadRotationPacket(receiver);
        }
    }

    public synchronized void setPassengers(int... entityIds) {
        if (viewerCollection.isEmpty()) {
            return;
        }

        WrapperPlayServerMount mountPacket = new WrapperPlayServerMount();

        mountPacket.setEntityID(entityId);
        mountPacket.setPassengerIds(entityIds);

        viewerCollection.forEach(mountPacket::sendPacket);
    }

    public synchronized void setPassengers(FakeBaseEntity... fakeBaseEntities) {
        int[] entityIds = Arrays.stream(fakeBaseEntities).mapToInt(FakeBaseEntity::getEntityId).toArray();

        setPassengers(entityIds);
    }

    public synchronized void setVelocity(@NonNull Vector vector) {
        WrapperPlayServerEntityVelocity velocityPacket = new WrapperPlayServerEntityVelocity();
        velocityPacket.setEntityID(entityId);

        velocityPacket.setVelocityX(vector.getX());
        velocityPacket.setVelocityX(vector.getY());
        velocityPacket.setVelocityX(vector.getZ());

        viewerCollection.forEach(velocityPacket::sendPacket);
    }

    public synchronized void setVelocity(@NonNull Player player, @NonNull Vector vector) {
        WrapperPlayServerEntityVelocity velocityPacket = new WrapperPlayServerEntityVelocity();
        velocityPacket.setEntityID(entityId);

        velocityPacket.setVelocityX(vector.getX());
        velocityPacket.setVelocityX(vector.getY());
        velocityPacket.setVelocityX(vector.getZ());

        velocityPacket.sendPacket(player);
    }

    protected synchronized void sendTeleportPacket(@NonNull Player player) {
        WrapperPlayServerEntityTeleport teleportPacket = new WrapperPlayServerEntityTeleport();

        teleportPacket.setEntityID(entityId);

        teleportPacket.setX(location.getX());
        teleportPacket.setY(location.getY());
        teleportPacket.setZ(location.getZ());

        teleportPacket.setYaw(location.getYaw());
        teleportPacket.setPitch(location.getPitch());

        teleportPacket.sendPacket(player);
    }

    protected synchronized void sendEntityLookPacket(@NonNull Player player) {
        WrapperPlayServerEntityLook entityLookPacket = new WrapperPlayServerEntityLook();

        entityLookPacket.setEntityID(entityId);
        entityLookPacket.setYaw(location.getYaw());
        entityLookPacket.setPitch(location.getPitch());

        entityLookPacket.sendPacket(player);
    }

    protected synchronized void sendHeadRotationPacket(@NonNull Player player) {
        WrapperPlayServerEntityHeadRotation headRotation = new WrapperPlayServerEntityHeadRotation();

        headRotation.setEntityID(entityId);
        headRotation.setHeadYaw((byte) (location.getYaw() * 256.0F / 360.0F));

        headRotation.sendPacket(player);
    }

    protected synchronized void sendDataWatcherPacket(@NonNull Player player) {
        if (!player.isOnline())
            return;

        ProtocolPacketFactory.createEntityMetadataPacket(entityId, dataWatcher)
                .sendPacket(player);
    }

    protected synchronized void broadcastDataWatcherPacket() {
        if (viewerCollection.isEmpty()) {
            return;
        }

        WrapperPlayServerEntityMetadata entityMetadataPacket
                = ProtocolPacketFactory.createEntityMetadataPacket(entityId, dataWatcher);

        viewerCollection.forEach(entityMetadataPacket::sendPacket);
    }

    protected synchronized void broadcastDataWatcherObject(int dataWatcherIndex,
                                                           @NonNull WrappedDataWatcher.Serializer serializer, @NonNull Object value) {

        WrappedDataWatcher.WrappedDataWatcherObject wrappedDataWatcherObject
                = new WrappedDataWatcher.WrappedDataWatcherObject(dataWatcherIndex, serializer);

        getDataWatcher().setObject(wrappedDataWatcherObject, value);
        broadcastDataWatcherPacket();
    }


    public synchronized void setBurning(boolean burning) {
        if (this.burning == burning) {
            return;
        }

        this.burning = burning;
        broadcastDataWatcherObject(0, BYTE_SERIALIZER, generateBitMask());
    }

    public synchronized void setSneaking(boolean sneaking) {
        if (this.sneaking == sneaking) {
            return;
        }

        this.sneaking = sneaking;
        broadcastDataWatcherObject(0, BYTE_SERIALIZER, generateBitMask());
    }

    public void setSprinting(boolean sprinting) {
        if (this.sprinting == sprinting) {
            return;
        }

        this.sprinting = sprinting;
        broadcastDataWatcherObject(0, BYTE_SERIALIZER, generateBitMask());
    }

    public synchronized void setInvisible(boolean invisible) {
        if (this.invisible == invisible) {
            return;
        }

        this.invisible = invisible;
        broadcastDataWatcherObject(0, BYTE_SERIALIZER, generateBitMask());
    }

    public synchronized void setElytraFlying(boolean elytraFlying) {
        if (this.elytraFlying == elytraFlying) {
            return;
        }

        this.elytraFlying = elytraFlying;
        broadcastDataWatcherObject(0, BYTE_SERIALIZER, generateBitMask());
    }

    public synchronized void setGlowingColor(@NonNull ChatColor glowingColor) {
        if (this.glowingColor == glowingColor) {
            return;
        }

        this.glowingColor = glowingColor;
        broadcastDataWatcherObject(0, BYTE_SERIALIZER, generateBitMask());
    }

    public synchronized void setCustomNameVisible(boolean customNameVisible) {
        if (this.customNameVisible == customNameVisible) {
            return;
        }

        this.customNameVisible = customNameVisible;
        broadcastDataWatcherObject(3, BOOLEAN_SERIALIZER, customNameVisible);
    }

    public synchronized void setNoGravity(boolean noGravity) {
        if (this.noGravity == noGravity) {
            return;
        }

        this.noGravity = noGravity;
        broadcastDataWatcherObject(5, BOOLEAN_SERIALIZER, noGravity);
    }

    public synchronized void setCustomName(@NonNull String customName) {
        if (this.customName != null && this.customName.equals(customName)) {
            return;
        }

        this.customName = customName;

        int aquaticVersion = MinecraftProtocolVersion.getVersion(MinecraftVersion.AQUATIC_UPDATE);
        int currentVersion = MinecraftProtocolVersion.getCurrentVersion();

        if (currentVersion < aquaticVersion) {
            broadcastDataWatcherObject(2, STRING_SERIALIZER, customName);

        } else {

            broadcastDataWatcherObject(2, CHAT_COMPONENT_SERIALIZER, WrappedChatComponent.fromText(customName));
        }
    }


    public synchronized void setCustomName(@NonNull String customName,
                                           @NonNull Player player) {

        // update entity metadata objects
        WrappedDataWatcher wrappedDataWatcher = new WrappedDataWatcher();
        wrappedDataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, BOOLEAN_SERIALIZER), true);

        int aquaticVersion = MinecraftProtocolVersion.getVersion(MinecraftVersion.AQUATIC_UPDATE);
        int currentVersion = MinecraftProtocolVersion.getCurrentVersion();

        if (currentVersion < aquaticVersion) {
            wrappedDataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, STRING_SERIALIZER), customName);

        } else {

            wrappedDataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, CHAT_COMPONENT_SERIALIZER), WrappedChatComponent.fromText(customName));
        }


        // send entity metadata packet
        WrapperPlayServerEntityMetadata entityMetadataPacket = new WrapperPlayServerEntityMetadata();

        entityMetadataPacket.setEntityID(entityId);
        entityMetadataPacket.setMetadata(wrappedDataWatcher.getWatchableObjects());

        entityMetadataPacket.sendPacket(player);
    }

    @SneakyThrows
    public synchronized FakeBaseEntity clone() {
        return (FakeBaseEntity) super.clone();
    }

    private synchronized byte generateBitMask() {
        return (byte) ((burning ? 0x01 : 0) + (sneaking ? 0x02 : 0) + (sprinting ? 0x08 : 0) + (invisible ? 0x20 : 0) + (glowingColor != null ? 0x40 : 0) + (elytraFlying ? 0x80 : 0));
    }

}
