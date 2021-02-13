package ru.stonlex.bukkit.test;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.command.BaseCommand;
import ru.stonlex.bukkit.command.annotation.CommandCooldown;
import ru.stonlex.bukkit.command.annotation.CommandPermission;

@CommandPermission(permission = "stonlexapi.test", message = "§6нет прав.")
@CommandCooldown(cooldownMillis = 1000, receiverModifier = CommandCooldown.ReceiverModifier.ONLY_SENDER)
public class TestCommand extends BaseCommand<Player> {

    /**
     * дженерик <Player> означает, что команда будет выполняться только для игроков
     * <p>
     * судя по вышеприведенным аннотациям, у команды есть право stonlexapi.test, а выполнять
     * ее можно только спустя одну секунду, так как выставлена задержка в 1000 миллисекунд
     */

    public TestCommand() {
        super("test");
    }

    @Override
    protected void onExecute(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage("§c/test <радиус>");

            return;
        }

        Location playerLocation = player.getLocation();

        double radius = Double.parseDouble(args[0]);
        double radian = Math.pow(2, 5);
        EntityType.

        for (double p = 0; p <= 2 * Math.PI; p += Math.PI / 10) {
            for (double t = 0; t <= 360; t += Math.PI / radian) {

                double x = radius * Math.cos(t) * Math.sin(p);
                double y = radius * Math.cos(p) + 1.5;
                double z = radius * Math.sin(t) * Math.sin(p);

                player.spawnParticle(Particle.CLOUD, playerLocation.clone().add(x, y, z), 1, 0, 0, 0, 0);
            }
        }
    }

}
