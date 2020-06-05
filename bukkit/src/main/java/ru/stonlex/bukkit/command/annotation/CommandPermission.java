package ru.stonlex.bukkit.command.annotation;

public @interface CommandPermission {

    /**
     * Возвращает право на команду, без котрого
     * отправитель не сможет ее выполнить
     */
    String permission();

    /**
     * Сообщение, которое будет выводиться тогда,
     * когда у отправителя не будет указанного выше права
     */
    String message() default "§cОшибка, у Вас недостаточно прав!";
}
