package ru.stonlex.bukkit.game.builder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;
import ru.stonlex.bukkit.game.GameManager;
import ru.stonlex.bukkit.game.builder.registry.GameBuilderRegistry;
import ru.stonlex.bukkit.game.enums.GameEvent;
import ru.stonlex.bukkit.game.enums.GameType;
import ru.stonlex.bukkit.game.event.impl.GameFactoryListener;
import ru.stonlex.bukkit.game.event.manager.GameEventManager;
import ru.stonlex.bukkit.game.setup.settings.SetupSettings;
import ru.stonlex.bukkit.game.team.manager.GameTeamManager;

@RequiredArgsConstructor
public final class GameBuilder {

    @Getter
    private final GameManager gameManager;


    /**
     * Инициализировать название игры
     *
     * @param gameName - название игры
     */
    public GameBuilder setGameName(String gameName) {
        gameManager.getGameSettings().GAME_NAME = gameName;

        return this;
    }

    /**
     * Инициализировать название мира игровой арены
     *
     * @param gameWorld - мир арены
     */
    public GameBuilder setGameWorld(String gameWorld) {
        gameManager.getGameSettings().ARENA_WORLD_NAME = gameWorld;

        return this;
    }

    /**
     * Инициализировать тип игры
     *
     * @param gameType - тип игры
     */
    public GameBuilder setGameType(GameType gameType) {
        gameManager.getGameSettings().GAME_TYPE = gameType;
        gameManager.getGameSettings().PLAYERS_IN_TEAM_COUNT = gameType.getPlayersInTeamCount();

        return this;
    }

    /**
     * Установить определенное количество игроков в одной команде
     *
     * @param teamPlayersCount - кол-во игроков в команде
     */
    public GameBuilder setPlayersInTeam(int teamPlayersCount) {
        gameManager.getGameSettings().PLAYERS_IN_TEAM_COUNT = teamPlayersCount;

        return this;
    }

    /**
     * Установить максимальное количество игроков на арене
     *
     * @param maxPlayersCount - кол-во игроков на арене
     */
    public GameBuilder setArenaMaxPlayers(int maxPlayersCount) {
        gameManager.getGameSettings().MAX_ARENA_SLOTS = maxPlayersCount;

        return this;
    }

    /**
     * Зарегистрировать игровые листенеры
     *
     * @param eventRegistry - менеджер регистрации
     */
    public GameBuilder registerListeners(GameBuilderRegistry<GameEventManager> eventRegistry) {
        eventRegistry.applyRegister(gameManager.getEventManager());

        return this;
    }

    /**
     * Зарегистрировать настройки установки арены
     *
     * @param setupRegistry - менеджер регистрации
     */
    public GameBuilder registerSetups(Plugin plugin, GameBuilderRegistry<SetupSettings> setupRegistry) {
        SetupSettings setupSettings = gameManager.getSetupManager().getSetupSettings();

        setupSettings.setPlugin(plugin);

        setupRegistry.applyRegister(setupSettings);
        setupSettings.install();

        return this;
    }

    /**
     * Зарегистрировать игровые команды
     *
     * @param teamRegistry - менеджер регистрации
     */
    public GameBuilder registerTeams(GameBuilderRegistry<GameTeamManager> teamRegistry) {
        teamRegistry.applyRegister(gameManager.getTeamManager());

        return this;
    }

    /**
     * Инициализировать игровую API
     */
    public void create() {
        SetupSettings setupSettings = getGameManager().getSetupManager().getSetupSettings();

        if (setupSettings != null && setupSettings.isSetupMode()) {
            return;
        }

        getGameManager().getEventManager().callGameEvent(GameEvent.REGISTER_GAME);

        getGameManager().getEventManager().registerGameListener(getGameManager().getGameFactory());
        getGameManager().getEventManager().registerGameListener(new GameFactoryListener());
    }

}
