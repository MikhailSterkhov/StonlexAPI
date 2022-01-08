package ru.stonlex.bukkit.vault.impl;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import ru.stonlex.bukkit.vault.VaultService;

public class PermissionService extends VaultService<Permission> {

    public PermissionService() {
        super(Permission.class);
    }

    @Override
    public Permission getCurrentService(Server server) {
        RegisteredServiceProvider<Permission> provider = server.getServicesManager().getRegistration(Permission.class);
        if (provider == null || provider.getProvider() == null) {
            return null;
        }

        return provider.getProvider();
    }

    @Override
    public void register(Plugin plugin, ServicePriority priority, Permission instance) {
        plugin.getServer().getServicesManager().register(Permission.class, instance, plugin, priority);
    }

    @Override
    public void register(Plugin plugin, Permission instance) {
        register(plugin, ServicePriority.Normal, instance);
    }

}
