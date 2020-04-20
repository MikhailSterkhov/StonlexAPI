package ru.stonlex.bukkit.protocol.messaging.inventory.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Getter;
import ru.stonlex.bukkit.menu.StonlexMenu;

import java.util.concurrent.TimeUnit;

public final class InventoryCache {

    @Getter
    private static final InventoryCache instance = new InventoryCache();

    @Getter
    private final Cache<String, StonlexMenu> inventoryCache = CacheBuilder.newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build();

}
