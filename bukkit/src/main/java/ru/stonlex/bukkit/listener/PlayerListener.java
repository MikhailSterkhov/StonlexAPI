package ru.stonlex.bukkit.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.depend.vault.player.StonlexVaultPlayer;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        BukkitAPI.getInstance().getVaultManager().loadVaultPlayer(
                player.getName().toLowerCase(), new StonlexVaultPlayer(player.getName()));
    }

}
