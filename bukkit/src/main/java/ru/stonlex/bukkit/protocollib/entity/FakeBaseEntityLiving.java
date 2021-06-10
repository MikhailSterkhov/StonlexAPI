package ru.stonlex.bukkit.protocollib.entity;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.protocollib.entity.animation.FakeEntityAnimation;
import ru.stonlex.bukkit.protocollib.packet.AbstractPacket;
import ru.stonlex.bukkit.protocollib.packet.ProtocolPacketFactory;

import java.util.Collection;
import java.util.Collections;

@Getter
public abstract class FakeBaseEntityLiving
        extends FakeBaseEntity implements FakeEntityLiving {


    protected int arrowCount;
    protected float healthScale;

    protected boolean ambientPotionEffect;

    protected ChatColor potionEffectColor;


    public FakeBaseEntityLiving(@NonNull EntityType entityType, @NonNull Location location) {
        super(entityType, location);
    }


    @Override
    public synchronized Collection<AbstractPacket> getSpawnPackets() {
        return Collections.singletonList(
                ProtocolPacketFactory.createSpawnEntityLivingPacket(entityId, entityType, dataWatcher, location));
    }

    @Override
    public synchronized void playAnimationAll(@NonNull FakeEntityAnimation fakeEntityAnimation) {
        for (Player player : getViewerCollection())
            playAnimation(fakeEntityAnimation, player);
    }

    @Override
    public synchronized void playAnimation(@NonNull FakeEntityAnimation fakeEntityAnimation, @NonNull Player player) {
        ProtocolPacketFactory.createAnimationPacket(entityId, fakeEntityAnimation.ordinal())
                .sendPacket(player);
    }


    @Override
    public synchronized void setArrowCount(int arrowCount) {
        this.arrowCount = arrowCount;

        broadcastDataWatcherObject(11, INT_SERIALIZER, arrowCount);
    }

    @Override
    public synchronized void setHealthScale(float healthScale) {
        this.healthScale = healthScale;

        broadcastDataWatcherObject(11, FLOAT_SERIALIZER, healthScale);
    }

    @Override
    public synchronized void setAmbientPotionEffect(boolean ambientPotionEffect) {
        this.ambientPotionEffect = ambientPotionEffect;

        broadcastDataWatcherObject(10, BOOLEAN_SERIALIZER, healthScale);
    }

    @Override
    public synchronized void setPotionEffectColor(@NonNull ChatColor potionEffectColor) {
        this.potionEffectColor = potionEffectColor;

        broadcastDataWatcherObject(9, INT_SERIALIZER, potionEffectColor.ordinal());
    }

}
