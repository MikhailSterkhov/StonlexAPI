package ru.stonlex.bukkit.vault.manager;

import lombok.Getter;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.vault.player.VaultPlayer;
import ru.stonlex.bukkit.vault.provider.VaultChatManager;
import ru.stonlex.bukkit.vault.provider.VaultEconomyManager;
import ru.stonlex.bukkit.vault.provider.VaultPermissionManager;
import ru.stonlex.global.utility.AbstractCacheManager;

@Getter
public final class VaultManager extends AbstractCacheManager<VaultPlayer> {

    private final VaultEconomyManager economyManager       = new VaultEconomyManager();
    private final VaultPermissionManager permissionManager = new VaultPermissionManager();
    private final VaultChatManager chatManager             = new VaultChatManager();

    /**
     * Получение кешированного VaultPlayer'а по нику игрока
     *
     * Если его нет в мапе, то он автоматически туда добавляется
     */
    public VaultPlayer getVaultPlayer(String playerName) {
        return get(playerName.toLowerCase(), VaultPlayer::new);
    }

    /**
     * Получение кешированного VaultPlayer'а по игроку
     *
     * Если его нет в мапе, то он автоматически туда добавляется
     */
    public VaultPlayer getVaultPlayer(Player player) {
        return getVaultPlayer(player.getName());
    }

}
