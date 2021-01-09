package ru.stonlex.bukkit.gaming.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import ru.stonlex.bukkit.gaming.GameProcess;
import ru.stonlex.bukkit.gaming.event.GameGhostStatusChangeEvent;
import ru.stonlex.bukkit.gaming.player.GamingPlayer;

@RequiredArgsConstructor
public class GamingProcessListener implements Listener {

    private final GameProcess gameProcess;


    @EventHandler
    public void onPlayerGhost(GameGhostStatusChangeEvent event) {
        GamingPlayer gamingPlayer = event.getGamingPlayer();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        GamingPlayer gamingPlayer = GamingPlayer.of(event.getEntity());

        if (gamingPlayer == null || gamingPlayer.isGhost()) {
            return;
        }

        gameProcess.onDeath(gamingPlayer, event);
    }

}
