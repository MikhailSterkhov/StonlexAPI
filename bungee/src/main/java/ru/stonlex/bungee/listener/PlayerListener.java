package ru.stonlex.bungee.listener;

import lombok.Getter;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import ru.stonlex.bungee.event.PlayerConnectEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerListener implements Listener {

    @Getter
    private final Map<String, ProxiedPlayer> joinedPlayerMap = new HashMap<>();


    @EventHandler
    public void onPlayerConnect(ServerConnectedEvent event) {
        ProxiedPlayer proxiedPlayer = event.getPlayer();

        // initialize player connect event
        if (!joinedPlayerMap.containsKey(proxiedPlayer.getName().toLowerCase())) {
            BungeeCord.getInstance().getPluginManager().callEvent(new PlayerConnectEvent(proxiedPlayer));

            joinedPlayerMap.put(proxiedPlayer.getName().toLowerCase(), proxiedPlayer);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerDisconnectEvent event) {
        ProxiedPlayer proxiedPlayer = event.getPlayer();

        joinedPlayerMap.remove(proxiedPlayer.getName().toLowerCase());
    }

    //@EventHandler
    //public void onServerFall(ServerKickEvent event) {
    //    ProxiedPlayer proxiedPlayer = event.getPlayer();
    //    ServerInfo cancelServer = event.getCancelServer();
    //
    //    if (cancelServer == null) {
    //        return;
    //    }
    //
    //    event.setCancelServer(null);
    //    event.setCancelled(true);
    //
    //    proxiedPlayer.connect(BungeeCord.getInstance().getServerInfo("Limbo-1"));
    //
    //    proxiedPlayer.sendMessage("§r ");
    //    proxiedPlayer.sendMessage("§r ");
    //    proxiedPlayer.sendMessage("§cСервер, ан котором Вы находились упал");
    //    proxiedPlayer.sendMessage("§cОтвет от сервера: §e" + event.getKickReason());
    //    proxiedPlayer.sendMessage("§r ");
    //    proxiedPlayer.sendMessage("§r ");
    //}

}
