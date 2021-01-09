package ru.stonlex.bukkit.gaming.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.stonlex.bukkit.gaming.player.GamingPlayer;

@AllArgsConstructor
@Getter
public class GameGhostStatusChangeEvent extends GamingEvent {

    private final GamingPlayer gamingPlayer;

    @Setter
    private boolean ghost;
}
