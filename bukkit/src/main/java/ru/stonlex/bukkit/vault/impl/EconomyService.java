package ru.stonlex.bukkit.vault.impl;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import ru.stonlex.bukkit.vault.VaultService;

public class EconomyService extends VaultService<Economy> {

    public EconomyService() {
        super(Economy.class);
    }

    @Override
    public Economy getCurrentService(Server server) {
        RegisteredServiceProvider<Economy> provider = server.getServicesManager().getRegistration(Economy.class);
        if (provider == null || provider.getProvider() == null) {
            return null;
        }

        return provider.getProvider();
    }

    @Override
    public void register(Plugin plugin, ServicePriority priority, Economy instance) {
        plugin.getServer().getServicesManager().register(Economy.class, instance, plugin, priority);
    }

    @Override
    public void register(Plugin plugin, Economy instance) {
        register(plugin, ServicePriority.Normal, instance);
    }

}
