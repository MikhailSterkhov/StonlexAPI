package ru.stonlex.bukkit.game.setup.command;

import org.bukkit.entity.Player;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.command.StonlexCommand;
import ru.stonlex.bukkit.game.setup.SetupManager;

public class SetupCommand extends StonlexCommand<Player> {

    @Override
    public void execute(Player player, String[] args) {
        SetupManager setupManager = BukkitAPI.getInstance().getGameManager().getSetupManager();

        if (args.length == 0) {
            player.sendMessage("§6GameSetup §8| §fПомощь по установке арены:");

            setupManager.getSetupKeys().forEach(setupKey -> {
                String configSection = setupManager.getConfigSection(setupKey);

                player.sendMessage("§6* §f/setup " + setupKey + " §7- установить секцию " + configSection);
            });

            return;
        }

        if (!setupManager.getSetupKeys().contains(args[0])) {
            player.sendMessage("§cОшибка, данного ключа установки не существует!");
            return;
        }

        player.sendMessage("§6GameSetup §8| §fКлюч §e" + args[0].toLowerCase() + " §fуспешно установил локацию в секции §e"
                + setupManager.getConfigSection(args[0]));

        setupManager.installLocation(args[0], player.getLocation());
        setupManager.saveToFile();
    }

}
