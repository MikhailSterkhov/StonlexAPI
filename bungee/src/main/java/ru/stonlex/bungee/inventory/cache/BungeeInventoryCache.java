package ru.stonlex.bungee.inventory.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Getter;
import ru.stonlex.bungee.inventory.BungeeStonlexMenu;

import java.util.concurrent.TimeUnit;

public final class BungeeInventoryCache {

    @Getter
    private static final BungeeInventoryCache instance = new BungeeInventoryCache();

    @Getter
    private final Cache<String, BungeeStonlexMenu> inventoryCache = CacheBuilder.newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build();

}
