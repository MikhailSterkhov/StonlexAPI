package ru.stonlex.bukkit.depend.vault.provider;

import lombok.Getter;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import ru.stonlex.bukkit.depend.vault.IVaultProvider;

public class StonlexVaultChatProvider implements IVaultProvider<Chat> {

    @Getter
    private Chat provider;


    @Override
    public void registerProvider() {
        RegisteredServiceProvider<Chat> serviceProvider = Bukkit.getServicesManager().getRegistration(Chat.class);

        if (serviceProvider == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Vault service provider '" + getProvider().getClass().getSimpleName() + "' not found.");
            return;
        }

        this.provider = serviceProvider.getProvider();
    }

}
