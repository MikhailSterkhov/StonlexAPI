package ru.stonlex.bukkit.gaming.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.stonlex.bukkit.gaming.player.GamingPlayer;
import ru.stonlex.bukkit.gaming.team.GamingTeam;

@RequiredArgsConstructor
@Getter
public class GameTeamPlayerRemoveEvent extends GamingEvent {

    private final GamingTeam gamingTeam;
    private final GamingPlayer gamingPlayer;
}
