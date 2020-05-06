package ru.stonlex.bukkit.module.protocol.entity.impl;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import ru.stonlex.bukkit.module.protocol.entity.StonlexFakeEntity;

public class FakeSlime extends StonlexFakeEntity {

    @Getter
    private int size;

    public FakeSlime(Location location) {
        super(EntityType.SLIME, location);
    }


    /**
     * Установить новый размер для слайма
     *
     * @param size - новый размер
     */
    public void setSize(int size) {
        this.size = size;

        getDataWatcher().setObject(new WrappedDataWatcher.WrappedDataWatcherObject(15, INT_SERIALIZER), size);
        sendDataWatcherPacket();
    }

}
