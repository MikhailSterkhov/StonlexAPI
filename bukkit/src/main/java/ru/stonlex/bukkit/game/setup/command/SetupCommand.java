package ru.stonlex.bukkit.game.setup.command;

import org.bukkit.entity.Player;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.command.CommandInfo;
import ru.stonlex.bukkit.command.StonlexCommand;
import ru.stonlex.bukkit.game.setup.SetupManager;

public class SetupCommand extends StonlexCommand<Player> {

    @Override
    public void execute(Player player, CommandInfo commandInfo) {
        SetupManager setupManager = BukkitAPI.getInstance().getGameManager().getSetupManager();

        if (commandInfo.args.length == 0) {
            player.sendMessage("§6GameSetup §8| §fПомощь по установке арены:");

            setupManager.getSetupKeys().forEach(setupKey -> {
                String configSection = setupManager.getConfigSection(setupKey);

                player.sendMessage("§6* §f/setup " + setupKey + " §7- установить секцию " + configSection);
            });

            return;
        }

        if (!setupManager.getSetupKeys().contains(commandInfo.args[0])) {
            player.sendMessage("§cОшибка, данного ключа установки не существует!");
            return;
        }

        player.sendMessage("§6GameSetup §8| §fКлюч §e" + commandInfo.args[0].toLowerCase() + " §fуспешно установил локацию в секции §e"
                + setupManager.getConfigSection(commandInfo.args[0]));

        setupManager.installLocation(commandInfo.args[0], player.getLocation());
        setupManager.saveToFile();
    }

}
