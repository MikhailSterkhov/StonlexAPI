package ru.stonlex.bukkit.game.factory;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.game.GameSettings;
import ru.stonlex.bukkit.game.enums.GameEvent;
import ru.stonlex.bukkit.game.enums.GameType;
import ru.stonlex.bukkit.game.event.GameListener;
import ru.stonlex.bukkit.game.player.GamePlayer;

import java.util.Collection;

public abstract class AbstractGameFactory extends GameListener {

    private final Plugin plugin;

// ======================================================================================= //

    @Getter
    protected final GameSettings gameSettings = BukkitAPI.getInstance().getGameManager().getGameSettings();

// ======================================================================================= //

    /**
     * Инициализация некоторых настроек игры
     */
    public AbstractGameFactory(@NonNull GameType gameType,
                               @NonNull Plugin plugin,
                               int startSecondsTimer) {

        this.plugin = plugin;

        gameSettings.GAME_TYPE = gameType;
        gameSettings.PLAYERS_IN_TEAM_COUNT = gameType.getPlayersInTeamCount();
        gameSettings.LOBBY_TIMER_START_SECONDS = startSecondsTimer;
    }

    /**
     * Вызывается тогда, когда таймер в лобби заканчивает свою
     * работу и начинается сама игра
     */
    public abstract void onStartGame();

    /**
     * Вызывается при окончании игры
     */
    public abstract void onStopGame();

    /**
     * Вызывается тогда, когда умирает игрок
     *
     * @param gamePlayer - игрок, который умер
     */
    public abstract void onDeath(@NonNull GamePlayer gamePlayer);


    /**
     * Вызывается тогда, когда нужно оповестить всех
     * игроков онлайн о чем-либо
     *
     * @param text - Текст оповещения
     */
    protected void broadcastToAll(String text) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            String message = gameSettings.BROADCAST_PREFIX.concat(text);

            player.sendMessage(message);
        });
    }

    /**
     * Вызывается тогда, когда нужно оповестить каких-то
     * именно игроков о чем-либо
     *
     * @param players - Игроки, которых нужно оповестить
     * @param text - Текст оповещения
     */
    protected void broadcast(Collection<GamePlayer> players, String text) {
        players.forEach(gamePlayer -> {
            String message = gameSettings.BROADCAST_PREFIX.concat(text);

            gamePlayer.getPlayer().sendMessage(message);
        });
    }

    /**
     * Вызывается тогда, когда нужно оповестить
     * наблюдателей в о чем-либо
     *
     * @param text - Текст оповещения
     */
    protected void broadcastToSpectators(String text) {
        broadcast(BukkitAPI.getInstance().getGameManager().getSpectatePlayers(), text);
    }

    /**
     * Вызывается тогда, когда нужно оповестить
     * игроков, что находятся в игре в о чем-либо
     *
     * @param text - Текст оповещения
     */
    protected void broadcastToPlayers(String text) {
        broadcast(BukkitAPI.getInstance().getGameManager().getAlivePlayers(), text);
    }

    /**
     * Принудительно остановить игру
     */
    protected void stop() {
        getGameManager().getEventManager().callGameEvent(GameEvent.RESTART_GAME);

        for (Player player : Bukkit.getOnlinePlayers()) {

            //broadcast
            broadcastToAll("§e[Game] Арена перезагружается!");

            //connect to lobby
            ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();

            dataOutput.writeUTF("Connect");

            dataOutput.writeUTF(player.getName());
            dataOutput.writeUTF(gameSettings.LOBBY_SERVER_NAME);

            Bukkit.getServer().sendPluginMessage(BukkitAPI.getInstance(), "BungeeCord", dataOutput.toByteArray());
        }

        BukkitAPI.getInstance().getGameManager().getGamePlayerMap().clear();

        plugin.onEnable();

        getGameManager().getEventManager().callGameEvent(GameEvent.STOP_GAME);

        Bukkit.unloadWorld(gameSettings.ARENA_WORLD_NAME, false);
        Bukkit.shutdown();
    }

}
