package ru.stonlex.bukkit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.BukkitAPI;

import java.lang.reflect.ParameterizedType;

public abstract class StonlexCommand<S extends CommandSender>
        extends Command
        implements CommandExecutor {


    /**
     * На тот случай, если авторегистрация команды
     * не нужна
     */
    public StonlexCommand() {
        super(null);
    }

    /**
     * На тот случай, если при регистрации команды
     * нужно указывать только 1 алиас
     *
     * @param command - алиас
     */
    public StonlexCommand(String command) {
        this();

        BukkitAPI.getInstance().getCommandManager().registerCommand(this, command);
    }

    /**
     * На тот случай, если у команды несколько
     * вариаций алиасов
     *
     * @param command - главный алиас
     * @param aliases - алиасы
     */
    public StonlexCommand(String command, String aliases) {
        this();

        BukkitAPI.getInstance().getCommandManager().registerCommand(this, command, aliases);
    }

    @Override
    public boolean execute(CommandSender commandSender, String label, String[] args) {
        Class<S> senderClass = (Class<S>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];

        // пишем ебаный ИИ
        if (!senderClass.isAssignableFrom(CommandSender.class)) {
            boolean senderIsPlayer = senderClass.isAssignableFrom(Player.class);

            if (!(commandSender instanceof Player) && senderIsPlayer) {
                return true;
            }

            if (commandSender instanceof Player && !senderIsPlayer) {
                return true;
            }
        }

        // ну все, щас он поработит галактику
        execute((S) commandSender, args);
        return false;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        return false;
    }

    /**
     * execute команды
     *
     * @param commandSender - отправитель
     * @param args - аргументы команды
     */
    public abstract void execute(S commandSender, String[] args);
}
