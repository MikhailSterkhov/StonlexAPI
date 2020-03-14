package ru.stonlex.example;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.hologram.StonlexHologram;
import ru.stonlex.bukkit.protocol.entity.impl.FakePlayer;
import ru.stonlex.bukkit.utility.location.LocationUtil;
import ru.stonlex.example.command.ConsoleCommand;
import ru.stonlex.example.command.PlayerCommand;
import ru.stonlex.global.mail.MailSender;
import ru.stonlex.global.utility.MailUtil;

public final class StonlexExample {

    /**
     * Пример регистрации команд
     */
    public void exampleCommandRegister() {
        //Если в классе команды наследуется конструктор из
        //StonlexCommand, то команды регистрируются автоматически, вызовом
        //самого конструктора.
        new PlayerCommand();

        //Если же подобных технологий не знаем и не делаем,
        //то регистрируем сами при помощи CommandFactory
        BukkitAPI.getCommandFactory().registerCommand(new ConsoleCommand(), "console", "console-alias");
    }

    /**
     * Пример создания голограммы
     *
     * @param receiver - (Пример) игрок, которому отправлять голограмму
     * @param location - (Пример) локация, на которой спавнить голограмму
     */
    public void exampleHologram(Player receiver, Location location) {
        StonlexHologram stonlexHologram = BukkitAPI.getHologramManager().createHologram(location);

        //добавить строки в голограмму
        stonlexHologram.addLine(ChatColor.YELLOW + "Разработчик данной API");
        stonlexHologram.addLine(ChatColor.GREEN + "https://vk.com/itzstonlex");

        //добавить действие при клике на нее
        stonlexHologram.setClickAction( player -> { //player = игрок, который кликнул
            player.sendMessage(ChatColor.GOLD + "Клик по голограмме прошел успешно");
            player.sendMessage(ChatColor.GOLD + "Локация: " + LocationUtil.locationToString(stonlexHologram.getLocation()));
        });

        stonlexHologram.spawn(); //заспавнить для всех игроков онлайн
        stonlexHologram.spawnToPlayer(receiver); //заспавнить только для одного игрока
    }

    /**
     * Создать NPC с подсведкой и скином
     *
     * @param receiver - (Пример) игрок, которому отправлять NPC
     * @param playerSkin - (Пример) ник скина NPC
     * @param location - (Пример) локация, на которой спавнить NPC
     */
    public void exampleNPC(Player receiver, String playerSkin, Location location) {
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
        fakePlayer.spawnToPlayer(receiver); //заспавнить только для одного игрока
    }

    /**
     * Отправить сообщение человеку на электронную почту
     *
     * @param toMail - e-mail получателя сообщения
     * @param subject - заголовок сообщения
     * @param contentMessage - текст сообщения
     */
    public void exampleSendMailMessage(String toMail, String subject, String contentMessage) {
        //создаем и получаем отправителя
        MailSender mailSender = MailUtil.getMailSender(
                "mail_sender@mail.ru", "***password***");

        //кидаем сообщение получателю
        MailUtil.sendMessage(mailSender, subject, contentMessage, toMail);
    }

}
