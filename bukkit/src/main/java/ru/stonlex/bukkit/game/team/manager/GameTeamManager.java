package ru.stonlex.bukkit.game.team.manager;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import ru.stonlex.bukkit.game.enums.GameTeamType;
import ru.stonlex.bukkit.game.team.GameTeam;

public final class GameTeamManager {

    @Getter
    private final TIntObjectMap<GameTeam> teamMap = new TIntObjectHashMap<>();

    /**
     * Создать и кешировать команду
     *
     * @param teamType - тип команды
     */
    public void createGameTeam(GameTeamType teamType) {
        GameTeam gameTeam = new GameTeam(teamType);

        teamMap.put(teamType.getTeamId(), gameTeam);
    }

    /**
     * Получить игровую команду из кеша по ее типу
     *
     * @param teamType - тип команды
     */
    public GameTeam getGameTeam(GameTeamType teamType) {
        return teamMap.get(teamType.getTeamId());
    }

}
