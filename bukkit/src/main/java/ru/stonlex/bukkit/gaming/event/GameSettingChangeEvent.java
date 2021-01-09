package ru.stonlex.bukkit.gaming.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.stonlex.bukkit.gaming.setting.GamingSettingType;

@RequiredArgsConstructor
@Getter
public class GameSettingChangeEvent extends GamingEvent {

    private final GamingSettingType settingType;

    private final Object previousValue;
    private final Object currentValue;
}
