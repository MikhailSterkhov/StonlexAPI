package ru.stonlex.bukkit.game.setup;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import ru.stonlex.bukkit.game.setup.settings.SetupSettings;
import ru.stonlex.bukkit.utility.location.LocationUtil;

import java.util.*;

public final class SetupManager {

    @Getter
    private final Map<String, List<Location>> setupMap = new HashMap<>();

    @Getter
    @Setter
    private SetupSettings setupSettings = new SetupSettings();

    /**
     * Добавить значение к ключу установки
     *
     * @param setupKey - ключ
     * @param location - значение
     */
    public void installLocation(String setupKey, Location location) {
        String configSection = getConfigSection(setupKey);

        List<Location> locationList = setupMap.get(configSection);

        if (locationList == null) {
            locationList = new ArrayList<>();
        }

        locationList.add(location);

        setupMap.put(configSection, locationList);
    }

    /**
     * Сохранить созданные данные в конфигурацию
     */
    public void saveToFile() {
        setupMap.forEach((configSection, locationList) -> {
            List<String> stringLocationList = new ArrayList<>();

            for (Location location : locationList) {
                stringLocationList.add(LocationUtil.locationToString(location));
            }

            setupSettings.getPlugin().getConfig().set(configSection, stringLocationList);
        });

        setupSettings.getPlugin().saveConfig();
    }

    /**
     * Получить устаноленные локации по ключу установки
     *
     * @param setupKey - ключ установки
     */
    public List<Location> getInstalledLocations(String setupKey) {
        String configSection = getConfigSection(setupKey);

        List<Location> installedLocations = setupMap.get(configSection);

        if (installedLocations == null) {
            FileConfiguration setupConfig = setupSettings.getPlugin().getConfig();

            installedLocations = new ArrayList<>();

            for (String stringLocation : setupConfig.getStringList(configSection)) {
                installedLocations.add(LocationUtil.stringToLocation(stringLocation));
            }
        }

        setupMap.put(configSection, installedLocations);

        return installedLocations;
    }

    /**
     * Получить устаноленную локацию по ключу установки
     *
     * @param setupKey - ключ установки
     */
    public Location getInstalledLocation(String setupKey) {
        return getInstalledLocations(setupKey)
                .stream()
                .findFirst()
                .orElse(null);
    }

    /**
     * Получить список установочных ключей
     */
    public Set<String> getSetupKeys() {
        return setupSettings.getSetupKeyMap().keySet();
    }

    /**
     * Получить секцию для конфига по ключу установки
     *
     * @param setupKey - клю установки
     */
    public String getConfigSection(String setupKey) {
        return setupSettings.getSetupKeyMap().get(setupKey.toLowerCase());
    }

}
