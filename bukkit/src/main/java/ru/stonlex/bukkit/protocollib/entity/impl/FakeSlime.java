package ru.stonlex.bukkit.protocollib.entity.impl;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import ru.stonlex.bukkit.protocollib.entity.FakeBaseEntityLiving;

public class FakeSlime extends FakeBaseEntityLiving {

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
    public synchronized void setSize(int size) {
        this.size = size;

        broadcastDataWatcherObject(15, INT_SERIALIZER, size);
    }

}
