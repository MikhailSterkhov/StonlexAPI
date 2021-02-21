package ru.stonlex.bukkit.test;

import lombok.NonNull;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.command.BaseCommand;
import ru.stonlex.bukkit.command.annotation.CommandCooldown;
import ru.stonlex.bukkit.command.annotation.CommandPermission;
import ru.stonlex.bukkit.utility.localization.LocalizedPlayer;
import ru.stonlex.global.localization.LanguageType;
import ru.stonlex.global.localization.LocalizationResource;

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
        LocalizedPlayer localizedPlayer = LocalizedPlayer.create(player, Lang.EN_LANGUAGE.getLocalizationResource());

        localizedPlayer.sendMessage("TEST_LOCALIZED_MESSAGE");
        localizedPlayer.sendTitle("TEST_LOCALIZED_TITLE", "TEST_LOCALIZED_SUBTITLE");
    }

    public static class Lang {

        public static final LanguageType RU_LANGUAGE = LanguageType.create(0, "ru", "Русский", LocalizationResource.create().initResources("https://raw.githubusercontent.com/ItzStonlex/StonlexAPI/master/example/src/main/resources/ru_lang.yml"));
        public static final LanguageType EN_LANGUAGE = LanguageType.create(1, "en", "English", LocalizationResource.create().initResources("https://raw.githubusercontent.com/ItzStonlex/StonlexAPI/master/example/src/main/resources/en_lang.yml"));

        public static String of(@NonNull LanguageType languageType, @NonNull String key) {
            return languageType.getLocalizationResource().getText(key);
        }

    }

}
