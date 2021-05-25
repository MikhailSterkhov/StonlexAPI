package ru.stonlex.bukkit.inventory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import ru.stonlex.bukkit.inventory.handler.BaseInventoryHandler;
import ru.stonlex.bukkit.inventory.update.BaseInventoryUpdateTask;
import ru.stonlex.global.utility.WeakObjectCache;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Getter
public final class BaseInventoryManager {

    private final BaseInventoryHandlerManager inventoryHandlerManager = new BaseInventoryHandlerManager();
    private final Cache<Player, BaseInventory> inventoryCache = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build();

    private final Map<BaseInventory, BaseInventoryUpdateTask> inventoryUpdateTaskMap = new ConcurrentHashMap<>();

    /**
     * Кешировать инвентарь, открытый игроку
     *
     * @param player         - игроку
     * @param baseInventory - инвентарь
     */
    public void createInventory(@NonNull Player player, @NonNull BaseInventory baseInventory) {
        inventoryCache.put(player, baseInventory);
    }

    /**
     * Удалить из кеша инвентарь, недавно открытый игроку
     *
     * @param player         - игроку
     * @param baseInventory - инвентарь
     */
    public void removeInventory(@NonNull Player player, @NonNull BaseInventory baseInventory) {
        inventoryCache.asMap().remove(player, baseInventory);
        player.closeInventory();
    }

    /**
     * Получить открытый инвентарь игрока
     *
     * @param player - игрок
     */
    public BaseInventory getPlayerInventory(@NonNull Player player) {
        inventoryCache.cleanUp();

        return inventoryCache.asMap().get(player);
    }

    public void startInventoryUpdateTask(@NonNull Plugin plugin) {
        new BukkitRunnable() {
            long expireSecond;

            @Override
            public void run() {
                for (BaseInventoryUpdateTask baseInventoryUpdateTask : inventoryUpdateTaskMap.values()) {
                    if (expireSecond % baseInventoryUpdateTask.getUpdateTaskDelay() != 0) {
                        continue;
                    }

                    baseInventoryUpdateTask.getInventoryUpdateTask().run();
                }

                expireSecond++;
            }

        }.runTaskTimer(plugin, 0, 20);
    }

    public void addInventoryUpdateTask(@NonNull BaseInventory baseInventory, @NonNull BaseInventoryUpdateTask inventoryUpdateTask) {
        if (inventoryUpdateTaskMap.containsKey(baseInventory)) {
            return;
        }

        inventoryUpdateTaskMap.put(baseInventory, inventoryUpdateTask);
    }

    public void removeInventoryUpdateTask(@NonNull BaseInventory baseInventory) {
        inventoryUpdateTaskMap.remove(baseInventory);
    }


    @Getter
    public static final class BaseInventoryHandlerManager {

        /**
         * Добавить новый хандлер в список
         * обработчиков инвентаря
         *
         * @param baseInventory        - инвентарь
         * @param baseInventoryHandler - обработчик
         */
        public <T extends BaseInventoryHandler> void add(@NonNull BaseInventory baseInventory,
                                                         @NonNull Class<T> handlerClass,
                                                         @NonNull T baseInventoryHandler) {

            baseInventory.getInventoryInfo().addHandler(handlerClass, baseInventoryHandler);
        }

        /**
         * Получить список хандлеров инвентаря
         * по указанному типу обработчика
         *
         * @param baseInventory        - инвентарь
         * @param inventoryHandlerClass - класс обработчика
         */
        public <T extends BaseInventoryHandler> Collection<T> get(@NonNull BaseInventory baseInventory,
                                                                   @NonNull Class<? extends T> inventoryHandlerClass) {

            return (Collection<T>) baseInventory.getInventoryInfo().getHandlers(inventoryHandlerClass);
        }

        /**
         * Получить первый хандлер по типу его класса
         * из списка обработчиков инвентаря
         *
         * @param baseInventory        - инвентарь
         * @param inventoryHandlerClass - класс обработчика
         */
        public <T extends BaseInventoryHandler> T getFirst(@NonNull BaseInventory baseInventory,
                                                            @NonNull Class<T> inventoryHandlerClass) {

            return baseInventory.getInventoryInfo().getFirstHandler(inventoryHandlerClass);
        }


        public void handle(@NonNull BaseInventory baseInventory,
                           @NonNull Class<? extends BaseInventoryHandler> inventoryHandlerClass,

                           WeakObjectCache weakObjectCache) {

            baseInventory.getInventoryInfo().handleHandlers(inventoryHandlerClass, weakObjectCache);
        }

    }
}
