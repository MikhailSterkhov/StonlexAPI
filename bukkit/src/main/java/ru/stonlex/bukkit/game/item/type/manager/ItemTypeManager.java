package ru.stonlex.bukkit.game.item.type.manager;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import lombok.NonNull;
import ru.stonlex.bukkit.game.item.GameItem;
import ru.stonlex.bukkit.game.item.type.ItemType;

import java.util.Collections;

public final class ItemTypeManager {

    @Getter
    private final TIntObjectMap<ItemType> itemTypeMap = new TIntObjectHashMap<>();

    /**
     * Зарегистрировать новый тип GameItem
     *
     * @param itemType - тип GameItem
     */
    public void registerItemType(@NonNull ItemType itemType, GameItem... gameItems) {
        Collections.addAll(itemType.getItemList(), gameItems);

        itemTypeMap.put(itemType.getTypeId(), itemType);
    }

}
