package ru.stonlex.bukkit.gaming;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import ru.stonlex.bukkit.StonlexBukkitApiPlugin;
import ru.stonlex.bukkit.gaming.item.GamingItem;
import ru.stonlex.bukkit.gaming.item.GamingItemManager;
import ru.stonlex.bukkit.gaming.listener.GamingPlayerDatabaseListener;
import ru.stonlex.bukkit.gaming.listener.GamingProcessListener;
import ru.stonlex.bukkit.gaming.listener.GamingSettingsListener;
import ru.stonlex.bukkit.gaming.setting.GamingSettingType;
import ru.stonlex.bukkit.gaming.team.GamingTeam;
import ru.stonlex.bukkit.gaming.team.GamingTeamManager;

import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GamingProcessBuilder {

    private GameProcess gameProcess;
    private GameCountdownProcess countdownProcess;

    private String lobbyServer;
    private String gameName;
    private String arenaName;
    private String worldName;

    private final ItemRegistry itemRegistry = new ItemRegistry(this);
    private final TeamRegistry teamRegistry = new TeamRegistry(this);


    public GamingProcessBuilder process(@NonNull GameProcess gameProcess) {
        this.gameProcess = gameProcess;
        return this;
    }

    public GamingProcessBuilder countdown(@NonNull GameCountdownProcess countdownProcess) {
        this.countdownProcess = countdownProcess;
        return this;
    }

    public GamingProcessBuilder lobby(@NonNull String lobbyServer) {
        this.lobbyServer = lobbyServer;
        return this;
    }

    public GamingProcessBuilder name(@NonNull String gameName) {
        this.gameName = gameName;
        return this;
    }

    public GamingProcessBuilder arena(@NonNull String arenaName) {
        this.arenaName = arenaName;
        return this;
    }

    public GamingProcessBuilder world(@NonNull World world) {
        this.worldName = world.getName();
        return this;
    }

    public GamingProcessBuilder world(@NonNull String worldName) {
        this.worldName = worldName;
        return this;
    }


    public TeamRegistry teamRegistry() {
        return teamRegistry;
    }

    public ItemRegistry itemRegistry() {
        return itemRegistry;
    }


    public void create(Consumer<GameProcess> gameProcessConsumer) {
        Preconditions.checkArgument(gameProcess != null, "Game process can`t be null");

        Preconditions.checkArgument(lobbyServer != null, "Lobby server name can`t be null");
        Preconditions.checkArgument(gameName != null, "Game name can`t be null");
        Preconditions.checkArgument(arenaName != null, "Arena name can`t be null");
        Preconditions.checkArgument(worldName != null, "Arena world name can`t be null");

        gameProcess.setSetting(GamingSettingType.LOBBY_SERVER, lobbyServer);
        gameProcess.setSetting(GamingSettingType.GAME_NAME, gameName);
        gameProcess.setSetting(GamingSettingType.ARENA_NAME, arenaName);
        gameProcess.setSetting(GamingSettingType.ARENA_WORLD, worldName);

        if (countdownProcess != null) {
            countdownProcess.enableCountdown();
        }
        else {
            gameProcess.onStart();
        }

        Bukkit.getPluginManager().registerEvents(new GamingPlayerDatabaseListener(gameProcess), StonlexBukkitApiPlugin.getInstance());
        Bukkit.getPluginManager().registerEvents(new GamingProcessListener(gameProcess), StonlexBukkitApiPlugin.getInstance());
        Bukkit.getPluginManager().registerEvents(new GamingSettingsListener(), StonlexBukkitApiPlugin.getInstance());

        gameProcessConsumer.accept(gameProcess);
    }


    public static GamingProcessBuilder newBuilder() {
        return new GamingProcessBuilder();
    }


    @RequiredArgsConstructor
    public static class ItemRegistry {

        private @NonNull GamingProcessBuilder gamingProcessBuilder;
        private @NonNull GamingItemManager itemManager = GameAPI.ITEM_MANAGER;

        public ItemRegistry item(@NonNull GamingItem gamingItem) {
            itemManager.registerItem(gamingItem);
            return this;
        }

        public GamingProcessBuilder register() {
            return gamingProcessBuilder;
        }
    }

    @RequiredArgsConstructor
    public static class TeamRegistry {

        private @NonNull GamingProcessBuilder gamingProcessBuilder;
        private @NonNull GamingTeamManager teamManager = GameAPI.TEAM_MANAGER;

        public TeamRegistry team(@NonNull GamingTeam gamingTeam) {
            teamManager.createGamingTeam(gamingTeam.getChatColor(), gamingTeam);
            return this;
        }

        public GamingProcessBuilder register() {
            return gamingProcessBuilder;
        }
    }

}
