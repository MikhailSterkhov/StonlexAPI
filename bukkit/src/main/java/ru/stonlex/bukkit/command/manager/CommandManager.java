package ru.stonlex.bukkit.command.manager;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.command.StonlexCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class CommandManager {

    @Getter
    private final List<StonlexCommand> commandList = new ArrayList<>();

    private static CommandMap commandMap;


    /**
     * Регистрация комманд при помощи org.bukkit.command.CommandMap
     *
     *  (Код старый, переписывать его было лень, так как он и так
     *   стабильно и правильно работает. Сделал его только чуток красивее)
     *
     * @param stonlexCommand - команда
     * @param command - главная команда
     * @param aliases - ее алиасы
     */
    public void registerCommand(StonlexCommand stonlexCommand,
                                String command, String... aliases) {
        registerCommand(BukkitAPI.getInstance(), stonlexCommand, command, aliases);
    }

    /**
     * Регистрация комманд при помощи org.bukkit.command.CommandMap
     *
     *  (Код старый, переписывать его было лень, так как он и так
     *   стабильно и правильно работает. Сделал его только чуток красивее)
     *
     * @param plugin - плагин, от имени котрого регистрируется команда
     * @param stonlexCommand - команда
     * @param command - главная команда
     * @param aliases - ее алиасы
     */
    public void registerCommand(Plugin plugin, StonlexCommand stonlexCommand,
                                String command, String... aliases) {
        commandList.add(stonlexCommand);

        stonlexCommand.setLabel(command);
        stonlexCommand.setName(command);
        stonlexCommand.setAliases(Lists.newArrayList(aliases));

        try {
            if (commandMap == null) {
                String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

                Class<?> craftServerClass = Class.forName("org.bukkit.craftbukkit." + version + ".CraftServer");
                Object craftServerObject = craftServerClass.cast((Object) Bukkit.getServer());
                Field commandMapField = craftServerClass.getDeclaredField("commandMap");

                commandMapField.setAccessible(true);

                commandMap = (SimpleCommandMap)commandMapField.get(craftServerObject);
            }

            commandMap.register(plugin.getName(), stonlexCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
