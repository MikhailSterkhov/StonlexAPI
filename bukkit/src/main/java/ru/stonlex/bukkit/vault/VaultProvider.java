package ru.stonlex.bukkit.vault;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

@RequiredArgsConstructor
@Getter
public class VaultProvider<T> {

    private @NonNull Class<T> vaultProviderClass;

    private T provider;


    public void registerProvider(@NonNull Plugin plugin) {
        RegisteredServiceProvider<T> serviceProvider = plugin.getServer().getServicesManager()
                .getRegistration(vaultProviderClass);

        if (serviceProvider == null || serviceProvider.getProvider() == null) {
            plugin.getLogger().warning(ChatColor.RED + "Vault provider " + vaultProviderClass.getSimpleName() + " not found!");

            return;
        }

        this.provider = serviceProvider.getProvider();
    }

}
