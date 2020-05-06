package ru.stonlex.bukkit.module.protocol.messaging.inventory.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Getter;
import ru.stonlex.bukkit.inventory.impl.StonlexInventory;

import java.util.concurrent.TimeUnit;

public final class InventoryCache {

    @Getter
    private static final InventoryCache instance = new InventoryCache();

    @Getter
    private final Cache<String, StonlexInventory> inventoryCache = CacheBuilder.newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build();

}
