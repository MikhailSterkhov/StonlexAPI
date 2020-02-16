package ru.stonlex.bukkit.command;

import ru.stonlex.bukkit.BukkitAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public abstract class MoonCommand<S extends CommandSender> extends Command
        implements CommandExecutor {


    /**
     * На тот случай, если авторегистрация команды
     * не нужна
     */
    public MoonCommand() {
        super(null);
    }

    /**
     * На тот случай, если при регистрации команды
     * нужно указывать только 1 алиас
     *
     * @param command - алиас
     */
    public MoonCommand(String command) {
        this();

        BukkitAPI.getCommandFactory().registerCommand(this, command);
    }

    /**
     * На тот случай, если у команды несколько
     * вариаций алиасов
     *
     * @param command - главный алиас
     * @param aliases - алиасы
     */
    public MoonCommand(String command, String aliases) {
        this();

        BukkitAPI.getCommandFactory().registerCommand(this, command, aliases);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        S castedSender = ((S) commandSender);

        //пишем ебаный ИИ
        if ( !castedSender.getClass().equals(CommandSender.class) ) {
            if ( !(commandSender instanceof Player) && castedSender instanceof ConsoleCommandSender ) {
                return true;
            }

            if ( !(commandSender instanceof ConsoleCommandSender) && castedSender instanceof Player ) {
                return true;
            }
        }

        //ну все, щас он поработит галактику
        CommandInfo commandInfo = new CommandInfo(command, label, args);
        execute( castedSender, commandInfo );

        return false;
    }

    /**
     * execute команды
     *
     * @param commandSender - отправитель
     * @param commandInfo - информация о команде
     */
    public abstract void execute(S commandSender, CommandInfo commandInfo);
}
