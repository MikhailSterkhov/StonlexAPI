package ru.stonlex.bukkit.game.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import ru.stonlex.bukkit.BukkitAPI;

public abstract class GameListener implements Listener {

    public GameListener(boolean autoRegister) {
        if ( !autoRegister ) {
            return;
        }

        register();
    }

    public void register() {
        Bukkit.getPluginManager().registerEvents(this, BukkitAPI.getPlugin(BukkitAPI.class));
    }

}
