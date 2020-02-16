package ru.stonlex.bukkit.protocol.entity.impl;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import ru.stonlex.bukkit.protocol.entity.MoonFakeEntity;

public class FakeVillager extends MoonFakeEntity {

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
