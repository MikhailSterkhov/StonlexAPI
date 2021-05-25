package ru.stonlex.bukkit.inventory.handler.impl;

import lombok.NonNull;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.inventory.BaseInventory;
import ru.stonlex.bukkit.inventory.handler.BaseInventoryHandler;
import ru.stonlex.global.utility.WeakObjectCache;

public interface BaseInventoryUpdateHandler extends BaseInventoryHandler {

    void onUpdate(@NonNull BaseInventory baseInventory, @NonNull Player player);

    @Override
    default void handle(@NonNull BaseInventory baseInventory,
                        WeakObjectCache objectCache) {

        onUpdate(baseInventory, objectCache.getObject(Player.class, "player"));
    }
}
