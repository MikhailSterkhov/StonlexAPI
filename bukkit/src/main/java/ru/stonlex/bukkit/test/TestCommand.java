package ru.stonlex.bukkit.test;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.command.BaseCommand;
import ru.stonlex.bukkit.command.annotation.CommandCooldown;
import ru.stonlex.bukkit.command.annotation.CommandPermission;
import ru.stonlex.bukkit.holographic.impl.SimpleHolographic;
import ru.stonlex.global.localization.LocalizationResource;

@CommandPermission(permission = "stonlexapi.test", message = "§6нет прав.")
@CommandCooldown(cooldownMillis = 1000, receiverModifier = CommandCooldown.ReceiverModifier.ONLY_SENDER)
public class TestCommand extends BaseCommand<Player> {

    /**
     * Дженерик <Player> означает, что команда будет выполняться только для игроков
     * <p>
     * Судя по вышеприведенным аннотациям, у команды есть право stonlexapi.test, а выполнять
     * ее можно только спустя одну секунду, так как выставлена задержка в 1.000 миллисекунд
     */

    public TestCommand() {
        super("test");
    }

    @Override
    protected void onExecute(Player player, String[] args) {
        SimpleHolographic holographic = new SimpleHolographic(player.getLocation());


        holographic.addTextLine(ChatColor.AQUA + "Text line");
        holographic.addClickLine(ChatColor.YELLOW + "[Click line]", player1 -> player1.sendMessage("ладно"));

        holographic.addDropLine(new ItemStack(Material.GOLDEN_APPLE));
        holographic.addTextLine(ChatColor.AQUA + "Test line 1");

        holographic.addSkullLine("ItzStonlex", false);
        holographic.addSkullLine("md_5", true);

        holographic.addTextLine(ChatColor.AQUA + "Test line 2");


        holographic.setFullClickAction(interactedPlayer -> interactedPlayer.sendMessage("зач кликнул? иди поспи"));
        holographic.addReceivers(player);
    }

    @Getter
    @RequiredArgsConstructor
    public enum Lang {

        RU_LANGUAGE(0, "ru", "Русский", LocalizationResource.create().initResources("https://raw.githubusercontent.com/ItzStonlex/StonlexAPI/master/example/src/main/resources/ru_lang.yml")),
        EN_LANGUAGE(1, "en", "English", LocalizationResource.create().initResources("https://raw.githubusercontent.com/ItzStonlex/StonlexAPI/master/example/src/main/resources/en_lang.yml"));

        private final int langId;

        private final String langCode;
        private final String langName;

        private final LocalizationResource resource;


        public String of(@NonNull String key) {
            return resource.getText(key);
        }

    }

}
