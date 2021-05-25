package ru.stonlex.example;

import jline.internal.TestAccessible;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.stonlex.bukkit.StonlexBukkitApi;
import ru.stonlex.bukkit.holographic.ProtocolHolographic;
import ru.stonlex.bukkit.protocollib.entity.impl.FakePlayer;
import ru.stonlex.bukkit.scoreboard.BaseScoreboardBuilder;
import ru.stonlex.bukkit.scoreboard.BaseScoreboardScope;
import ru.stonlex.bukkit.scoreboard.animation.ScoreboardDisplayFlickAnimation;
import ru.stonlex.bukkit.utility.localization.LocalizedPlayer;
import ru.stonlex.bukkit.utility.location.LocationUtil;
import ru.stonlex.example.command.ExampleConsoleCommand;
import ru.stonlex.example.command.ExamplePlayerCommand;
import ru.stonlex.example.configuration.TestConfiguration;
import ru.stonlex.example.localization.ExampleLang;
import ru.stonlex.global.mail.MailSender;
import ru.stonlex.global.utility.MailUtil;

import java.util.function.Consumer;

public final class ApiExample {

    @TestAccessible
    public void exampleLocalization(@NonNull Player player) {
        LocalizedPlayer localizedPlayer = LocalizedPlayer.create(player, ExampleLang.EN_LANGUAGE.getResource());

        // Simple localized title to player
        localizedPlayer.sendTitle("TEST_LOCALIZED_TITLE", "TEST_LOCALIZED_SUBTITLE");

        // Simple localized message to player
        localizedPlayer.sendMessage("TEST_LOCALIZED_MESSAGE");

        // Localized message with placeholders
        localizedPlayer.sendMessage(localizationResource -> localizationResource.getMessage("TEST_LOCALIZED_MESSAGE")

                .replace("%player_name%", player.getName())
                .replace("%player_level%", player.getLevel())
                .replace("%player_gamemode%", player.getGameMode())

                .toText());
    }

    @TestAccessible
    protected void exampleConfiguration(@NonNull JavaPlugin javaPlugin) {
        new TestConfiguration(javaPlugin).createIfNotExists();
    }

    @TestAccessible
    protected void exampleScoreboard(@NonNull Player player) {
        // Анимация Title
        ScoreboardDisplayFlickAnimation displayFlickAnimation = new ScoreboardDisplayFlickAnimation();

        displayFlickAnimation.addColor(ChatColor.RED);
        displayFlickAnimation.addColor(ChatColor.GOLD);
        displayFlickAnimation.addColor(ChatColor.YELLOW);
        displayFlickAnimation.addColor(ChatColor.WHITE);
        displayFlickAnimation.addTextToAnimation("§lANIMATION");

        // Создание своего скорборда
        BaseScoreboardBuilder scoreboardBuilder = StonlexBukkitApi.newScoreboardBuilder();

        scoreboardBuilder.scoreboardDisplay(ChatColor.YELLOW + "TEST TITLE"); //Статический Title скорборда
        scoreboardBuilder.scoreboardDisplay(displayFlickAnimation); // Простейшая анимация Title скорборда

        scoreboardBuilder.scoreboardScope(BaseScoreboardScope.PROTOTYPE); // [Обязательно] Видимость скорборда, подробнее можно посмотреть в самом классе BaseScoreboardScope

        scoreboardBuilder.scoreboardLine(3, "Ваш игровой ник: §cЗагрузка...");
        scoreboardBuilder.scoreboardLine(2, "");
        scoreboardBuilder.scoreboardLine(1, "§ewww.google.com");

        scoreboardBuilder.scoreboardUpdater((baseScoreboard, player1) -> {
            baseScoreboard.updateScoreboardLine(3, player1, "Ваш игровой ник: §c" + player1.getName());

        }, 20);

        // Отправляем его игроку
        scoreboardBuilder.build().setScoreboardToPlayer(player);
    }

    @TestAccessible
    protected void exampleCommand() {

        // Если в классе команды наследуется конструктор из
        //  StonlexCommand, то команды регистрируются автоматически, вызовом
        //  самого конструктора.
        new ExamplePlayerCommand(true);

        // Если же подобных технологий не знаем и не делаем,
        //  то регистрируем сами при помощи CommandFactory
        StonlexBukkitApi.registerCommand(new ExampleConsoleCommand(false), "console", "console-alias");
    }

    @TestAccessible
    protected void exampleHolographic(Player receiver, Location location) {
        ProtocolHolographic protocolHolographic = StonlexBukkitApi.createSimpleHolographic(location);

        // Создание кликабельных голограмм
        Consumer<Player> playerConsumer = player -> { //player = игрок, который кликнул

            player.sendMessage(ChatColor.GOLD + "Клик по голограмме прошел успешно");
            player.sendMessage(ChatColor.GOLD + "Локация: " + LocationUtil.locationToString(protocolHolographic.getLocation()));
        };

        // Добавление строк в голограмму
        protocolHolographic.addClickLine(ChatColor.YELLOW + "Разработчик данной API", playerConsumer);
        protocolHolographic.addClickLine(ChatColor.GREEN + "https://vk.com/itzstonlex", playerConsumer);

        protocolHolographic.addReceivers(receiver); //заспавнить только для одного игрока
    }

    @TestAccessible
    protected void exampleNPC(Player receiver, String playerSkin, Location location) {
        FakePlayer fakePlayer = new FakePlayer(playerSkin, location);

        //создаем желтую подсветку для NPC
        fakePlayer.setGlowingColor(ChatColor.YELLOW);

        //добавить действие при клике на NPC
        fakePlayer.setClickAction( player -> { //player = игрок, который кликнул
            player.sendMessage(ChatColor.GOLD + "Клик по NPC прошел успешно");
            player.sendMessage(ChatColor.GOLD + "Локация: " + LocationUtil.locationToString(fakePlayer.getLocation()));
        });

        fakePlayer.look(receiver); //посмотреть на игрока

        fakePlayer.setBurning(true); //поджечь
        fakePlayer.setSneaking(true); //присесть
        fakePlayer.setInvisible(false); //сделать видимым

        fakePlayer.spawn(); //заспавнить для всех игроков онлайн
        fakePlayer.addViewers(receiver); //заспавнить только для одного игрока
    }

    @TestAccessible
    protected void exampleSendMailMessage(String toMail, String subject, String contentMessage) {
        //создаем и получаем отправителя
        MailSender mailSender = MailUtil.getMailSender(
                "mail_sender@mail.ru", "***password***");

        //кидаем сообщение получателю
        MailUtil.sendMessage(mailSender, subject, contentMessage, toMail);
    }

}
