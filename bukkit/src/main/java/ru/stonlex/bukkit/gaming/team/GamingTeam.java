package ru.stonlex.bukkit.gaming.team;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.gaming.event.GameTeamCreateEvent;
import ru.stonlex.bukkit.gaming.event.GameTeamPlayerAddEvent;
import ru.stonlex.bukkit.gaming.event.GameTeamPlayerRemoveEvent;
import ru.stonlex.bukkit.gaming.player.GamingPlayer;

import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public class GamingTeam {

    private final int teamIndex;

    private final ChatColor chatColor;
    private final String teamName;

    private final Map<String, GamingPlayer> playerMap = new LinkedHashMap<>();

/* ============================================== // DEFAULT TEAMS // ============================================== */
    public static final GamingTeam DEFAULT_RED_TEAM         = new GamingTeam(1, ChatColor.RED, "Красные");
    public static final GamingTeam DEFAULT_ORANGE_TEAM      = new GamingTeam(2, ChatColor.GOLD, "Оранжевые");
    public static final GamingTeam DEFAULT_YELLOW_TEAM      = new GamingTeam(3, ChatColor.YELLOW, "Желтые");
    public static final GamingTeam DEFAULT_GREEN_TEAM       = new GamingTeam(4, ChatColor.GREEN, "Лаймовые");
    public static final GamingTeam DEFAULT_DARK_GREEN_TEAM  = new GamingTeam(5, ChatColor.DARK_GREEN, "Зеленые");
    public static final GamingTeam DEFAULT_AQUA_TEAM        = new GamingTeam(6, ChatColor.AQUA, "Голубые");
    public static final GamingTeam DEFAULT_DARK_AQUA_TEAM   = new GamingTeam(7, ChatColor.DARK_AQUA, "Бирюзовые");
    public static final GamingTeam DEFAULT_BLUE_TEAM        = new GamingTeam(8, ChatColor.BLUE, "Синий");
    public static final GamingTeam DEFAULT_PINK_TEAM        = new GamingTeam(9, ChatColor.LIGHT_PURPLE, "Розовые");
    public static final GamingTeam DEFAULT_PURPLE_TEAM      = new GamingTeam(10, ChatColor.DARK_PURPLE, "Фиолетовые");
    public static final GamingTeam DEFAULT_WHITE_TEAM       = new GamingTeam(11, ChatColor.WHITE, "Белые");
    public static final GamingTeam DEFAULT_GRAY_TEAM        = new GamingTeam(12, ChatColor.GRAY, "Серые");
/* ============================================== // DEFAULT TEAMS // ============================================== */


    /**
     * Добавить игрока в команду
     *
     * @param gamingPlayer - игровой пользователь,
     *                     которого нужно добавить в команду
     */
    public void addPlayer(@NonNull GamingPlayer gamingPlayer) {
        GameTeamPlayerAddEvent teamPlayerAddEvent = new GameTeamPlayerAddEvent(this, gamingPlayer);
        Bukkit.getPluginManager().callEvent(teamPlayerAddEvent);

        if (teamPlayerAddEvent.isCancelled()) {
            return;
        }

        playerMap.put(gamingPlayer.getPlayerName().toLowerCase(), gamingPlayer);
        gamingPlayer.setCurrentTeam(this);
    }

    /**
     * Добавить игрока в команду
     *
     * @param player - Bukkit игрок,
     *               которого нужно добавить в команду
     */
    public void addPlayer(@NonNull Player player) {
        addPlayer(GamingPlayer.of(player));
    }

    /**
     * Добавить игрока в команду
     *
     * @param playerName - ник игрока,
     *                   которого нужно добавить в команду
     */
    public void addPlayer(@NonNull String playerName) {
        addPlayer(GamingPlayer.of(playerName));
    }


    /**
     * Удалить игрока из команды
     *
     * @param gamingPlayer - игровой пользователь,
     *                     которого нужно удалить из команды
     */
    public void removePlayer(@NonNull GamingPlayer gamingPlayer) {
        GameTeamPlayerRemoveEvent teamPlayerRemoveEvent = new GameTeamPlayerRemoveEvent(this, gamingPlayer);
        Bukkit.getPluginManager().callEvent(teamPlayerRemoveEvent);

        if (teamPlayerRemoveEvent.isCancelled()) {
            return;
        }

        playerMap.remove(gamingPlayer.getPlayerName().toLowerCase());
        gamingPlayer.setCurrentTeam(null);
    }

    /**
     * Удалить игрока из команды
     *
     * @param player - Bukkit игрок,
     *               которого нужно удалить из команды
     */
    public void removePlayer(@NonNull Player player) {
        removePlayer(GamingPlayer.of(player));
    }

    /**
     * Удалить игрока из команды
     *
     * @param playerName - ник игрока,
     *                   которого нужно удалить из команды
     */
    public void removePlayer(@NonNull String playerName) {
        removePlayer(GamingPlayer.of(playerName));
    }


    /**
     * Проверить наличие игрока в команде
     *
     * @param gamingPlayer - игровой пользователь,
     *                     наличие которого нужно проверить
     */
    public boolean hasPlayer(@NonNull GamingPlayer gamingPlayer) {
        return playerMap.containsKey(gamingPlayer.getPlayerName().toLowerCase());
    }

    /**
     * Проверить наличие игрока в команде
     *
     * @param player - Bukkit игрок,
     *               наличие которого нужно проверить
     */
    public boolean hasPlayer(@NonNull Player player) {
        return hasPlayer(GamingPlayer.of(player));
    }

    /**
     * Проверить наличие игрока в команде
     *
     * @param playerName - ник игрока,
     *                   наличие которого нужно проверить
     */
    public boolean hasPlayer(@NonNull String playerName) {
        return hasPlayer(GamingPlayer.of(playerName));
    }

}
