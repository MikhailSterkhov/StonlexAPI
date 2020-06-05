package ru.stonlex.bukkit.depend.vault.provider;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import ru.stonlex.bukkit.depend.vault.IVaultProvider;

public class StonlexVaultEconomyProvider implements IVaultProvider<Economy> {

    @Getter
    private Economy provider;


    @Override
    public void registerProvider() {
        RegisteredServiceProvider<Economy> serviceProvider = Bukkit.getServicesManager().getRegistration(Economy.class);

        if (serviceProvider == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Vault service provider '" + getProvider().getClass().getSimpleName() + "' not found.");
            return;
        }

        this.provider = serviceProvider.getProvider();
    }

}
