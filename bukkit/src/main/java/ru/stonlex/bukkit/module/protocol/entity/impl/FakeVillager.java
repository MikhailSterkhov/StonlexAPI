package ru.stonlex.bukkit.module.protocol.entity.impl;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import ru.stonlex.bukkit.module.protocol.entity.StonlexFakeEntity;

public class FakeVillager extends StonlexFakeEntity {

    public FakeVillager(Location location) {
        super(EntityType.VILLAGER, location);
    }

    public void setProfession(Profession profession) {
        getDataWatcher().setObject(new WrappedDataWatcher.WrappedDataWatcherObject(13, INT_SERIALIZER), profession.ordinal());
        sendDataWatcherPacket();
    }

    public int getProfession() {
        return getDataWatcher().getInteger(13);
    }

    public enum Profession {
        FARMER, LIBRARIAN, PRIEST, BACKSMITH, BUTCHER, NITWIT

    }

}
