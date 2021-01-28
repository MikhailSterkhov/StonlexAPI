package ru.stonlex.bukkit.tag.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.stonlex.bukkit.StonlexBukkitApiPlugin;
import ru.stonlex.bukkit.protocollib.packet.scoreboard.WrapperPlayServerScoreboardTeam;
import ru.stonlex.bukkit.tag.PlayerTag;
import ru.stonlex.bukkit.tag.manager.TagManager;
import ru.stonlex.global.utility.query.AsyncUtil;

public class TagListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        AsyncUtil.submitAsync(() -> TagManager.INSTANCE.getPlayerTagMap().values().forEach(
                playerTag -> playerTag.sendPacket(player, WrapperPlayServerScoreboardTeam.Mode.TEAM_UPDATED)));
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerTag playerTag = TagManager.INSTANCE.getPlayerTag(player);

        if (playerTag == null) {
            return;
        }

        playerTag.broadcastPacket(WrapperPlayServerScoreboardTeam.Mode.PLAYERS_REMOVED);
        playerTag.broadcastPacket(WrapperPlayServerScoreboardTeam.Mode.TEAM_REMOVED);

        TagManager.INSTANCE.getTeamCacheMap().remove(playerTag.getTeamName());
        TagManager.INSTANCE.getPlayerTagMap().remove(player.getName().toLowerCase());
    }

}
