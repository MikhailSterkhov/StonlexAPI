package ru.stonlex.bukkit.protocollib.packet;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import lombok.experimental.UtilityClass;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntity;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.protocollib.packet.entity.*;

import java.util.Collections;
import java.util.UUID;

@UtilityClass
public class ProtocolPacketUtil {

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
        WrapperPlayServerNamedEntitySpawn packet = new WrapperPlayServerNamedEntitySpawn();

        packet.setEntityID(entityId);
        packet.setMetadata(dataWatcher);
        packet.setPlayerUUID(uuid);

        packet.setPosition(location.toVector());
        packet.setYaw(location.getYaw());
        packet.setPitch(location.getPitch());

        return packet;
    }

    public WrapperPlayServerSpawnEntityLiving createSpawnEntityLivingPacket(int entityId, EntityType entityType, WrappedDataWatcher dataWatcher, Location location) {
        WrapperPlayServerSpawnEntityLiving packet = new WrapperPlayServerSpawnEntityLiving();

        packet.setEntityID(entityId);
        packet.setMetadata(dataWatcher);

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
