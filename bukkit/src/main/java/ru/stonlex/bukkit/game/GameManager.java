package ru.stonlex.bukkit.game;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.game.builder.GameBuilder;
import ru.stonlex.bukkit.game.enums.GameSettingsType;
import ru.stonlex.bukkit.game.event.manager.GameEventManager;
import ru.stonlex.bukkit.game.factory.AbstractGameFactory;
import ru.stonlex.bukkit.game.factory.AbstractTimerFactory;
import ru.stonlex.bukkit.game.player.GamePlayer;
import ru.stonlex.bukkit.game.setup.SetupManager;
import ru.stonlex.bukkit.game.team.manager.GameTeamManager;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class GameManager {

    @Getter
    private final GameSettings gameSettings = new GameSettings();

    @Getter
    private final SetupManager setupManager = new SetupManager();

    @Getter
    private final GameEventManager eventManager = new GameEventManager();

    @Getter
    private final GameTeamManager teamManager = new GameTeamManager();


    @Getter
    private final Map<String, GamePlayer> gamePlayerMap = new LinkedHashMap<>();


    @Getter
    @Setter
    private AbstractGameFactory gameFactory;

    @Getter
    @Setter
    private AbstractTimerFactory timerFactory;


    /**
     * Получить игрока по его нику
     *
     * @param playerName - ник игрока
     */
    public GamePlayer getGamePlayer(String playerName) {
        return gamePlayerMap.computeIfAbsent(playerName.toLowerCase(), GamePlayer::new);
    }

    /**
     * Получить игрока
     *
     * @param player - игрок
     */
    public GamePlayer getGamePlayer(Player player) {
        return getGamePlayer(player.getName());
    }

    /**
     * Получить список выживших игроков
     */
    public Collection<GamePlayer> getAlivePlayers() {
        return gamePlayerMap.values().stream()
                .filter(gamePlayer -> !gamePlayer.isSpectate()).collect(Collectors.toList());
    }

    /**
     * Получить список наблюдателей игры
     */
    public Collection<GamePlayer> getSpectatePlayers() {
        return gamePlayerMap.values().stream()
                .filter(GamePlayer::isSpectate).collect(Collectors.toList());
    }

    /**
     * Создать игровой строитель для полной
     * настройки игры
     */
    public GameBuilder newBuilder() {
        return new GameBuilder(this);
    }

    /**
     * Установить шаблон для настройки игры
     *
     * @param settingsType - шаблон/тип для настроек игры
     */
    public void setSettingsType(GameSettingsType settingsType) {
        switch (settingsType) {

            // ======================== // DEFAULT // ======================== //
            case WAIT_LOBBY: {
                gameSettings.PLAYER_MOVE = true;

                gameSettings.BLOCK_BREAK = false;
                gameSettings.BLOCK_PLACE = false;
                gameSettings.LEAVES_DECAY = false;

                gameSettings.PLAYER_DAMAGE = false;
                gameSettings.ENTITY_DAMAGE = false;
                gameSettings.PLAYER_DAMAGE_FALL = false;
                gameSettings.PLAYER_DAMAGE_BY_PLAYER = false;
                gameSettings.PLAYER_DAMAGE_BY_ENTITY = false;
                gameSettings.ENTITY_DAMAGE_BY_PLAYER = false;

                gameSettings.ENTITY_SPAWN = false;

                gameSettings.PLAYER_FOOD_CHANGE = false;

                gameSettings.PLAYER_PICKUP_ITEM = false;
                gameSettings.PLAYER_DROP_ITEM = false;

                gameSettings.WEATHER_CHANGE = false;
                break;
            }

            // ======================== // ALLOW // ======================== //
            case ALLOW_BUILD: {
                gameSettings.BLOCK_BREAK = true;
                gameSettings.BLOCK_PLACE = true;
                break;
            }

            case ALLOW_DAMAGE: {
                gameSettings.PLAYER_DAMAGE = true;
                gameSettings.PLAYER_DAMAGE_FALL = true;
                break;
            }

            case ALLOW_FIGHT: {
                gameSettings.ENTITY_DAMAGE = true;
                gameSettings.PLAYER_DAMAGE_BY_PLAYER = true;
                gameSettings.PLAYER_DAMAGE_BY_ENTITY = true;
                gameSettings.ENTITY_DAMAGE_BY_PLAYER = true;
                break;
            }

            case ALLOW_SPAWN_ENTITY: {
                gameSettings.ENTITY_SPAWN = true;
                break;
            }

            case ALLOW_PLAYER_ACTION_WITH_ITEM: {
                gameSettings.PLAYER_INTERACT = true;
                gameSettings.PLAYER_PICKUP_ITEM = true;
                gameSettings.PLAYER_DROP_ITEM = true;
                break;
            }

            // ======================== // DENY // ======================== //
            case DENY_BUILD: {
                gameSettings.BLOCK_BREAK = false;
                gameSettings.BLOCK_PLACE = false;
                break;
            }

            case DENY_DAMAGE: {
                gameSettings.PLAYER_DAMAGE = false;
                gameSettings.PLAYER_DAMAGE_FALL = false;
                break;
            }

            case DENY_FIGHT: {
                gameSettings.ENTITY_DAMAGE = false;
                gameSettings.PLAYER_DAMAGE_BY_PLAYER = false;
                gameSettings.PLAYER_DAMAGE_BY_ENTITY = false;
                gameSettings.ENTITY_DAMAGE_BY_PLAYER = false;
                break;
            }

            case DENY_SPAWN_ENTITY: {
                gameSettings.ENTITY_SPAWN = false;
                break;
            }

            case DENY_PLAYER_ACTION_WITH_ITEM: {
                gameSettings.PLAYER_INTERACT = false;
                gameSettings.PLAYER_PICKUP_ITEM = false;
                gameSettings.PLAYER_DROP_ITEM = false;
                break;
            }
        }
    }

}
