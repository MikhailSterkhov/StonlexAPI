package ru.stonlex.example.configuration;

import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import ru.stonlex.bukkit.configuration.BaseConfiguration;

import java.util.HashMap;
import java.util.Map;

public class TestConfiguration extends BaseConfiguration {

    protected final Map<String, String> messagesCacheMap = new HashMap<>();

    public TestConfiguration(@NonNull Plugin plugin) {
        super(plugin, "messages.yml");
    }

    @Override
    protected void onInstall(@NonNull FileConfiguration fileConfiguration) {
        for (String messageKey : fileConfiguration.getConfigurationSection("Messages").getKeys(false)) {

            String messageText = ChatColor.translateAlternateColorCodes('&', fileConfiguration.getString("Messages.".concat(messageKey)));
            messagesCacheMap.put(messageKey, messageText);
        }
    }

}
