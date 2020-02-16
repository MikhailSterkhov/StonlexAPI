package ru.stonlex.bukkit.command.factory;

import com.google.common.collect.Lists;
import lombok.Getter;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.command.MoonCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class CommandFactory {

    @Getter
    private final List<MoonCommand> commandList = new ArrayList<>();

    private static CommandMap commandMap;


    /**
     * Регистрация комманд при помощи org.bukkit.command.CommandMap
     *
     *  (Код старый, переписывать его было лень, так как он и так
     *   стабильно и правильно работает. Сделал его только чуток красивее)
     *
     * @param moonCommand - команда
     * @param command - главная команда
     * @param aliases - ее алиасы
     */
    public void registerCommand(MoonCommand moonCommand,
                                String command, String... aliases) {
        commandList.add(moonCommand);

        moonCommand.setName(command);
        moonCommand.setAliases(Lists.newArrayList(aliases));
        moonCommand.setUsage("");
        moonCommand.setDescription("");

        try {
            if (commandMap == null) {
                String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

                Class<?> craftServerClass = Class.forName("org.bukkit.craftbukkit." + version + ".CraftServer");
                Object craftServerObject = craftServerClass.cast((Object) Bukkit.getServer());
                Field commandMapField = craftServerClass.getDeclaredField("commandMap");

                commandMapField.setAccessible(true);

                commandMap = (SimpleCommandMap)commandMapField.get(craftServerObject);
            }

            commandMap.register(BukkitAPI.getPlugin(BukkitAPI.class).getDescription().getName(), moonCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
