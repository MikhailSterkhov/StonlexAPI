package ru.stonlex.bukkit.event;

import lombok.RequiredArgsConstructor;
import ru.stonlex.bukkit.BukkitAPI;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import ru.stonlex.global.Applicable;
import ru.stonlex.global.Builder;

@RequiredArgsConstructor
public final class EventRegisterBuilder<E extends Event> implements Builder<EventRegister<E>> {

    private Plugin plugin;

    private final Class<E> eventClass;

    private Applicable<E> eventApplicable;


    /**
     * Установить JavaPlugin для ивента
     */
    public EventRegisterBuilder<E> setPlugin(Plugin plugin) {
        this.plugin = plugin;

        return this;
    }

    /**
     * Установить дейстие при вызове ивента
     */
    public EventRegisterBuilder<E> setEventApplicable(Applicable<E> eventApplicable) {
        this.eventApplicable = eventApplicable;

        return this;
    }

    /**
     * Построение и регистрация ивента
     */
    @Override
    public EventRegister<E> build() {
        EventRegister<E> eventRegister = new EventRegister<>(plugin, eventClass, eventApplicable);

        BukkitAPI.getEventRegisterManager().register(eventRegister);

        return eventRegister;
    }

}
