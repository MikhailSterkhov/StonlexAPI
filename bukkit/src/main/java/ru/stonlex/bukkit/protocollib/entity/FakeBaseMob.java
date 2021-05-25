package ru.stonlex.bukkit.protocollib.entity;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@Getter
public abstract class FakeBaseMob
        extends FakeBaseEntityLiving implements FakeEntityLiving {

    private boolean noAI;
    private boolean agressive;

    public FakeBaseMob(@NonNull EntityType entityType, @NonNull Location location) {
        super(entityType, location);
    }

    public void setAgressive(boolean agressive) {
        this.agressive = agressive;

        broadcastDataWatcherObject(14, BYTE_SERIALIZER, generateBitMask());
    }

    public void setNoAI(boolean noAI) {
        this.noAI = noAI;

        broadcastDataWatcherObject(14, BYTE_SERIALIZER, generateBitMask());
    }

    private byte generateBitMask() {
        return (byte) ((noAI ? 0x01 : 0) + (agressive ? 0x04 : 0));
    }
}
