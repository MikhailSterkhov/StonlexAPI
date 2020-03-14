package ru.stonlex.bukkit.vault.provider;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class VaultEconomyManager {

    @Getter
    private Economy vaultEconomy;

    private static final String NO_INIT_MESSAGE = ChatColor.RED + "Не удалось инициализировать VaultEconomy.";

    /**
     * Инициализация vaultEconomy
     *
     * Если инициализация прошла неудачно, то в консоль выводит
     * сообщение о том, что vaultEconomy == null
     */
    public VaultEconomyManager() {
        RegisteredServiceProvider<Economy> serviceProvider = Bukkit.getServicesManager().getRegistration(Economy.class);

        if (serviceProvider == null || serviceProvider.getProvider() == null) {
            Bukkit.getConsoleSender().sendMessage(NO_INIT_MESSAGE);
            return;
        }

        this.vaultEconomy = serviceProvider.getProvider();

        if (vaultEconomy == null) {
            Bukkit.getConsoleSender().sendMessage(NO_INIT_MESSAGE);
        }

    }

}
