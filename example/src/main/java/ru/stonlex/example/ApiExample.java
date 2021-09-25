package ru.stonlex.example;

import jline.internal.TestAccessible;
import lombok.NonNull;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.utility.localization.LocalizedPlayer;
import ru.stonlex.example.database.TestDatabaseEventListener;
import ru.stonlex.example.localization.ExampleLang;
import ru.stonlex.global.database.*;
import ru.stonlex.global.database.query.RemoteDatabaseRowType;
import ru.stonlex.global.database.query.row.TypedQueryRow;
import ru.stonlex.global.database.query.row.ValueQueryRow;
import ru.stonlex.global.mail.MailSender;
import ru.stonlex.global.utility.DateUtil;
import ru.stonlex.global.utility.MailUtil;

import java.sql.Timestamp;

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
    protected void exampleSendMailMessage(String toMail, String subject, String contentMessage) {
        //создаем и получаем отправителя
        MailSender mailSender = MailUtil.getMailSender(
                "mail_sender@mail.ru", "***password***");

        //кидаем сообщение получателю
        MailUtil.sendMessage(mailSender, subject, contentMessage, toMail);
    }

    @TestAccessible
    protected void exampleRemoteDatabase() {

        // Создание подключения к базе данных.
        RemoteDatabasesApi remoteDatabasesApi = RemoteDatabasesApi.getInstance();
        RemoteDatabaseConnectionFields connectionFields = remoteDatabasesApi.createConnectionFields("localhost", "root", "", "test");

        RemoteDatabaseConnectionHandler connectionHandler = remoteDatabasesApi.createHikariConnection(connectionFields);

        // Установка обработчика событий (необязательно)
        connectionHandler.setEventHandler(new TestDatabaseEventListener());

        // Выполнение строковых запросов.
        {
            RemoteDatabaseExecuteHandler executeHandler = connectionHandler.getExecuteHandler();

            // 1: Запрос на создание таблицы
            executeHandler.executeUpdate(true, "CREATE TABLE `PlayerIdentifiers` (`Id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, `Name` TEXT NOT NULL, `JoinDate` TIMESTAMP)");

            // 2: Запрос на изменение данных
            executeHandler.executeUpdate(false, "INSERT INTO `Humans` VALUES (?, ?)", "Миша Лейн", new Timestamp(System.currentTimeMillis()));

            // 3: Запрос на получение данных
            executeHandler.executeQuery(true, "SELECT * FROM `Humans` WHERE `Name`=?", "Миша Лейн")
                    .thenAccept(result -> {

                        if (!result.next()) {
                            return;
                        }

                        int userId = result.getInt("Id");

                        Timestamp registerTime = result.getTimestamp("RegisterTime");

                        System.out.println("(ID: " + userId + ") Дата регистрации - " + DateUtil.formatTime(registerTime.getTime(), DateUtil.DEFAULT_DATE_PATTERN));
                    });
        }

        // Выполнение запросов через билдер-паттерн.
        {
            // Сначала получим таблицу Humans
            RemoteDatabaseTable humansTable = connectionHandler.createOrGetTable("Humans", createTableQuery -> {

                createTableQuery.push(TypedQueryRow.createPrimaryNotNull(RemoteDatabaseRowType.INT, "Id")
                        .putIndex(TypedQueryRow.IndexType.AUTO_INCREMENT));

                createTableQuery.push(TypedQueryRow.create(RemoteDatabaseRowType.VAR_CHAR, "Name")
                        .putIndex(TypedQueryRow.IndexType.NOT_NULL));

                createTableQuery.push(TypedQueryRow.create(RemoteDatabaseRowType.TIMESTAMP, "RegisterTime"));
                createTableQuery.executeSync(connectionHandler);
            });

            // 1: Отправим запрос на создание строчки
            humansTable.newDatabaseQuery()
                    .insertQuery()

                    .push(ValueQueryRow.create("Name", "Миша Лейн"))
                    .push(ValueQueryRow.create("RegisterTime", new Timestamp(System.currentTimeMillis())))

                    .executeSync(connectionHandler);

            // 2: Запрос на получение данных из только что созданной строчки, где имя = Миша Лейн
            humansTable.newDatabaseQuery()
                    .selectQuery()

                    .push(ValueQueryRow.create("Name", "Миша Лейн"))

                    .executeQueryAsync(connectionHandler)
                    .thenAccept(result -> {

                        if (!result.next()) {
                            return;
                        }

                        int userId = result.getInt("Id");

                        Timestamp registerTime = result.getTimestamp("RegisterTime");

                        System.out.println("(ID: " + userId + ") Дата регистрации - " + DateUtil.formatTime(registerTime.getTime(), DateUtil.DEFAULT_DATE_PATTERN));
                    });
        }

        // Закрываем соединение с базой данных (если это конец работы с ней, конечно).
        connectionHandler.handleDisconnect();
    }

}
