package ru.stonlex.bukkit.gaming.item;

import lombok.NonNull;
import ru.stonlex.bukkit.gaming.player.GamingPlayer;

import java.util.HashMap;
import java.util.Map;

public final class GamingItemManager {

    private final
    Map<Class<? extends GamingItem>, GamingItem> gamingItemMap = new HashMap<>();


    /**
     * Зарегистрировать игровой предмет
     *
     * @param gamingItem - тип класса игрового предмета
     */
    public void registerItem(@NonNull GamingItem gamingItem) {
        gamingItemMap.put(gamingItem.getClass(), gamingItem);
    }

    /**
     * Получить игровой предмет
     * по типу его класса
     *
     * @param gamingItemClass - тип класса игрового предмета
     */
    public GamingItem getGamingItem(@NonNull Class<? extends GamingItem> gamingItemClass) {
        return gamingItemMap.get(gamingItemClass);
    }

}
