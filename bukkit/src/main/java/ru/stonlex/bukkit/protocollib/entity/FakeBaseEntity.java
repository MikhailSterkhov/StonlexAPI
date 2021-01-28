package ru.stonlex.bukkit.protocollib.entity;

import com.comphenix.protocol.reflect.accessors.Accessors;
import com.comphenix.protocol.reflect.accessors.FieldAccessor;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import ru.stonlex.bukkit.protocollib.entity.equipment.FakeEntityEquipment;
import ru.stonlex.bukkit.protocollib.packet.AbstractPacket;
import ru.stonlex.bukkit.protocollib.packet.ProtocolPacketUtil;
import ru.stonlex.bukkit.protocollib.packet.entity.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

@Getter
public abstract class FakeBaseEntity implements Cloneable, FakeEntityClickable {

    public static @NonNull WrappedDataWatcher.Serializer BYTE_SERIALIZER = WrappedDataWatcher.Registry.get(Byte.class);
    public static @NonNull WrappedDataWatcher.Serializer FLOAT_SERIALIZER = WrappedDataWatcher.Registry.get(Float.class);
    public static @NonNull WrappedDataWatcher.Serializer INT_SERIALIZER = WrappedDataWatcher.Registry.get(Integer.class);
    public static @NonNull WrappedDataWatcher.Serializer STRING_SERIALIZER = WrappedDataWatcher.Registry.get(String.class);
    public static @NonNull WrappedDataWatcher.Serializer BOOLEAN_SERIALIZER = WrappedDataWatcher.Registry.get(Boolean.class);

    public static @NonNull FieldAccessor ENTITY_ID_ACCESSOR = Accessors.getFieldAccessor(MinecraftReflection.getEntityClass(), "entityCount", true);


    protected @NonNull int entityId;
    protected @NonNull EntityType entityType;

    protected FakeEntityScope entityScope;

    protected String customName;
    protected Location location;

    protected ChatColor glowingColor;

    protected @NonNull FakeEntityEquipment entityEquipment = new FakeEntityEquipment(this);
    protected @NonNull WrappedDataWatcher dataWatcher = new WrappedDataWatcher();
    protected @NonNull Collection<Player> viewerCollection = new ArrayList<>();

    protected boolean burning;
    protected boolean sneaking;
    protected boolean sprinting;
    protected boolean invisible;
    protected boolean elytraFlying;
    protected boolean customNameVisible;
    protected boolean noGravity;

    @Setter
    protected Consumer<Player> clickAction;


    public FakeBaseEntity(int entityId, @NonNull EntityType entityType, @NonNull Location location, @NonNull FakeEntityScope entityScope) {
        this.entityId = entityId;
        this.entityType = entityType;

        this.location = location;
        this.entityScope = entityScope;

        FakeEntityRegistry.INSTANCE.register(entityScope, this);
    }

    public FakeBaseEntity(@NonNull EntityType entityType, @NonNull Location location, @NonNull FakeEntityScope entityScope) {
        this((int) ENTITY_ID_ACCESSOR.get(null), entityType, location, entityScope);

        ENTITY_ID_ACCESSOR.set(null, (entityId + 1));
    }

    public FakeBaseEntity(@NonNull EntityType entityType, @NonNull Location location) {
        this(entityType, location, FakeEntityScope.PROTOTYPE);
    }


    public synchronized void spawn() {
        this.entityScope = FakeEntityScope.PUBLIC;

        FakeEntityRegistry.INSTANCE.registerPublic(this);
        addViewers(Bukkit.getOnlinePlayers().toArray(new Player[0]));
    }

    public synchronized void remove() {

        /* ConcurrentModificationException fix */
        Collection<Player> copiedViewerCollection = new ArrayList<>(viewerCollection);

        removeViewers(copiedViewerCollection.toArray(new Player[0]));
    }


