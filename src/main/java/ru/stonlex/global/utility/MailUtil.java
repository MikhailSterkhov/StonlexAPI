package ru.stonlex.global.utility;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import ru.stonlex.global.mail.MailSender;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class MailUtil {

    @Getter
    @Setter
    private String smtpHost = "smtp.yandex.ru";

    private final Map<String, MailSender> mailSenderMap = new HashMap<>();


    /**
     * Получение MailSender из кеша.
     *
     * Если его там нет, то он автоматически туда добавляется,
     * возвращая тот объект, что был добавлен туда.
     *
     * @param username - имя пользователя отправителя
     * @param password - пароль отправилеля
     */
    public MailSender getMailSender(String username, String password) {
        return mailSenderMap.computeIfAbsent(username, f -> new MailSender(username, username, password, smtpHost));
    }

    /**
     * Отправить сообщение на почту
     *
     * @param subject - тема сообщения
     * @param content - содержимое сообщения
     * @param toMail - email адрес получателя
     */
    public void sendMessage(MailSender mailSender, String subject, String content, String toMail) {
        mailSender.sendMessage(subject, content, toMail);
    }

}
