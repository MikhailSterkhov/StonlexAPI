package ru.stonlex.bukkit.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface CommandCooldown {

    /**
     * Задержка для команды, указанная в
     * миллисекундах
     */
    long cooldownMillis();

    /**
     * Тип объекта, для которого будет
     * выставляться задержка - либо только для
     * того, кто написал данную команду, либо
     * для всех игроков
     */
    EnumReceiver receiverType() default EnumReceiver.ONLY_SENDER;


    /**
     * Типы объектов для задержек
     */
    enum EnumReceiver {

        ONLY_SENDER,
        ALL
    }
}
