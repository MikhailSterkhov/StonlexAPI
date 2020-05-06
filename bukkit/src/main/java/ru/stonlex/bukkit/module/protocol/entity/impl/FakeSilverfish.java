package ru.stonlex.bukkit.module.protocol.entity.impl;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import ru.stonlex.bukkit.module.protocol.entity.StonlexFakeEntity;

public class FakeSilverfish extends StonlexFakeEntity {

    public FakeSilverfish(Location location) {
        super(EntityType.SILVERFISH, location);
    }

}
