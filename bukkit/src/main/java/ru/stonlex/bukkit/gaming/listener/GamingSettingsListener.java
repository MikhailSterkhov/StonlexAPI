package ru.stonlex.bukkit.gaming.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.stonlex.bukkit.gaming.GameAPI;
import ru.stonlex.bukkit.gaming.GamingStatus;
import ru.stonlex.bukkit.gaming.player.GamingPlayer;
import ru.stonlex.bukkit.gaming.setting.GamingSettingType;
import ru.stonlex.bukkit.vault.VaultPlayer;

public class GamingSettingsListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event) {

        boolean isKick = GameAPI.SETTINGS.getSetting(GamingSettingType.PLAYER_PRE_LOGIN_KICK).asBoolean();
        String kickMessage = GameAPI.SETTINGS.getSetting(GamingSettingType.PLAYER_PRE_LOGIN_MESSAGE).asString();

        if (isKick) {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
        }

        if (kickMessage != null) {
            event.setKickMessage(kickMessage);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (GameAPI.SETTINGS.getSetting(GamingSettingType.GAME_STATUS).asInt() == GamingStatus.IN_GAME) {
            return;
        }

        GamingPlayer gamingPlayer = GamingPlayer.of(event.getPlayer());
        VaultPlayer vaultPlayer = gamingPlayer.toVault();

        String joinMessage = GameAPI.SETTINGS.getSetting(GamingSettingType.PLAYER_JOIN_MESSAGE)
                .asString();

        if (joinMessage == null) {
            event.setJoinMessage(null);
            return;
        }

        event.setJoinMessage(joinMessage.replace("%prefix%", vaultPlayer.getPrefix())
                .replace("%player%", vaultPlayer.getPlayerName())
                .replace("%suffix%", vaultPlayer.getSuffix()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (GameAPI.SETTINGS.getSetting(GamingSettingType.GAME_STATUS).asInt() == GamingStatus.IN_GAME) {
            return;
        }

        GamingPlayer gamingPlayer = GamingPlayer.of(event.getPlayer());
        VaultPlayer vaultPlayer = gamingPlayer.toVault();

        String quitMessage = GameAPI.SETTINGS.getSetting(GamingSettingType.PLAYER_QUIT_MESSAGE)
                .asString();

        if (quitMessage == null) {
            event.setQuitMessage(null);
            return;
        }

        event.setQuitMessage(quitMessage.replace("%prefix%", vaultPlayer.getPrefix())
                .replace("%player%", vaultPlayer.getPlayerName())
                .replace("%suffix%", vaultPlayer.getSuffix()));
    }
}
