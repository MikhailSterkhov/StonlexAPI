package ru.stonlex.bukkit.test;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.command.StonlexCommand;
import ru.stonlex.bukkit.command.annotation.CommandCooldown;
import ru.stonlex.bukkit.command.annotation.CommandPermission;
import ru.stonlex.bukkit.depend.protocol.entity.impl.FakePlayer;

import java.util.concurrent.ThreadLocalRandom;

@CommandCooldown(cooldownMillis = 1000, receiverType = CommandCooldown.EnumReceiver.ALL)
@CommandPermission(permission = "stonlexapi.test", message = "§eнет прав.")
public class TestCommand extends StonlexCommand<Player> {

    /**
     * дженерик <Player> означает, что команда будет выполняться только для игроков
     *
     * судя по вышеприведенным аннотациям, у команды есть право stonlexapi.test, а выполнять
     *  ее можно только спустя одну секунду, так как выставлена задержка в 1000 миллисекунд
     */

    @Override
    public void execute(Player player, String[] args) {
        //player.sendMessage("Вы выполнили тестовую команду!");

        FakePlayer fakePlayer = new FakePlayer(player.getName(), player.getLocation());
        fakePlayer.setGlowingColor(ChatColor.RED);

        fakePlayer.spawn();

        new BukkitRunnable() {

            @Override
            public void run() {
                fakePlayer.setGlowingColor(ChatColor.YELLOW);
                fakePlayer.setSneaking(!fakePlayer.isSneaking());
            }

        }.runTaskTimer(BukkitAPI.getInstance(), 20, 20);
    }

}
