package ru.stonlex.bungee;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import ru.stonlex.bungee.listener.PlayerListener;
import ru.stonlex.bungee.messaging.BungeeMessagingManager;

@Getter
public final class StonlexBungeeApi extends Plugin {

    @Getter
    private static StonlexBungeeApi instance; {
        instance = this;
    }

    private final BungeeMessagingManager messagingManager = new BungeeMessagingManager();

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, new PlayerListener());
    }

}
