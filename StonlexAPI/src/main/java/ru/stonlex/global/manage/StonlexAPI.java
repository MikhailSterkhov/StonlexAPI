package ru.stonlex.global.manage;

import lombok.Getter;
import ru.stonlex.global.group.manager.GroupManager;
import ru.stonlex.global.mail.manager.MailManager;

public final class StonlexAPI {

    @Getter
    private static final GroupManager groupManager = new GroupManager();

    @Getter
    private static final MailManager mailManager = new MailManager();

}
