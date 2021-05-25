package ru.stonlex.bukkit.protocollib.entity.impl;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import ru.stonlex.bukkit.protocollib.entity.FakeBaseMob;

@Getter
public class FakeSheep extends FakeBaseMob {

    private DyeColor color;
    private boolean sheared;

    public FakeSheep(@NonNull Location location) {
        super(EntityType.SHEEP, location);
    }

    public void setSheared(boolean sheared) {
        this.sheared = sheared;

        broadcastDataWatcherObject(13, BYTE_SERIALIZER, generateBitMask());
    }

    public void setColor(DyeColor color) {
        this.color = color;

        broadcastDataWatcherObject(13, BYTE_SERIALIZER, generateBitMask());
    }

    private byte generateBitMask() {
        return (byte) ((sheared ? 0x10 : 0) + (color != null ? color.getWoolData() : 0));
    }

}
