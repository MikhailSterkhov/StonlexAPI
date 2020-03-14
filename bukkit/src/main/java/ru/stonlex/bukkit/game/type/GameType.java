package ru.stonlex.bukkit.game.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum GameType {

    SOLO("Solo", 1),
    DOUBLES("Doubles", 2),
    TEAM("Team", 4);


    private final String typeName;

    private final int playersInTeamCount;

}
