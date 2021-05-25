package ru.stonlex.bukkit.inventory.handler.impl;

import lombok.NonNull;
import org.bukkit.event.inventory.InventoryClickEvent;
import ru.stonlex.bukkit.inventory.BaseInventory;
import ru.stonlex.bukkit.inventory.handler.BaseInventoryHandler;
import ru.stonlex.global.utility.WeakObjectCache;

public interface BaseInventoryClickHandler extends BaseInventoryHandler {

    void onClick(@NonNull BaseInventory baseInventory, @NonNull InventoryClickEvent inventoryClickEvent);

    @Override
    default void handle(@NonNull BaseInventory baseInventory,
                        WeakObjectCache objectCache) {

        onClick(baseInventory, objectCache.getObject(InventoryClickEvent.class, "event"));
    }
}
