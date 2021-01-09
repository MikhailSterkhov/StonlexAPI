package ru.stonlex.bukkit.protocollib.entity.impl;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import ru.stonlex.bukkit.protocollib.entity.FakeBaseEntityLiving;

public class FakeVillager extends FakeBaseEntityLiving {

    public FakeVillager(Location location) {
        super(EntityType.VILLAGER, location);
    }

    public void setProfession(Profession profession) {
        broadcastDataWatcherObject(13, INT_SERIALIZER, profession.ordinal());
    }

    public int getProfession() {
        return getDataWatcher().getInteger(13);
    }

    public enum Profession {

        FARMER,
        LIBRARIAN,
        PRIEST,
        BACKSMITH,
        BUTCHER,
        NITWIT
        ;
    }

}
