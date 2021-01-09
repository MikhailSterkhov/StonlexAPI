package ru.stonlex.bukkit.vault;

import lombok.Getter;
import lombok.NonNull;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Getter
public final class VaultManager {

    public static final VaultManager INSTANCE = new VaultManager();


    protected final VaultProvider<Economy> economyProvider        = new VaultProvider<>(Economy.class);
    protected final VaultProvider<Permission> permissionProvider  = new VaultProvider<>(Permission.class);
    protected final VaultProvider<Chat> chatProvider              = new VaultProvider<>(Chat.class);

    protected final Map<String, VaultPlayer> vaultPlayerMap = new HashMap<>();


    /**
     * Получить Vault-игрока по его нику
     *
     * @param playerName - ник игрока
     */
    public VaultPlayer getVaultPlayer(@NonNull String playerName) {
        return vaultPlayerMap.computeIfAbsent(playerName.toLowerCase(), VaultPlayer::new);
    }

    /**
     * Получить Vault-игрока по игроку
     *
     * @param player - игрок
     */
    public VaultPlayer getVaultPlayer(@NonNull Player player) {
        return vaultPlayerMap.computeIfAbsent(player.getName().toLowerCase(), VaultPlayer::new);
    }

}
