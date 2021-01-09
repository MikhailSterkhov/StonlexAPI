package ru.stonlex.bukkit.protocollib.entity.impl;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import ru.stonlex.bukkit.protocollib.entity.FakeBaseEntityLiving;

@Getter
public class FakeArmorStand extends FakeBaseEntityLiving {

    private boolean marker;
    private boolean small;
    private boolean basePlate;
    private boolean arms;

    public FakeArmorStand(Location location) {
        super(EntityType.ARMOR_STAND, location);
    }


    public void setSmall(boolean small) {
        this.small = small;

        broadcastDataWatcherObject(11, BYTE_SERIALIZER, generateBitMask());
    }

    public void setArms(boolean arms) {
        this.arms = arms;

        broadcastDataWatcherObject(11, BYTE_SERIALIZER, generateBitMask());
    }

    public void setBasePlate(boolean basePlate) {
        this.basePlate = basePlate;

        broadcastDataWatcherObject(11, BYTE_SERIALIZER, generateBitMask());
    }

    public void setMarker(boolean marker) {
        this.marker = marker;

        broadcastDataWatcherObject(11, BYTE_SERIALIZER, generateBitMask());
    }

    private byte generateBitMask() {
        return (byte) ((small ? 0x01 : 0) + (arms ? 0x04 : 0) + (!basePlate ? 0x08 : 0) + (marker ? 0x10 : 0));
    }

}
