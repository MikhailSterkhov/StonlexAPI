package ru.stonlex.bukkit.tab.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.depend.protocol.packet.scoreboard.WrapperPlayServerScoreboardTeam;
import ru.stonlex.bukkit.tab.PlayerTag;
import ru.stonlex.global.utility.AsyncUtil;

public class TagListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        AsyncUtil.runAsync(() -> BukkitAPI.getInstance().getTagManager().getPlayerTagMap().values().forEach(
                playerTag -> playerTag.sendPacket(player, WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED)));
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerTag playerTag = BukkitAPI.getInstance().getTagManager().getPlayerTag(player);

        if (playerTag == null) {
            return;
        }

        playerTag.broadcastPacket(WrapperPlayServerScoreboardTeam.Mode.PLAYERS_REMOVED);
        playerTag.broadcastPacket(WrapperPlayServerScoreboardTeam.Mode.TEAM_REMOVED);

        BukkitAPI.getInstance().getTagManager().getTeamCacheMap().remove(playerTag.getTeamName());
        BukkitAPI.getInstance().getTagManager().getPlayerTagMap().remove(player.getName().toLowerCase());
    }

}
