package ru.stonlex.bukkit.vault.impl;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import ru.stonlex.bukkit.vault.VaultService;

public class ChatService extends VaultService<Chat> {

    public ChatService() {
        super(Chat.class);
    }

    @Override
    public Chat getCurrentService(Server server) {
        RegisteredServiceProvider<Chat> provider = server.getServicesManager().getRegistration(Chat.class);
        if (provider == null || provider.getProvider() == null) {
            return null;
        }

        return provider.getProvider();
    }

    @Override
    public void register(Plugin plugin, ServicePriority priority, Chat instance) {
        plugin.getServer().getServicesManager().register(Chat.class, instance, plugin, priority);
    }

    @Override
    public void register(Plugin plugin, Chat instance) {
        register(plugin, ServicePriority.Normal, instance);
    }

}
