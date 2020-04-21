package ru.stonlex.bukkit.game.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public enum GameTeamType {

    RED(1, ChatColor.RED, "Красные"),
    BLUE(2, ChatColor.BLUE, "Синие"),
    YELLOW(3, ChatColor.YELLOW, "Желтые"),
    GREEN(4, ChatColor.DARK_GREEN, "Зеленые"),
    LIME(5, ChatColor.GREEN, "Лаймовые"),
    WHITE(6, ChatColor.WHITE, "Белые"),
    CYAN(7, ChatColor.DARK_AQUA, "Бирюзовые"),
    MAGENTA(8, ChatColor.LIGHT_PURPLE, "Розовые"),
    ORANGE(9, ChatColor.GOLD, "Оранжевые"),
    AQUA(10, ChatColor.AQUA, "Голубые"),
    GRAY(11, ChatColor.GRAY, "Серые"),
    PURPLE(12, ChatColor.DARK_PURPLE, "Фиолетовые");


    @Getter
    private final int teamId;

    @Getter
    private final ChatColor chatColor;

    @Getter
    private final String teamName;

    @Getter
    private final List<String> playerInTeamList = new ArrayList<>();

    /**
     * Добавить игрока в команду
     *
     * @param playerName - ник игрока
     */
    public void addPlayerToTeam(String playerName) {
        playerInTeamList.add(playerName);
    }

    /**
     * Удалить игрока из команды
     *
     * @param playerName - ник игрока
     */
    public void removePlayerToTeam(String playerName) {
        playerInTeamList.remove(playerName);
    }

    /**
     * Проверить, находится ли игрок в данной команде
     *
     * @param playerName - ник игрока
     */
    public boolean hasPlayerInTeam(String playerName) {
        return playerInTeamList.contains(playerName);
    }

    /**
     * Получить количество игроков в команде
     */
    public int getPlayersCount() {
        return playerInTeamList.size();
    }

}
