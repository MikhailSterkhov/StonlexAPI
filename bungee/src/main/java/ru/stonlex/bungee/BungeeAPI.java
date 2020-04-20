package ru.stonlex.bungee;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import ru.stonlex.bungee.listener.PlayerListener;

public final class BungeeAPI extends Plugin {

    @Getter
    private static BungeeAPI instance; {
        instance = this;
    }


    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, new PlayerListener());
    }

}
