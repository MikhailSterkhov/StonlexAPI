package ru.stonlex.example;

import jline.internal.TestAccessible;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import ru.stonlex.bukkit.command.manager.CommandManager;
import ru.stonlex.bukkit.gaming.GamingMode;
import ru.stonlex.bukkit.gaming.GamingProcessBuilder;
import ru.stonlex.bukkit.gaming.team.GamingTeam;
import ru.stonlex.bukkit.holographic.IProtocolHolographic;
import ru.stonlex.bukkit.holographic.impl.QuickStonlexHolographic;
import ru.stonlex.bukkit.protocollib.entity.impl.FakePlayer;
import ru.stonlex.bukkit.scoreboard.BaseScoreboardBuilder;
import ru.stonlex.bukkit.scoreboard.BaseScoreboardScope;
import ru.stonlex.bukkit.scoreboard.animation.ScoreboardDisplayFlickAnimation;
import ru.stonlex.bukkit.utility.location.LocationUtil;
import ru.stonlex.example.command.ExampleConsoleCommand;
import ru.stonlex.example.command.ExamplePlayerCommand;
import ru.stonlex.example.game.ExampleGameCountdown;
import ru.stonlex.example.game.ExampleGameItem;
import ru.stonlex.example.game.ExampleGameProcess;
import ru.stonlex.global.mail.MailSender;
import ru.stonlex.global.utility.MailUtil;

import java.util.function.Consumer;

public final class ApiExample {

    @TestAccessible
    protected void exampleSkyWars() {
        ExampleGameProcess exampleGameProcess = new ExampleGameProcess(GamingMode.SOLO);

        GamingProcessBuilder.newBuilder()
                .name("SkyWars")
                .lobby("SWLobby-1")

                .arena("Ballons")
                .world("Ballons")

                .process(exampleGameProcess)
                .countdown(new ExampleGameCountdown(exampleGameProcess, 2))

                .itemRegistry()
                .item(new ExampleGameItem(100, "Example1", new MaterialData(Material.STONE)))
                .item(new ExampleGameItem(150, "Example2", new MaterialData(Material.EGG)))
                .item(new ExampleGameItem(200, "Example3", new MaterialData(Material.WOOL, (byte) 14)))
                .register()

                .teamRegistry()
                .team(GamingTeam.DEFAULT_RED_TEAM)
                .team(GamingTeam.DEFAULT_BLUE_TEAM)
                .team(GamingTeam.DEFAULT_YELLOW_TEAM)
                .team(GamingTeam.DEFAULT_WHITE_TEAM)
                .register()

                .create(null);
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
        BaseScoreboardBuilder scoreboardBuilder = BaseScoreboardBuilder.newScoreboardBuilder();

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
        CommandManager.INSTANCE.registerCommand(new ExampleConsoleCommand(false), "console", "console-alias");
    }

    @TestAccessible
    protected void exampleHolographic(Player receiver, Location location) {
        IProtocolHolographic protocolHolographic = new QuickStonlexHolographic(location);

        // Создание кликабельных голограмм
        Consumer<Player> playerConsumer = player -> { //player = игрок, который кликнул

            player.sendMessage(ChatColor.GOLD + "Клик по голограмме прошел успешно");
            player.sendMessage(ChatColor.GOLD + "Локация: " + LocationUtil.locationToString(protocolHolographic.getLocation()));
        };

        // Добавление строк в голограмму
        protocolHolographic.addClickHolographicLine(ChatColor.YELLOW + "Разработчик данной API", playerConsumer);
        protocolHolographic.addClickHolographicLine(ChatColor.GREEN + "https://vk.com/itzstonlex", playerConsumer);

        protocolHolographic.showToPlayer(receiver); //заспавнить только для одного игрока
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
