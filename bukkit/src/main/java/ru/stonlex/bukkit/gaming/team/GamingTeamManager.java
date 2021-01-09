package ru.stonlex.bukkit.gaming.team;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import ru.stonlex.bukkit.gaming.event.GameTeamCreateEvent;

import java.util.HashMap;
import java.util.Map;

public final class GamingTeamManager {

    @Getter
    private final Map<ChatColor, GamingTeam> loadedTeamMap = new HashMap<>();


    /**
     * Получить загруженную игровую команду
     * по ее цвету
     *
     * @param chatColor - цвет команды
     */
    public GamingTeam getGamingTeam(@NonNull ChatColor chatColor) {
        return loadedTeamMap.get(chatColor);
    }

    /**
     * Создать и загрузить игровую команду
     * по ее цвету
     *
     * @param chatColor - цвет игровой команды
     * @param gamingTeam - игровая команда
     */
    public void createGamingTeam(@NonNull ChatColor chatColor, @NonNull GamingTeam gamingTeam) {
        GameTeamCreateEvent createTeamEvent = new GameTeamCreateEvent(chatColor, gamingTeam);
        Bukkit.getPluginManager().callEvent(createTeamEvent);

        if (createTeamEvent.isCancelled()) {
            return;
        }

        loadedTeamMap.put(chatColor, gamingTeam);
    }

    /**
     * Получить количество загруженных
     * игровых команд
     *
     * @return - количество игровых команд
     */
    public int getLoadedTeamsCount() {
        return loadedTeamMap.size();
    }

}
