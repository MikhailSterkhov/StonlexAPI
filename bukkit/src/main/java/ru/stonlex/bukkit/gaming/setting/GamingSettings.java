package ru.stonlex.bukkit.gaming.setting;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import ru.stonlex.bukkit.gaming.event.GameSettingChangeEvent;
import ru.stonlex.global.utility.ValidatedObject;

import java.util.EnumMap;

@RequiredArgsConstructor
public class GamingSettings {

    private final EnumMap<GamingSettingType, Object> settingsMap
            = new EnumMap<>(GamingSettingType.class);


    /**
     * Установить значение для настройки
     * игрового процесса
     *
     * @param settingType  - тип настройки
     * @param settingValue - значение настройки
     */
    public void setSetting(@NonNull GamingSettingType settingType, @NonNull Object settingValue) {
        GameSettingChangeEvent settingChangeEvent
                = new GameSettingChangeEvent(settingType, getSetting(settingType, Object.class), settingValue);

        Bukkit.getPluginManager().callEvent(settingChangeEvent);

        if (!settingChangeEvent.isCancelled()) {
            settingsMap.put(settingType, settingValue);
        }
    }

    /**
     * Проверить наличие заданного значения
     * для указанной настройки
     *
     * @param settingType - тип настройки
     */
    public boolean hasSetting(@NonNull GamingSettingType settingType) {
        return settingsMap.containsKey(settingType);
    }

    /**
     * Получить заданное значение настройки
     * игрового процесса
     *
     * @param settingType       - тип настройки
     * @param settingValueClass - класс получаемого значения настройки
     */
    public <T> T getSetting(@NonNull GamingSettingType settingType,
                            @NonNull Class<T> settingValueClass) {

        return (T) settingsMap.getOrDefault(settingType, settingType.getDefaultValue());
    }

    /**
     * Получить заданное значение настройки
     * игрового процесса
     *
     * @param settingType       - тип настройки
     */
    public ValidatedObject getSetting(@NonNull GamingSettingType settingType) {
        return new ValidatedObject(getSetting(settingType, Object.class));
    }

}
