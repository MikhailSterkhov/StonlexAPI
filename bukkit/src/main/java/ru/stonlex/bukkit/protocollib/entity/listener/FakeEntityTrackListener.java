package ru.stonlex.bukkit.protocollib.entity.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import ru.stonlex.bukkit.protocollib.entity.FakeBaseEntity;
import ru.stonlex.bukkit.protocollib.entity.FakeEntityRegistry;

public class FakeEntityTrackListener implements Listener {

    protected static final double ENTITY_TRACK_DISTANCE = 65.5d;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        for (FakeBaseEntity fakeBaseEntity : FakeEntityRegistry.INSTANCE.getFakeEntityIdsMap().valueCollection()) {

            if (fakeBaseEntity.getLocation().distance(player.getLocation()) > ENTITY_TRACK_DISTANCE && fakeBaseEntity.hasViewer(player)) {
                fakeBaseEntity.removeViewers(player);
            }

            if (fakeBaseEntity.getLocation().distance(player.getLocation()) <= ENTITY_TRACK_DISTANCE && !fakeBaseEntity.hasViewer(player)) {
                fakeBaseEntity.addViewers(player);
            }
        }
    }
}
