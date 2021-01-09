package ru.stonlex.bukkit.protocollib.entity.impl;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import ru.stonlex.bukkit.protocollib.entity.FakeBaseEntityLiving;

@Getter
public class FakeBat extends FakeBaseEntityLiving {

    private boolean hanging;

    public FakeBat(Location location) {
        super(EntityType.BAT, location);
    }

    public void setHanging(boolean hanging) {
        this.hanging = hanging;

        broadcastDataWatcherObject(12, BYTE_SERIALIZER, generateBitMask());
    }

    public byte generateBitMask() {
        return (byte) (hanging ? 0x01 : 0);
    }

}
