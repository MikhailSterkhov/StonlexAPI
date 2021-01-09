package ru.stonlex.bukkit.gaming.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class GamingEvent
        extends Event
        implements Cancellable {

    @Getter
    @Setter
    private boolean cancelled;


    @Getter
    public static final HandlerList handlerList = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
