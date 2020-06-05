package ru.stonlex.bukkit.depend.vault.manager;

import lombok.Getter;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.depend.vault.IVaultPlayer;
import ru.stonlex.bukkit.depend.vault.IVaultProvider;
import ru.stonlex.bukkit.depend.vault.provider.StonlexVaultChatProvider;
import ru.stonlex.bukkit.depend.vault.provider.StonlexVaultEconomyProvider;
import ru.stonlex.bukkit.depend.vault.provider.StonlexVaultPermissionProvider;
import ru.stonlex.global.utility.AbstractCacheManager;

public final class VaultManager extends AbstractCacheManager<IVaultPlayer> {

    @Getter
    private final IVaultProvider<Chat> chatProvider = new StonlexVaultChatProvider();

    @Getter
    private final IVaultProvider<Economy> economyProvider = new StonlexVaultEconomyProvider();

    @Getter
    private final IVaultProvider<Permission> permissionProvider = new StonlexVaultPermissionProvider();


    /**
     * Получить Vault-игрока по его нику
     *
     * @param playerName - ник игрока
     */
    public IVaultPlayer getVaultPlayer(String playerName) {
        return get(playerName.toLowerCase());
    }

    /**
     * Получить Vault-игрока по игроку
     *
     * @param player - игрок
     */
    public IVaultPlayer getVaultPlayer(Player player) {
        return get(player.getName().toLowerCase());
    }

    /**
     * Загрузить Vault-игрока в кеш
     *
     * @param playerName - ник игрока
     * @param vaultPlayer - объект игрока
     */
    public void loadVaultPlayer(String playerName, IVaultPlayer vaultPlayer) {
        cache(playerName.toLowerCase(), vaultPlayer);
    }

    /**
     * Удалить игрока из кеша
     *
     * @param playerName - ник игрока
     */
    public void unloadVaultPlayer(String playerName) {
        cacheMap.remove(playerName.toLowerCase());
    }

}
