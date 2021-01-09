package ru.stonlex.bukkit.gaming.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import ru.stonlex.bukkit.gaming.team.GamingTeam;

@RequiredArgsConstructor
@Getter
public class GameTeamCreateEvent extends GamingEvent {

    private final ChatColor chatColor;
    private final GamingTeam gamingTeam;
}
