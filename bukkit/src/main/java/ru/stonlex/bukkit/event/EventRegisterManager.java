package ru.stonlex.bukkit.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

public final class EventRegisterManager {

    /**
     * Вызов нового Builder
     */
    public <E extends Event> EventRegisterBuilder<E> newBuilder(Class<E> eventClass) {
        return new EventRegisterBuilder<>(eventClass);
    }

    /**
     * Регистрация EventRegister
     */
    public <E extends Event> void register(EventRegister<E> eventRegister) {

        Bukkit.getServer().getPluginManager().registerEvent(eventRegister.getEventClass(), eventRegister, EventPriority.MONITOR,
                (listener, event) -> eventRegister.getEventApplicable().apply((E) event), eventRegister.getPlugin());
    }

}
