package ru.stonlex.bukkit.protocollib.packet;

import com.comphenix.protocol.utility.MinecraftProtocolVersion;
import com.comphenix.protocol.utility.MinecraftVersion;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.protocollib.packet.block.WrapperPlayServerBlockAction;
import ru.stonlex.bukkit.protocollib.packet.entity.*;

import java.util.Collections;
import java.util.UUID;

@UtilityClass
public class ProtocolPacketFactory {

    public WrapperPlayServerAnimation createAnimationPacket(int entityId, int animationId) {
        WrapperPlayServerAnimation packet = new WrapperPlayServerAnimation();

        packet.setEntityID(entityId);
        packet.setAnimation(animationId);

        return packet;
    }

    public WrapperPlayServerEntityDestroy createEntityDestroyPacket(int... entityIds) {
        WrapperPlayServerEntityDestroy packet = new WrapperPlayServerEntityDestroy();

        packet.setEntityIds(entityIds);

        return packet;
    }

    public WrapperPlayServerEntityEquipment createEntityEquipmentPacket(int entityId, EnumWrappers.ItemSlot itemSlot, ItemStack itemStack) {
        WrapperPlayServerEntityEquipment packet = new WrapperPlayServerEntityEquipment();

        packet.setEntityID(entityId);
        packet.setSlot(itemSlot);
        packet.setItem(itemStack);

        return packet;
    }

    public WrapperPlayServerEntityHeadRotation createEntityHeadRotationPacket(int entityId, byte headYaw) {
        WrapperPlayServerEntityHeadRotation packet = new WrapperPlayServerEntityHeadRotation();

        packet.setEntityID(entityId);
        packet.setHeadYaw(headYaw);

        return packet;
    }

    public WrapperPlayServerEntityLook createEntityLookPacket(int entityId, float yaw, float pitch) {
        WrapperPlayServerEntityLook packet = new WrapperPlayServerEntityLook();

        packet.setEntityID(entityId);

        packet.setYaw(yaw);
        packet.setPitch(pitch);

        return packet;
    }

    public WrapperPlayServerNamedEntitySpawn createNamedEntitySpawnPacket(int entityId, WrappedDataWatcher dataWatcher, UUID uuid, Location location) {
        int beeVersion = MinecraftProtocolVersion.getVersion(MinecraftVersion.BEE_UPDATE);
        int currentVersion = MinecraftProtocolVersion.getCurrentVersion();

        WrapperPlayServerNamedEntitySpawn packet = new WrapperPlayServerNamedEntitySpawn();

        packet.setEntityID(entityId);
        packet.setPlayerUUID(uuid);

        if (currentVersion < beeVersion) {
            packet.setMetadata(dataWatcher);
        }

        packet.setPosition(location.toVector());
        packet.setYaw(location.getYaw());
        packet.setPitch(location.getPitch());

        return packet;
    }

    public WrapperPlayServerBlockAction createBlockActionPacket(Block block, int value1, int value2) {
        WrapperPlayServerBlockAction packet = new WrapperPlayServerBlockAction();

        packet.setBlockType(block.getType());
        packet.setLocation(new BlockPosition(block.getX(), block.getY(), block.getZ()));

        packet.setByte1(value1);
        packet.setByte2(value2);

        return packet;
    }

