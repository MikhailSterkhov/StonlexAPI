package ru.stonlex.bukkit.gaming.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.stonlex.bukkit.gaming.GameProcess;
import ru.stonlex.bukkit.gaming.database.GamingDatabase;
import ru.stonlex.bukkit.gaming.player.GamingPlayer;

@RequiredArgsConstructor
public class GamingPlayerDatabaseListener implements Listener {

    private final GameProcess gameProcess;


    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {

        Player player = playerJoinEvent.getPlayer();
        GamingPlayer gamingPlayer = GamingPlayer.of(player);

        GamingDatabase gamingDatabase = gameProcess.getGamingDatabase();
        if (gamingDatabase != null) {

            gamingDatabase.loadPlayer(gamingPlayer);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLeave(PlayerQuitEvent playerQuitEvent) {

        Player player = playerQuitEvent.getPlayer();
        GamingPlayer gamingPlayer = GamingPlayer.of(player);

        GamingDatabase gamingDatabase = gameProcess.getGamingDatabase();
        if (gamingDatabase != null) {

            gamingDatabase.savePlayer(gamingPlayer);
        }
    }
}
