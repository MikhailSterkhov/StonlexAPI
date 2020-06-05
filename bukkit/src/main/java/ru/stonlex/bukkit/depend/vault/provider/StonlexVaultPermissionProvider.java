package ru.stonlex.bukkit.depend.vault.provider;

import lombok.Getter;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import ru.stonlex.bukkit.depend.vault.IVaultProvider;

public class StonlexVaultPermissionProvider implements IVaultProvider<Permission> {

    @Getter
    private Permission provider;


    @Override
    public void registerProvider() {
        RegisteredServiceProvider<Permission> serviceProvider = Bukkit.getServicesManager().getRegistration(Permission.class);

        if (serviceProvider == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Vault service provider '" + getProvider().getClass().getSimpleName() + "' not found.");
            return;
        }

        this.provider = serviceProvider.getProvider();
    }

}
