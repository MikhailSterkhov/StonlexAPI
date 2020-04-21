package ru.stonlex.bukkit.game.event.manager;

import lombok.Getter;
import org.bukkit.Bukkit;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.game.enums.GameEvent;
import ru.stonlex.bukkit.game.event.GameListener;
import ru.stonlex.bukkit.game.event.annotation.GEventHandler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GameEventManager {

    @Getter
    private final List<GameListener> listenerList = new ArrayList<>();

    @Getter
    private final Map<GameEvent, List<Method>> eventMap = new HashMap<>();


    /**
     * Зарегистрировать игровой листенер
     *
     * @param gameListener - игровой листенер
     */
    public void registerGameListener(GameListener gameListener) {
        Method[] methodArray = gameListener.getClass().getMethods();

        for (Method eventMethod : methodArray) {
            if (eventMethod.getParameterTypes().length != 0) {
                continue;
            }

            GEventHandler eventHandler = eventMethod.getDeclaredAnnotation(GEventHandler.class);

            if (eventHandler == null) {
                continue;
            }

            registerGameEvent(eventHandler, eventMethod);
        }

        Bukkit.getPluginManager().registerEvents(gameListener, BukkitAPI.getInstance());
    }

    /**
     * Зарегистрировать игровой ивент
     *
     * @param eventHandler - хандлер метода с ивентом
     * @param eventMethod - метод ивента
     */
    private void registerGameEvent(GEventHandler eventHandler, Method eventMethod) {
        GameEvent gameEvent = eventHandler.gameEvent();

        List<Method> eventList = eventMap.get(gameEvent);

        if (eventList == null) {
            eventList = new ArrayList<>();
        }

        eventList.add(eventMethod);

        eventMap.put(gameEvent, eventList);
    }

    /**
     * Вызывать игровой ивент
     *
     * @param gameEvent - тип ивента
     */
    public void callGameEvent(GameEvent gameEvent) {
        try {
            for (GameListener gameListener : listenerList) {
                for (Method eventMethod : eventMap.get(gameEvent)) {

                    eventMethod.invoke(gameListener, gameEvent);
                }
            }
        }
        catch (Exception ignored) {
        }
    }

}
