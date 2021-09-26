package ru.stonlex.bukkit.test;

import com.google.common.base.Joiner;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.command.BaseMegaCommand;
import ru.stonlex.bukkit.command.annotation.CommandCooldown;

@CommandCooldown(cooldownMillis = 1000)
public class TestMegaCommand extends BaseMegaCommand<Player> {

    public TestMegaCommand() {
        super("megatest", "mtest");
    }

    @Override
    protected void onUsage(Player player) {
        player.sendMessage("Включить тест - /test on/enable/start");
        player.sendMessage("Выключить тест - /test off/disable/stop");
    }

    @CommandArgument(aliases = {"enable", "start"})
    public void on(Player player, String... args) {

        player.sendMessage(ChatColor.GREEN + "Тест включен! Аргументы: " + Joiner.on(", ").join(args));
    }

    @CommandArgument(aliases = {"disable", "stop"})
    public void off(Player player, String... args) {

        player.sendMessage(ChatColor.RED + "Тест выключен! Аргументы: " + Joiner.on(", ").join(args));
    }

}
