package ru.stonlex.bukkit.protocollib.entity.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.stonlex.bukkit.protocollib.entity.FakeBaseEntity;
import ru.stonlex.bukkit.protocollib.entity.FakeEntityRegistry;
import ru.stonlex.bukkit.protocollib.entity.FakeEntityScope;

import java.util.ArrayList;
import java.util.Collection;

public final class FakeEntityScopeListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Collection<FakeBaseEntity> publicFakeEntityCollection = new ArrayList<>();

        FakeEntityRegistry.INSTANCE.getFakeEntityScopeMap().forEachEntry((entityId, fakeEntityScope) -> {
            if (fakeEntityScope.equals(FakeEntityScope.PROTOTYPE)) {
                return true;
            }

            publicFakeEntityCollection.add(FakeEntityRegistry.INSTANCE.getEntityById(entityId));
            return true;
        });

        for (FakeBaseEntity fakeBaseEntity : publicFakeEntityCollection) {
            fakeBaseEntity.addViewers(player);
        }
    }

}
