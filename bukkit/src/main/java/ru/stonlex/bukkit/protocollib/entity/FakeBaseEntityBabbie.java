package ru.stonlex.bukkit.protocollib.entity;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@Getter
public abstract class FakeBaseEntityBabbie
        extends FakeBaseEntityLiving implements FakeEntityLiving {


    protected boolean baby;

    public FakeBaseEntityBabbie(@NonNull EntityType entityType, @NonNull Location location) {
        super(entityType, location);
    }

    public synchronized void setBaby(boolean baby) {
        this.baby = baby;

        broadcastDataWatcherObject(15, BOOLEAN_SERIALIZER, baby);
    }

}
