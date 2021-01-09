package ru.stonlex.bungee.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

@RequiredArgsConstructor
@Getter
public class PlayerConnectEvent extends Event {

    private final ProxiedPlayer player;
}
