package ru.stonlex.bukkit.vault.provider;

import lombok.Getter;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class VaultPermissionManager {

    @Getter
    private Permission vaultPermission;

    private static final String NO_INIT_MESSAGE = ChatColor.RED + "Не удалось инициализировать VaultPermission.";

    /**
     * Инициализация vaultPermission
     *
     * Если инициализация прошла неудачно, то в консоль выводит
     * сообщение о том, что vaultPermission == null
     */
    public VaultPermissionManager() {
        RegisteredServiceProvider<Permission> serviceProvider = Bukkit.getServicesManager().getRegistration(Permission.class);

        if (serviceProvider == null || serviceProvider.getProvider() == null) {
            Bukkit.getConsoleSender().sendMessage(NO_INIT_MESSAGE);
            return;
        }

        this.vaultPermission = serviceProvider.getProvider();

        if (vaultPermission == null) {
            Bukkit.getConsoleSender().sendMessage(NO_INIT_MESSAGE);
        }

    }

}
