package ru.stonlex.bukkit.depend.protocol.entity.impl;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import ru.stonlex.bukkit.depend.protocol.entity.StonlexFakeEntity;

public class FakeSilverfish extends StonlexFakeEntity {

    public FakeSilverfish(Location location) {
        super(EntityType.SILVERFISH, location);
    }

}
