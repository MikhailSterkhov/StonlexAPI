package ru.stonlex.bukkit.test;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.command.BaseCommand;
import ru.stonlex.bukkit.command.annotation.CommandCooldown;
import ru.stonlex.bukkit.command.annotation.CommandPermission;
import ru.stonlex.bukkit.holographic.impl.SimpleHolographic;

@CommandCooldown(
        cooldownMillis = 1000,
        receiverModifier = CommandCooldown.ReceiverModifier.ONLY_SENDER
)
@CommandPermission(
        permission = "stonlexapi.test",
        message = "§eнет прав."
)
public class TestCommand extends BaseCommand<Player> {

    /**
     * дженерик <Player> означает, что команда будет выполняться только для игроков
     *
     * судя по вышеприведенным аннотациям, у команды есть право stonlexapi.test, а выполнять
     *  ее можно только спустя одну секунду, так как выставлена задержка в 1000 миллисекунд
     */

    public TestCommand() {
        super("test");
    }

    @Override
    protected void onExecute(Player player, String[] args) {
        SimpleHolographic stonlexHolographic = new SimpleHolographic(player.getLocation());

        stonlexHolographic.addOriginalHolographicLine("---------");
        stonlexHolographic.addEmptyHolographicLine();

        stonlexHolographic.addClickHolographicLine(ChatColor.AQUA + "Clickable line",
                clickedPlayer -> player.sendMessage("Ты кликнул на кликабельную строку"));

        stonlexHolographic.addHeadHolographicLine(player.getName(), false);

        stonlexHolographic.addOriginalHolographicLine("123");
        stonlexHolographic.addOriginalHolographicLine(ChatColor.GRAY + "321");


        stonlexHolographic.showToPlayer(player);
    }

}
