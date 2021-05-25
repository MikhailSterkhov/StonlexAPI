package ru.stonlex.bukkit.inventory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.inventory.handler.BaseInventoryHandler;
import ru.stonlex.bukkit.inventory.handler.impl.BaseInventoryDisplayableHandler;
import ru.stonlex.bukkit.inventory.handler.impl.BaseInventoryUpdateHandler;
import ru.stonlex.global.utility.WeakObjectCache;

import java.util.ArrayList;
import java.util.Collection;

public interface BaseInventory {

    /**
     * Добавить новый обработчик
     * событий данному инвентарю
     *
     * @param baseInventoryHandler - обработчик событий
     */
    default <T extends BaseInventoryHandler> void addHandler(@NonNull Class<T> handlerClass, @NonNull T baseInventoryHandler) {
        getInventoryInfo().addHandler(handlerClass, baseInventoryHandler);
    }


    /**
     * Открыть инвентарь игроку
     */
    void openInventory(@NonNull Player player);

    /**
     * Открыть инвентарь игроку, используя
     * обработчик открытия и закрытия данного инвентаря
     *
     * @param inventoryDisplayableHandler - обработчик закрытия и открытия инвентаря
     */
    void openInventory(@NonNull Player player, @NonNull BaseInventoryDisplayableHandler inventoryDisplayableHandler);


    /**
     * Единаразово очистить инвентарь
     */
    void clearInventory();


    /**
     * Единоразово обновить инвентарь и
     * все предметы внутри игроку
     */
    void updateInventory(@NonNull Player player);

    /**
     * Единоразово обновить инвентарь и
     * все предметы внутри игроку, используя
     * обработчик обновления инвентаря
     *
     * @param player - игрок
     * @param inventoryUpdateHandler - обработчик обновления инвентаря
     */
    void updateInventory(@NonNull Player player, @NonNull BaseInventoryUpdateHandler inventoryUpdateHandler);

    /**
     * Включить цикличное автообновление инвентаря
     * до закрытия его игроком
     *
     * @param inventoryUpdateHandler - обработчик обновления инвентаря
     */
    void enableAutoUpdate(@NonNull Player player, BaseInventoryUpdateHandler inventoryUpdateHandler, long duration);


    /**
     * Переопределяющийся метод
     * <p>
     * Отрисовка и настройка инвентаря, установка
     * предметов и разметка
     *
     * @param player - игрок
     */
    void drawInventory(@NonNull Player player);

    /**
     * Закрыть инвентарь игроку, вызывав
     * при наличии обработчик закрытия инвентаря
     */
    void closeInventory(@NonNull Player player);


    /**
     * Отрисовать предмет в инвентаре
     *
     * @param baseInventoryItem - предмет и его функции
     */
    void addItem(@NonNull BaseInventoryItem baseInventoryItem);


    /**
     * Установить разметку инвентаря для
     * разрешенных мест в установке предметов
     *
     * @param baseInventoryMarkup - разметка инвентаря
     */
    void setItemMarkup(@NonNull BaseInventoryMarkup baseInventoryMarkup);


    /**
     * Получить обработчик информации инвентаря,
     * который хранит в себе как базовые поля,
     * так и различные списки предметов, хандлеров и т.д.
     */
    BaseInventoryInfo getInventoryInfo();

    BaseInventorySettings getInventorySettings();


    @Getter
    @RequiredArgsConstructor
    class BaseInventoryInfo {

        private final BaseInventory baseInventory;

        private final String inventoryTitle;
        private final int inventorySize, inventoryRows;

        private final Multimap<Class<? extends BaseInventoryHandler>, BaseInventoryHandler> inventoryHandlerMap   = HashMultimap.create();
        private final TIntObjectMap<BaseInventoryItem> inventoryItemMap                                            = new TIntObjectHashMap<>();


        public <T extends BaseInventoryHandler> void addHandler(@NonNull Class<T> handlerClass,
                                                                 @NonNull T baseInventoryHandler) {

            inventoryHandlerMap.put(handlerClass, baseInventoryHandler);
        }

        public void addItem(int itemSlot, @NonNull BaseInventoryItem baseInventoryItem) {
            inventoryItemMap.put(itemSlot, baseInventoryItem);
        }


        public <T extends BaseInventoryHandler> T getFirstHandler(@NonNull Class<T> inventoryHandlerClass) {
            return getHandlers(inventoryHandlerClass).stream().findFirst().orElse(null);
        }

        public <T extends BaseInventoryHandler> Collection<T> getHandlers(@NonNull Class<T> inventoryHandlerClass) {
            Class<T> handlerClassKey = (Class<T>) inventoryHandlerMap.keySet().stream()
                    .filter(handlerClass -> handlerClass.isAssignableFrom(inventoryHandlerClass))
                    .findFirst()
                    .orElse(null);

            if (handlerClassKey == null) {
                return new ArrayList<>();
            }

            return (Collection<T>) inventoryHandlerMap.get(handlerClassKey);
        }

        public BaseInventoryItem getItem(int itemSlot) {
            return inventoryItemMap.get(itemSlot);
        }

        public <T extends BaseInventoryHandler> void handleHandlers(@NonNull Class<T> inventoryHandlerClass,
                                                                     WeakObjectCache objectCache) {

            Collection<T> inventoryHandlerCollection = getHandlers(inventoryHandlerClass);

            for (BaseInventoryHandler inventoryHandler : inventoryHandlerCollection) {
                inventoryHandler.handle(baseInventory, objectCache);
            }
        }
    }

    @Getter
    @Setter
    class BaseInventorySettings {

        protected boolean useOnlyCacheItems = false;
    }

}
