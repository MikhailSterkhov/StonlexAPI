package ru.stonlex.bukkit.tab.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.protocol.packet.scoreboard.WrapperPlayServerScoreboardTeam;
import ru.stonlex.bukkit.tab.PlayerTag;

public class TagListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        BukkitAPI.getTagManager().getPlayerTagMap().values().forEach(
                playerTag -> playerTag.sendPacket(player, WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED));
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerTag playerTag = BukkitAPI.getTagManager().getPlayerTag(player);

        playerTag.broadcastPacket(WrapperPlayServerScoreboardTeam.Mode.PLAYERS_REMOVED);
        playerTag.broadcastPacket(WrapperPlayServerScoreboardTeam.Mode.TEAM_REMOVED);

        BukkitAPI.getTagManager().getPlayerTagMap().remove(player.getName().toLowerCase());
    }

}
