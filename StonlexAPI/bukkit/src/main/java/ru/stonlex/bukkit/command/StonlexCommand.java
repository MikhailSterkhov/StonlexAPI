package ru.stonlex.bukkit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.BukkitAPI;

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

        BukkitAPI.getCommandFactory().registerCommand(this, command);
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

        BukkitAPI.getCommandFactory().registerCommand(this, command, aliases);
    }

    @Override
    public boolean execute(CommandSender commandSender, String label, String[] args) {
        S castedSender = ((S) commandSender);

        // пишем ебаный ИИ
        if ( !castedSender.getClass().equals(CommandSender.class) ) {
            // пошел через костыли, ибо нормально работать он никак не хочет...
            //  от слова совсем...
            boolean isPlayer = getClass().getGenericSuperclass().getTypeName().contains("org.bukkit.entity.Player");

            if ( !(commandSender instanceof Player) && isPlayer ) {
                return true;
            }

            if ( commandSender instanceof Player && !isPlayer ) {
                return true;
            }
        }

        // ну все, щас он поработит галактику
        CommandInfo commandInfo = new CommandInfo(this, label, args);
        execute( castedSender, commandInfo );

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
     * @param commandInfo - информация о команде
     */
    public abstract void execute(S commandSender, CommandInfo commandInfo);
}
