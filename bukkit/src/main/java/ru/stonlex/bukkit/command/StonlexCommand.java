package ru.stonlex.bukkit.command;

import lombok.Getter;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.command.annotation.CommandCooldown;
import ru.stonlex.bukkit.command.annotation.CommandPermission;
import ru.stonlex.global.utility.CooldownUtil;

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


    @Getter
    private final String cooldownName = ("@StonlexCommand=").concat(RandomStringUtils.randomAlphanumeric(64));


    @Override
    public boolean execute(CommandSender commandSender, String label, String[] args) {
        //инициализация аннотаций команды
        CommandCooldown commandCooldown = getClass().getDeclaredAnnotation(CommandCooldown.class);
        CommandPermission commandPermission = getClass().getDeclaredAnnotation(CommandPermission.class);

        //задержка к выполнению команды
        if (commandCooldown != null) {
            if (CooldownUtil.hasCooldown(cooldownName)) {
                return true;
            }

            CooldownUtil.putCooldown(cooldownName, commandCooldown.cooldownMillis());
        }

        //проверка на право для команды
        if (commandPermission != null) {
            if (!commandSender.hasPermission(commandPermission.permission())) {
                commandSender.sendMessage(commandPermission.message());
                return true;
            }
        }

        //выполнение команды
        Class<S> senderClass = (Class<S>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];

        if (!senderClass.isAssignableFrom(CommandSender.class)) {
            boolean senderIsPlayer = senderClass.isAssignableFrom(Player.class);

            if (!(commandSender instanceof Player) && senderIsPlayer) {
                return true;
            }

            if (commandSender instanceof Player && !senderIsPlayer) {
                return true;
            }
        }

        execute((S) commandSender, args);
        return true;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        return true;
    }

    /**
     * execute команды
     *
     * @param commandSender - отправитель
     * @param args - аргументы команды
     */
    public abstract void execute(S commandSender, String[] args);
}
