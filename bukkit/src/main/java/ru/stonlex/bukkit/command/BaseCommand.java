package ru.stonlex.bukkit.command;

import lombok.Getter;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.command.annotation.CommandCooldown;
import ru.stonlex.bukkit.command.annotation.CommandPermission;
import ru.stonlex.bukkit.command.manager.CommandManager;
import ru.stonlex.global.utility.CooldownUtil;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

public abstract class BaseCommand<S extends CommandSender>
        extends Command
        implements CommandExecutor {

    @Getter
    protected final String cooldownName = ("@StonlexCommand=").concat(RandomStringUtils.randomAlphanumeric(64));


    /**
     * На тот случай, если при регистрации команды
     * нужно указывать только 1 алиас
     *
     * @param command - алиас
     */
    public BaseCommand(String command) {
        this(command, new String[0]);
    }

    /**
     * На тот случай, если у команды несколько
     * вариаций алиасов
     *
     * @param command - главный алиас
     * @param aliases - алиасы
     */
    public BaseCommand(String command, String... aliases) {
        this(false, command, aliases);
    }

    /**
     * На тот случай, если у команды несколько
     * вариаций алиасов
     *
     * @param command - главный алиас
     * @param aliases - алиасы
     */
    public BaseCommand(boolean constructorRegister, String command, String... aliases) {
        super(command, "Command registered by StonlexAPI", ("/").concat(command), Arrays.asList(aliases));

        if (constructorRegister) {
            CommandManager.INSTANCE.registerCommand(this, command, aliases);
        }
    }

    @Override
    public boolean execute(CommandSender commandSender, String label, String[] args) {
        //инициализация аннотаций команды
        CommandCooldown commandCooldown = getClass().getDeclaredAnnotation(CommandCooldown.class);
        CommandPermission commandPermission = getClass().getDeclaredAnnotation(CommandPermission.class);

        //задержка к выполнению команды
        if (commandCooldown != null) {

            switch (commandCooldown.receiverModifier()) {

                case PUBLIC: {
                    if (CooldownUtil.hasCooldown(cooldownName)) {
                        return true;
                    }

                    CooldownUtil.putCooldown(cooldownName, commandCooldown.cooldownMillis());
                    break;
                }

                case ONLY_SENDER: {
                    if (CooldownUtil.hasCooldown(cooldownName.concat("_").concat(commandSender.getName()))) {
                        return true;
                    }

                    CooldownUtil.putCooldown(cooldownName.concat("_").concat(commandSender.getName()), commandCooldown.cooldownMillis());
                    break;
                }
            }
        }

        if (commandPermission != null && !commandSender.hasPermission(commandPermission.permission())) {
            commandSender.sendMessage(commandPermission.message());

            return true;
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

        onExecute((S) commandSender, args);
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
    protected abstract void onExecute(S commandSender, String[] args);
}
