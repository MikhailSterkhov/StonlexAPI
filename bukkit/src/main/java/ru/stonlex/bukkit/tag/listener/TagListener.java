package ru.stonlex.bukkit.tag.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.stonlex.bukkit.StonlexBukkitApiPlugin;
import ru.stonlex.bukkit.protocollib.packet.scoreboard.WrapperPlayServerScoreboardTeam;
import ru.stonlex.bukkit.tag.PlayerTag;
import ru.stonlex.global.utility.query.AsyncUtil;

public class TagListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        AsyncUtil.submitAsync(() -> StonlexBukkitApiPlugin.getInstance().getTagManager().getPlayerTagMap().values().forEach(
                playerTag -> playerTag.sendPacket(player, WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED)));
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerTag playerTag = StonlexBukkitApiPlugin.getInstance().getTagManager().getPlayerTag(player);

        if (playerTag == null) {
            return;
        }

        playerTag.broadcastPacket(WrapperPlayServerScoreboardTeam.Mode.PLAYERS_REMOVED);
        playerTag.broadcastPacket(WrapperPlayServerScoreboardTeam.Mode.TEAM_REMOVED);

        StonlexBukkitApiPlugin.getInstance().getTagManager().getTeamCacheMap().remove(playerTag.getTeamName());
        StonlexBukkitApiPlugin.getInstance().getTagManager().getPlayerTagMap().remove(player.getName().toLowerCase());
    }

}
