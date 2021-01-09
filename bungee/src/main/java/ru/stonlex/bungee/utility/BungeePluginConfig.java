package ru.stonlex.bungee.utility;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BungeePluginConfig {

    private final String configPath;

    @Getter
    private Configuration config;


    /**
     * Получить файл по его путю
     */
    public File getFile() {
        return new File(configPath);
    }

    /**
     * Сохранить файл из сорцов
     *
     * @param resourceName - имя ресурса
     * @param plugin - плагин
     */
    public void saveResource(String resourceName, Plugin plugin) {
        InputStream resourceInputStream = plugin.getResourceAsStream(resourceName);
        Path configPath = getFile().toPath();

        try {

            if ( Files.exists(configPath) ) {
                return;
            }

            Files.copy( resourceInputStream, configPath );

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        reloadConfig();
    }

    /**
     * Сохранить файл из сорцов
     *
     * @param plugin - плагин
     */
    public void saveDefaultConfig(Plugin plugin) {
        String dataFolder = plugin.getDataFolder().getName();
        String resourceName = configPath.startsWith(dataFolder) ? configPath.substring(dataFolder.length()) : configPath;

        saveResource( resourceName, plugin );
    }

    /**
     * Перезагрузить полученную конфигурацию
     */
    public void reloadConfig() {
        try {

            ConfigurationProvider configurationProvider = YamlConfiguration.getProvider(YamlConfiguration.class);
            this.config = configurationProvider.load(getFile());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
