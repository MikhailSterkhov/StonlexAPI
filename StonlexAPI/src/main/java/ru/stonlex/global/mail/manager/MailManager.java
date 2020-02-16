package ru.stonlex.global.mail.manager;

import lombok.Getter;
import lombok.Setter;
import ru.stonlex.global.mail.MailSender;
import ru.stonlex.global.utility.AbstractCacheManager;

public final class MailManager extends AbstractCacheManager<MailSender> {

    @Getter
    @Setter
    private String smtpHost = "smtp.yandex.ru";


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
        return get(username, f -> new MailSender(username, username, password, smtpHost));
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
