package ru.stonlex.bukkit.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import ru.stonlex.global.Applicable;

@RequiredArgsConstructor
@Getter
public class EventRegister<E extends Event> implements Listener {

    private final Plugin plugin;

    private final Class<E> eventClass;

    private final Applicable<E> eventApplicable;


    @EventHandler
    public void onCallEvent(E event) {
        eventApplicable.apply(event);
    }

}
