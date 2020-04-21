package ru.stonlex.bukkit.game.event.annotation;

import ru.stonlex.bukkit.game.enums.GameEvent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GEventHandler {

    /**
     * Тип ивента, при вызове котрого будет
     * воспроизводиться метод хандлера
     */
    GameEvent gameEvent();
}
