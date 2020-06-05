package ru.stonlex.bukkit.game.event.impl;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.depend.vault.IVaultPlayer;
import ru.stonlex.bukkit.game.enums.GameEvent;
import ru.stonlex.bukkit.game.enums.GameStatus;
import ru.stonlex.bukkit.game.event.GameListener;
import ru.stonlex.bukkit.game.event.annotation.GEventHandler;
import ru.stonlex.bukkit.game.factory.AbstractTimerFactory;
import ru.stonlex.bukkit.game.player.GamePlayer;

import java.util.Collection;

public class GameFactoryListener extends GameListener {

    @GEventHandler(gameEvent = GameEvent.START_GAME)
    public void onStartGame() {
        Collection<GamePlayer> alivePlayers = getGameManager().getAlivePlayers();

        for (GamePlayer gamePlayer : alivePlayers) {
            gamePlayer.sendMessage("§cОру, ты еще жив? сочувствую. ну, игра началась, пизди всех :)");
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        event.setDeathMessage(null);
        event.setDroppedExp(0);
        event.setKeepLevel(false);
        event.setKeepInventory(false);

        GamePlayer gamePlayer = BukkitAPI.getInstance().getGameManager().getGamePlayer(player);

        BukkitAPI.getInstance().getGameManager().getGameFactory().onDeath(gamePlayer);
        gamePlayer.setSpectate();

        player.spigot().respawn();
    }

    // =========================== // ИГРОВОЙ ТАЙМЕР // =========================== //
    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        int maxArenaOnline = getGameManager().getGameSettings().MAX_ARENA_SLOTS;
        int serverOnline = Bukkit.getOnlinePlayers().size();

        if (serverOnline >= maxArenaOnline) {
            AsyncPlayerPreLoginEvent.Result kickResult = AsyncPlayerPreLoginEvent.Result.KICK_OTHER;

            if (getGameManager().getGameSettings().GAME_STATUS == GameStatus.GAME_STARTED) {
                event.disallow(kickResult, "§cИгра на данной арене уже началась!");
                return;
            }

            event.disallow(kickResult, "§cАрена переполнена, попробуйте позже!");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        IVaultPlayer vaultPlayer = BukkitAPI.getInstance().getVaultManager().getVaultPlayer(player);

        int maxArenaOnline = getGameManager().getGameSettings().MAX_ARENA_SLOTS;
        int serverOnline = Bukkit.getOnlinePlayers().size();

        checkGameTimer(serverOnline, maxArenaOnline, true);

        event.setJoinMessage("§6" + getGameManager().getGameSettings().GAME_NAME + " §8| §fИгрок " + vaultPlayer.getDisplayName()
                + " §fприсоединился к игре! §7(" + serverOnline + "/" + maxArenaOnline + ")");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        IVaultPlayer vaultPlayer = BukkitAPI.getInstance().getVaultManager().getVaultPlayer(player);

        int maxArenaOnline = getGameManager().getGameSettings().MAX_ARENA_SLOTS;
        int serverOnline = Bukkit.getOnlinePlayers().size() - 1;

        checkGameTimer(serverOnline, maxArenaOnline, false);

        event.setQuitMessage("§c" + getGameManager().getGameSettings().GAME_NAME + " §8| §fИгрок " + vaultPlayer.getDisplayName()
                + " §fвышел из игры! §7(" + serverOnline + "/" + maxArenaOnline + ")");
    }

    private void checkGameTimer(int serverOnline, int maxArenaOnline, boolean join) {
        AbstractTimerFactory gameTimer = getGameManager().getTimerFactory();

        int onlinePercent = serverOnline / maxArenaOnline * 100;

        if (join) {
            if (onlinePercent >= 75 && !gameTimer.isTimerTick()) {
                gameTimer.start(BukkitAPI.getInstance());
            }
        } else {
            if (onlinePercent < 75 && gameTimer.isTimerTick()) {
                gameTimer.cancel();
            }
        }
    }
}
