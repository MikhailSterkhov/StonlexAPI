package ru.stonlex.bukkit.gaming.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.stonlex.bukkit.gaming.GameCountdownProcess;

@RequiredArgsConstructor
@Getter
public class GameCountdownStartEvent extends GamingEvent {

    private final GameCountdownProcess countdownProcess;
}
