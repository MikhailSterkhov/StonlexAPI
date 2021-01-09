package ru.stonlex.bukkit.protocollib.entity.impl;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import ru.stonlex.bukkit.protocollib.entity.FakeBaseEntityLiving;

public class FakeSilverfish extends FakeBaseEntityLiving {

    public FakeSilverfish(Location location) {
        super(EntityType.SILVERFISH, location);
    }

}
