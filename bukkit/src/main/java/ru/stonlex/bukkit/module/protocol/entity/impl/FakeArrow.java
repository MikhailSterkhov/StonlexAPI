package ru.stonlex.bukkit.module.protocol.entity.impl;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import ru.stonlex.bukkit.module.protocol.entity.StonlexFakeEntity;

/**
 * @Author ItzStonlex.
 * @VK https://vk.com/itzstonlex
 * <p>
 * (Created on 14.08.2019 8:27)
 */
public class FakeArrow extends StonlexFakeEntity {

    @Getter
    private boolean critical, noClip;

    public FakeArrow(Location location) {
        super(EntityType.ARROW, location);
    }

    public void setCritical(boolean critical) {
        this.critical = critical;

        getDataWatcher().setObject(new WrappedDataWatcher.WrappedDataWatcherObject(6, BYTE_SERIALIZER), generateBitMask());
        sendDataWatcherPacket();
    }

    public void setNoClip(boolean noClip) {
        this.noClip = noClip;

        getDataWatcher().setObject(new WrappedDataWatcher.WrappedDataWatcherObject(6, BYTE_SERIALIZER), generateBitMask());
        sendDataWatcherPacket();
    }

    public byte generateBitMask() {
        return (byte) ((critical ? 0x01 : 0) + (noClip ? 0x02 : 0));
    }
}
