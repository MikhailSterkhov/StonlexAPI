package ru.stonlex.bukkit.inventory.handler.impl;

import lombok.NonNull;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.inventory.BaseInventory;
import ru.stonlex.bukkit.inventory.handler.BaseInventoryHandler;
import ru.stonlex.global.utility.WeakObjectCache;

public interface BaseInventoryDisplayableHandler extends BaseInventoryHandler {

    void onOpen(@NonNull Player player);
    void onClose(@NonNull Player player);

    @Override
    default void handle(@NonNull BaseInventory baseInventory,
                        WeakObjectCache objectCache) {

        Player player = objectCache.getObject(Player.class, "player");
        boolean isOpen = objectCache.getObject(boolean.class, "isOpen");

        if (isOpen) {
            onOpen(player);
        } else {
            onClose(player);
        }
    }
}
