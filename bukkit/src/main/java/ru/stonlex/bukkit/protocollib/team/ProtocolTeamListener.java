package ru.stonlex.bukkit.protocollib.team;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.stonlex.bukkit.StonlexBukkitApiPlugin;

import java.util.Collection;
import java.util.HashSet;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ProtocolTeamListener implements Listener {

    private final Collection<ProtocolTeam> protocolTeamCollection = new HashSet<>();

    public void addTeam(@NonNull ProtocolTeam protocolTeam) {
        protocolTeamCollection.add(protocolTeam);
    }

    public void removeTeam(@NonNull ProtocolTeam protocolTeam) {
        protocolTeamCollection.remove(protocolTeam);
    }

    public boolean hasTeam(@NonNull ProtocolTeam protocolTeam) {
        return protocolTeamCollection.contains(protocolTeam);
    }


    private void addTeamsReceive(@NonNull Player player) {
        for (ProtocolTeam protocolTeam : protocolTeamCollection)
            protocolTeam.addReceiver(player);
    }

    private void removeTeamsReceive(@NonNull Player player) {
        for (ProtocolTeam protocolTeam : protocolTeamCollection)
            protocolTeam.removeReceiver(player);
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {

        Bukkit.getScheduler().runTaskLater(StonlexBukkitApiPlugin.getPlugin(StonlexBukkitApiPlugin.class),
                () -> addTeamsReceive(event.getPlayer()), 20);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLeave(PlayerQuitEvent event) {

        removeTeamsReceive(event.getPlayer());
    }
}