    public synchronized boolean hasViewer(@NonNull Player player) {
        return player.isOnline() & viewerCollection.contains(player);
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


    protected synchronized void sendSpawnPackets(@NonNull Player player) {
        for (AbstractPacket abstractPacket : getSpawnPackets())
            abstractPacket.sendPacket(player);
    }

    protected synchronized void sendDestroyPackets(@NonNull Player player) {
        for (AbstractPacket abstractPacket : getDestroyPackets())
            abstractPacket.sendPacket(player);
    }


    public synchronized Collection<AbstractPacket> getSpawnPackets() {
        return Collections.singletonList(
                ProtocolPacketUtil.createSpawnEntityLivingPacket(entityId, entityType, dataWatcher, location));
    }

    public synchronized Collection<AbstractPacket> getDestroyPackets() {
        return Collections.singletonList(
                ProtocolPacketUtil.createEntityDestroyPacket(entityId));
    }

    public void teleport(@NonNull Location location) {
        this.location = location;

        viewerCollection.forEach(this::sendTeleportPacket);
    }

    public void look(@NonNull Player player, @NonNull Location location) {
        Vector vector = location.clone().subtract(this.location).toVector().normalize();

        this.location.setDirection(vector);
        this.location.setYaw(this.location.getYaw());
        this.location.setPitch(this.location.getPitch());

        sendEntityLookPacket(player);
        sendHeadRotationPacket(player);
    }

    public void look(@NonNull Player player) {
        look(player, player.getLocation());
    }

    public void look(float yaw, float pitch) {
        location.setYaw(yaw);
        location.setPitch(pitch);

        for (Player receiver : viewerCollection) {
            sendEntityLookPacket(receiver);
            sendHeadRotationPacket(receiver);
        }
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
        ProtocolPacketUtil.createEntityMetadataPacket(entityId, dataWatcher).sendPacket(player);
    }

    protected synchronized void broadcastDataWatcherPacket() {
        WrapperPlayServerEntityMetadata entityMetadataPacket
                = ProtocolPacketUtil.createEntityMetadataPacket(entityId, dataWatcher);

        viewerCollection.forEach(entityMetadataPacket::sendPacket);
    }

    protected synchronized void sendDataWatcherObject(@NonNull Player player,
                                                     int dataWatcherIndex, @NonNull WrappedDataWatcher.Serializer serializer, @NonNull Object value) {

        WrappedDataWatcher.WrappedDataWatcherObject wrappedDataWatcherObject
                = new WrappedDataWatcher.WrappedDataWatcherObject(dataWatcherIndex, serializer);

        getDataWatcher().setObject(wrappedDataWatcherObject, value);
        sendDataWatcherPacket(player);
    }

    protected synchronized void broadcastDataWatcherObject(int dataWatcherIndex, @NonNull WrappedDataWatcher.Serializer serializer, @NonNull Object value) {
        WrappedDataWatcher.WrappedDataWatcherObject wrappedDataWatcherObject
                = new WrappedDataWatcher.WrappedDataWatcherObject(dataWatcherIndex, serializer);

        getDataWatcher().setObject(wrappedDataWatcherObject, value);
        broadcastDataWatcherPacket();
    }


    public void setBurning(boolean burning) {
        this.burning = burning;

        broadcastDataWatcherObject(0, BYTE_SERIALIZER, generateBitMask());
    }

    public void setSneaking(boolean sneaking) {
        this.sneaking = sneaking;

        broadcastDataWatcherObject(0, BYTE_SERIALIZER, generateBitMask());
    }

    public void setSprinting(boolean sprinting) {
        this.sprinting = sprinting;

        broadcastDataWatcherObject(0, BYTE_SERIALIZER, generateBitMask());
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;

        broadcastDataWatcherObject(0, BYTE_SERIALIZER, generateBitMask());
    }

    public void setElytraFlying(boolean elytraFlying) {
        this.elytraFlying = elytraFlying;

        broadcastDataWatcherObject(0, BYTE_SERIALIZER, generateBitMask());
    }

    public void setGlowingColor(@NonNull ChatColor glowingColor) {
        this.glowingColor = glowingColor;

        broadcastDataWatcherObject(0, BYTE_SERIALIZER, generateBitMask());
    }

    public void setCustomNameVisible(boolean customNameVisible) {
        this.customNameVisible = customNameVisible;

        broadcastDataWatcherObject(3, BOOLEAN_SERIALIZER, customNameVisible);
    }

    public void setCustomName(@NonNull String customName) {
        this.customName = customName;

        broadcastDataWatcherObject(2, STRING_SERIALIZER, customName);
    }


    public void setCustomName(Entity entity, Player player) {

        // change entity datawatcher
        WrappedDataWatcher wrappedDataWatcher = WrappedDataWatcher.getEntityWatcher(entity);

        wrappedDataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, STRING_SERIALIZER), player.getName());
        wrappedDataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, BOOLEAN_SERIALIZER), true);

        // send datawatcher change packet
        WrapperPlayServerEntityMetadata entityMetadataPacket = new WrapperPlayServerEntityMetadata();

        entityMetadataPacket.setEntityID(entity.getEntityId());
        entityMetadataPacket.setMetadata(wrappedDataWatcher.getWatchableObjects());

        entityMetadataPacket.sendPacket(player);
    }

    public void setNoGravity(boolean noGravity) {
        this.noGravity = noGravity;

        broadcastDataWatcherObject(5, BOOLEAN_SERIALIZER, noGravity);
    }

    @SneakyThrows
    public synchronized FakeBaseEntity clone() {
        return (FakeBaseEntity) super.clone();
    }


    private synchronized byte generateBitMask() {
        return (byte) ((burning ? 0x01 : 0) + (sneaking ? 0x02 : 0) + (sprinting ? 0x08 : 0) + (invisible ? 0x20 : 0) + (glowingColor != null ? 0x40 : 0) + (elytraFlying ? 0x80 : 0));
    }

}
