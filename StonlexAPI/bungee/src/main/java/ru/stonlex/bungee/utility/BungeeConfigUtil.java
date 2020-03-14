package ru.stonlex.bungee.utility;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.plugin.Plugin;
import ru.stonlex.bungee.config.BungeePluginConfig;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class BungeeConfigUtil {

    @Getter
    private final Map<Plugin, BungeePluginConfig> pluginConfigMap = new HashMap<>();

    public BungeePluginConfig getPluginConfig(Plugin plugin) {
        return pluginConfigMap.computeIfAbsent(plugin, f -> new BungeePluginConfig(plugin.getDataFolder() + File.separator + "config.yml"));
    }

}
