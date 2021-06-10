package ru.stonlex.bukkit.protocollib.entity.impl;

import com.comphenix.protocol.wrappers.Vector3F;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import ru.stonlex.bukkit.protocollib.entity.FakeBaseEntity;
import ru.stonlex.bukkit.protocollib.packet.entity.WrapperPlayServerSpawnEntity;

@Getter
public class FakeArmorStand extends FakeBaseEntity {

    private boolean marker;
    private boolean small;
    private boolean basePlate;
    private boolean arms;

    public FakeArmorStand(Location location) {
        super(EntityType.ARMOR_STAND, location);
    }

    @Override
    public synchronized int getSpawnTypeId() {
        return WrapperPlayServerSpawnEntity.ObjectTypes.ARMORSTAND;
    }

    public synchronized void setSmall(boolean small) {
        this.small = small;

        broadcastDataWatcherObject(11, BYTE_SERIALIZER, generateBitMask());
    }

    public synchronized void setArms(boolean arms) {
        this.arms = arms;

        broadcastDataWatcherObject(11, BYTE_SERIALIZER, generateBitMask());
    }

    public synchronized void setBasePlate(boolean basePlate) {
        this.basePlate = basePlate;

        broadcastDataWatcherObject(11, BYTE_SERIALIZER, generateBitMask());
    }

    public synchronized void setMarker(boolean marker) {
        this.marker = marker;

        broadcastDataWatcherObject(11, BYTE_SERIALIZER, generateBitMask());
    }


    public synchronized void setHeadRotation(@NonNull Vector3F vector3F) {
        broadcastDataWatcherObject(12, ROTATION_SERIALIZER, vector3F);
    }

    public synchronized void setHeadRotation(float x, float y, float z) {
        setHeadRotation(new Vector3F(x, y, z));
    }

    public synchronized void setBodyRotation(@NonNull Vector3F vector3F) {
        broadcastDataWatcherObject(13, ROTATION_SERIALIZER, vector3F);
    }

    public synchronized void setBodyRotation(float x, float y, float z) {
        setBodyRotation(new Vector3F(x, y, z));
    }

    public synchronized void setLeftArmRotation(@NonNull Vector3F vector3F) {
        broadcastDataWatcherObject(14, ROTATION_SERIALIZER, vector3F);
    }

    public synchronized void setLeftArmRotation(float x, float y, float z) {
        setLeftArmRotation(new Vector3F(x, y, z));
    }

    public synchronized void setRightArmRotation(@NonNull Vector3F vector3F) {
        broadcastDataWatcherObject(15, ROTATION_SERIALIZER, vector3F);
    }

    public synchronized void setRightArmRotation(float x, float y, float z) {
        setRightArmRotation(new Vector3F(x, y, z));
    }

    public synchronized void setLeftLegRotation(@NonNull Vector3F vector3F) {
        broadcastDataWatcherObject(16, ROTATION_SERIALIZER, vector3F);
    }

    public synchronized void setLeftLegRotation(float x, float y, float z) {
        setLeftLegRotation(new Vector3F(x, y, z));
    }

    public synchronized void setRightLegRotation(@NonNull Vector3F vector3F) {
        broadcastDataWatcherObject(17, ROTATION_SERIALIZER, vector3F);
    }

    public synchronized void setRightLegRotation(float x, float y, float z) {
        setRightLegRotation(new Vector3F(x, y, z));
    }


    private synchronized byte generateBitMask() {
        return (byte) ((small ? 0x01 : 0) + (arms ? 0x04 : 0) + (!basePlate ? 0x08 : 0) + (marker ? 0x10 : 0));
    }

}
