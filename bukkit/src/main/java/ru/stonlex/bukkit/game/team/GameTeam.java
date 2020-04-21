package ru.stonlex.bukkit.game.team;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.stonlex.bukkit.game.enums.GameTeamType;

import javax.xml.stream.Location;

@RequiredArgsConstructor
public class GameTeam {

    @Getter
    private final GameTeamType teamType;

    @Getter
    @Setter
    private Location spawnLocation;

    @Getter
    @Setter
    private Location coreLocation;
}
