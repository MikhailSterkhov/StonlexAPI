package ru.stonlex.bukkit.test;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.command.BaseCommand;
import ru.stonlex.bukkit.command.annotation.CommandCooldown;
import ru.stonlex.bukkit.command.annotation.CommandPermission;
import ru.stonlex.global.localtization.LocalizationResource;

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
        player.sendMessage( Lang.of(LanguageType.RUSSIAN, "TEST_LOCALIZED_MESSAGE") );
        player.sendMessage( Lang.of(LanguageType.ENGLISH, "TEST_LOCALIZED_MESSAGE") );
    }

    public static class Lang {

        public static final LocalizationResource RU_LOCALIZATION_RESOURCE = new LocalizationResource("https://raw.githubusercontent.com/ItzStonlex/StonlexAPI/master/example/src/main/resources/ru_lang.yml").initResources();
        public static final LocalizationResource EN_LOCALIZATION_RESOURCE = new LocalizationResource("https://raw.githubusercontent.com/ItzStonlex/StonlexAPI/master/example/src/main/resources/en_lang.yml").initResources();


        public static String of(@NonNull LanguageType languageType, @NonNull String key) {
            switch (languageType) {

                case RUSSIAN:
                    return RU_LOCALIZATION_RESOURCE.getMessage(key);

                case ENGLISH:
                    return EN_LOCALIZATION_RESOURCE.getMessage(key);
            }

            return null;
        }
    }

    public enum LanguageType {

        RUSSIAN,
        ENGLISH,
        ;
    }

}
