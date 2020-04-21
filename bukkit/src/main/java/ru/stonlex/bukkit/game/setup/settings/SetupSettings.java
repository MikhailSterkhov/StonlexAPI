package ru.stonlex.bukkit.game.setup.settings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.plugin.Plugin;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.game.setup.SetupManager;
import ru.stonlex.bukkit.game.setup.command.SetupCommand;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class SetupSettings {

    @Getter
    private final Map<String, String> setupKeyMap = new HashMap<>();

    @Getter
    @Setter
    private Plugin plugin;

    @Getter
    @Setter
    private boolean setupMode;


    /**
     * Создать ключ установки
     *
     * @param setupKey - ключ установки
     * @param configSection - конфигурационная секция ключа
     */
    public void addSetupKey(String setupKey, String configSection) {
        setupKeyMap.put(setupKey.toLowerCase(), configSection);
    }

    /**
     * Установить настройки менеджеру и
     * зарегистрировать команду установки арены
     */
    public void install() {
        SetupManager setupManager = BukkitAPI.getInstance().getGameManager().getSetupManager();
        setupManager.setSetupSettings(this);

        if (isSetupMode()) {
            BukkitAPI.getInstance().getCommandManager().registerCommand(new SetupCommand(), "setup", "gamesetup");
        }
    }

}
