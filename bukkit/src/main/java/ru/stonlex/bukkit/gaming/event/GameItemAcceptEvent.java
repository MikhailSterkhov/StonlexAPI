package ru.stonlex.bukkit.gaming.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.stonlex.bukkit.gaming.item.GamingItem;
import ru.stonlex.bukkit.gaming.player.GamingPlayer;

@RequiredArgsConstructor
@Getter
public class GameItemAcceptEvent extends GamingEvent {

    private final GamingPlayer gamingPlayer;
    private final GamingItem gamingItem;
}
