package ru.stonlex.bukkit.vault.provider;

import lombok.Getter;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class VaultChatManager {

    @Getter
    private Chat vaultChat;

    private static final String NO_INIT_MESSAGE = ChatColor.RED + "Не удалось инициализировать VaultChat.";

    /**
     * Инициализация vaultChat
     *
     * Если инициализация прошла неудачно, то в консоль выводит
     * сообщение о том, что vaultChat == null
     */
    public VaultChatManager() {
        RegisteredServiceProvider<Chat> serviceProvider = Bukkit.getServicesManager().getRegistration(Chat.class);

        if (serviceProvider == null || serviceProvider.getProvider() == null) {
            Bukkit.getConsoleSender().sendMessage(NO_INIT_MESSAGE);
            return;
        }

        this.vaultChat = serviceProvider.getProvider();

        if (vaultChat == null) {
            Bukkit.getConsoleSender().sendMessage(NO_INIT_MESSAGE);
        }

    }

}