    public WrapperPlayServerSpawnEntity createSpawnEntityPacket(int entityId, int spawnTypeId, EntityType entityType, Location location) {
        int beeVersion = MinecraftProtocolVersion.getVersion(MinecraftVersion.BEE_UPDATE);
        int currentVersion = MinecraftProtocolVersion.getCurrentVersion();

        WrapperPlayServerSpawnEntity packet = new WrapperPlayServerSpawnEntity();

        packet.getHandle().getIntegers().write(0, entityId);
        packet.getHandle().getIntegers().write(6, spawnTypeId);

        if (currentVersion < beeVersion) {
            packet.getHandle().getIntegers().write(7, (int) entityType.getTypeId());

        } else {

            packet.getHandle().getEntityTypeModifier().write(0, entityType);
        }

        packet.getHandle().getDoubles().write(0, location.getX());
        packet.getHandle().getDoubles().write(1, location.getY());
        packet.getHandle().getDoubles().write(2, location.getZ());

       //if (entityData > 0) {
       //    packet.getHandle().getIntegers().write(1, (int) (velocity.getX() * 8000.0D));
       //    packet.getHandle().getIntegers().write(2, (int) (velocity.getY() * 8000.0D));
       //    packet.getHandle().getIntegers().write(3, (int) (velocity.getZ() * 8000.0D));
       //}

        packet.getHandle().getIntegers().write(4, (int) (location.getPitch() * 256.0F / 360.0F));
        packet.getHandle().getIntegers().write(5, (int) (location.getYaw() * 256.0F / 360.0F));

        return packet;
    }

    public WrapperPlayServerSpawnEntityLiving createSpawnEntityLivingPacket(int entityId, EntityType entityType, WrappedDataWatcher dataWatcher, Location location) {
        int beeVersion = MinecraftProtocolVersion.getVersion(MinecraftVersion.BEE_UPDATE);
        int currentVersion = MinecraftProtocolVersion.getCurrentVersion();

        WrapperPlayServerSpawnEntityLiving packet = new WrapperPlayServerSpawnEntityLiving();
        packet.setEntityID(entityId);

        if (currentVersion < beeVersion) {
            packet.setMetadata(dataWatcher);
        }

        packet.setType(entityType);

        packet.setX(location.getX());
        packet.setY(location.getY());
        packet.setZ(location.getZ());

        packet.setYaw(location.getYaw());
        packet.setPitch(location.getPitch());

        return packet;
    }

    public WrapperPlayServerEntityMetadata createEntityMetadataPacket(int entityId, WrappedDataWatcher dataWatcher) {
        WrapperPlayServerEntityMetadata packet = new WrapperPlayServerEntityMetadata();

        packet.setEntityID(entityId);
        packet.setMetadata(dataWatcher.getWatchableObjects());

        return packet;
    }

    public WrapperPlayServerEntityTeleport createEntityTeleportPacket(int entityId, Location location) {
        WrapperPlayServerEntityTeleport packet = new WrapperPlayServerEntityTeleport();

        packet.setEntityID(entityId);

        packet.setX(location.getX());
        packet.setY(location.getY());
        packet.setZ(location.getZ());

        packet.setYaw(location.getYaw());
        packet.setPitch(location.getPitch());

        return packet;
    }

    public WrapperPlayServerPlayerInfo createPlayerInfoPacket(EnumWrappers.PlayerInfoAction playerInfoAction, PlayerInfoData playerInfoData) {
        WrapperPlayServerPlayerInfo packet = new WrapperPlayServerPlayerInfo();

        packet.setAction(playerInfoAction);
        packet.setData(Collections.singletonList(playerInfoData));

        return packet;
    }

    public WrapperPlayServerRelEntityMove createRelEntityMovePacket(int entityId, Location location) {
        WrapperPlayServerRelEntityMove packet = new WrapperPlayServerRelEntityMove();

        packet.setEntityID(entityId);

        packet.setDx(location.getX());
        packet.setDy(location.getY());
        packet.setDz(location.getZ());

        packet.setYaw(location.getYaw());
        packet.setPitch(location.getPitch());

        return packet;
    }

    public WrapperPlayServerRelEntityMoveLook createRelEntityMoveLookPacket(int entityId, Location location) {
        WrapperPlayServerRelEntityMoveLook packet = new WrapperPlayServerRelEntityMoveLook();

        packet.setEntityID(entityId);

        packet.setDx(location.getX());
        packet.setDy(location.getY());
        packet.setDz(location.getZ());

        packet.setYaw(location.getYaw());
        packet.setPitch(location.getPitch());

        return packet;
    }
}
