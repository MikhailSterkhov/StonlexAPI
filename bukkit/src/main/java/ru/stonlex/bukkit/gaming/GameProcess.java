package ru.stonlex.bukkit.gaming;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import ru.stonlex.bukkit.gaming.database.GamingDatabase;
import ru.stonlex.bukkit.gaming.database.GamingItemDatabase;
import ru.stonlex.bukkit.gaming.player.GamingPlayer;
import ru.stonlex.bukkit.gaming.setting.GamingSettingType;
import ru.stonlex.bukkit.gaming.setting.GamingSettings;
import ru.stonlex.bukkit.gaming.team.GamingTeam;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class GameProcess {

    @Getter
    protected @NonNull int playersInTeamCount;

    @Setter
    @Getter
    protected int currentStatus;

    protected
    @NonNull GamingSettings gamingSettings = GameAPI.SETTINGS;


// ========================== // DATABASES // ========================== //

    @Setter
    @Getter
    protected GamingDatabase gamingDatabase;

    @Setter
    @Getter
    protected GamingItemDatabase itemDatabase;

// ========================== // DATABASES // ========================== //


    protected abstract void onStart();
    protected abstract void onEnd();

    /**
     * Переопределяющийся метод
     * Будет вызван при смерти живого {@link GamingPlayer}
     *
     * @param gamingPlayer     - игровой пользователь
     * @param playerDeathEvent - событие смерти игрока
     */
    public void onDeath
    (@NonNull GamingPlayer gamingPlayer, @NonNull PlayerDeathEvent playerDeathEvent) {}


    protected Collection<GamingPlayer> getAlivePlayers() {
        return Bukkit.getOnlinePlayers().stream()
                .map(GamingPlayer::of)
                .filter(GamingPlayer::isAlive)
                .collect(Collectors.toList());
    }

    protected Collection<Player> getBukkitAlivePlayers() {
        return Bukkit.getOnlinePlayers().stream()
                .filter(player -> GamingPlayer.of(player).isAlive())
                .collect(Collectors.toList());
    }

    protected Collection<GamingPlayer> getGhostPlayers() {
        return Bukkit.getOnlinePlayers().stream()
                .map(GamingPlayer::of)
                .filter(GamingPlayer::isGhost)
                .collect(Collectors.toList());
    }

    protected Collection<Player> getBukkitGhostPlayers() {
        return Bukkit.getOnlinePlayers().stream()
                .filter(player -> GamingPlayer.of(player).isGhost())
                .collect(Collectors.toList());
    }

    protected void createGamingTeam(@NonNull GamingTeam gamingTeam) {
        GameAPI.TEAM_MANAGER.createGamingTeam(gamingTeam.getChatColor(), gamingTeam);
    }

    protected GamingTeam getGamingTeam(@NonNull ChatColor chatColor) {
        return GameAPI.TEAM_MANAGER.getGamingTeam(chatColor);
    }

    protected int getLoadedTeamsCount() {
        return GameAPI.TEAM_MANAGER.getLoadedTeamsCount();
    }


    protected int getMaxPlayers() {
        return playersInTeamCount * GameAPI.TEAM_MANAGER.getLoadedTeamsCount();
    }


    public void setSetting(@NonNull GamingSettingType settingType, @NonNull Object settingValue) {
        gamingSettings.setSetting(settingType, settingValue);
    }

    public <T> T getSetting(@NonNull GamingSettingType settingType, @NonNull Class<T> settingValueClass) {
        return gamingSettings.getSetting(settingType, settingValueClass);
    }


    public void alert(@NonNull String message) {
        alert(Bukkit.getOnlinePlayers(), message);
    }

    public void alert(@NonNull Collection<? extends Player> playerCollection,
                      @NonNull String message) {

        for (Player player : playerCollection) {
            player.sendMessage(message);
        }
    }

}
