package ru.stonlex.bukkit.protocollib.entity.impl;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import ru.stonlex.bukkit.protocollib.entity.FakeBaseEntity;

@Getter
public class FakeArrow extends FakeBaseEntity {

    protected boolean critical;
    protected boolean noClip;

    public FakeArrow(@NonNull Location location) {
        super(EntityType.ARROW, location);
    }


    public synchronized void setCritical(boolean critical) {
        this.critical = critical;

        broadcastDataWatcherObject(6, BYTE_SERIALIZER, generateBitMask());
    }

    public synchronized void setNoClip(boolean noClip) {
        this.noClip = noClip;

        broadcastDataWatcherObject(6, BYTE_SERIALIZER, generateBitMask());
    }


    private synchronized byte generateBitMask() {
        return (byte) ((critical ? 0x01 : 0) + (noClip ? 0x02 : 0));
    }
}
