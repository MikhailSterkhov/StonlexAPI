package ru.stonlex.bukkit.game.factory;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.game.GameManager;
import ru.stonlex.bukkit.game.GameSettings;
import ru.stonlex.bukkit.game.listener.GameListener;
import ru.stonlex.bukkit.game.player.GamePlayer;
import ru.stonlex.bukkit.game.type.GameType;

import java.util.Collection;

@Getter
public abstract class AbstractGameFactory extends GameListener {

//---------------------------------------------------------------//
    protected final GameManager gameManager = BukkitAPI.getGameManager();

    protected final GameSettings gameSettings = gameManager.getGameSettings();
//---------------------------------------------------------------//

    /**
     * Инициализация некоторых настроек игры
     */
    public AbstractGameFactory(@NonNull GameType gameType, int startSecondsTimer) {
        super(true);

        gameSettings.GAME_TYPE = gameType;
        gameSettings.LOBBY_TIMER_START_SECONDS = startSecondsTimer;
        gameSettings.PLAYERS_IN_TEAM_COUNT = gameType.getPlayersInTeamCount();
    }

    /**
     * Вызывается тогда, когда таймер в лобби заканчивает свою
     * работу и начинается сама игра
     */
    public abstract void onStartGame();

    /**
     * Вызывается при окончании игры с победителями
     */
    public abstract void onStopGame(@NonNull Player... winnerPlayers);

    /**
     * Вызывается тогда, когда необходимо выключить игру
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
            String message = gameSettings.SUCCESSFULLY_PREFIX.concat(text);

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
            String message = gameSettings.SUCCESSFULLY_PREFIX.concat(text);

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
        broadcast(gameManager.getSpectatePlayers(), text);
    }

    /**
     * Вызывается тогда, когда нужно оповестить
     * игроков, что находятся в игре в о чем-либо
     *
     * @param text - Текст оповещения
     */
    protected void broadcastToPlayers(String text) {
        broadcast(gameManager.getAlivePlayers(), text);
    }

}
