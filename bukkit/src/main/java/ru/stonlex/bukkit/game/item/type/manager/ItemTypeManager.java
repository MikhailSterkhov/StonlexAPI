package ru.stonlex.bukkit.game.item.type.manager;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Material;
import ru.stonlex.bukkit.game.item.GameItem;
import ru.stonlex.bukkit.game.item.type.ItemType;

import java.util.Collections;

public final class ItemTypeManager {

    @Getter
    private final TIntObjectMap<ItemType> itemTypeMap = new TIntObjectHashMap<>();

    public static final ItemType PERK_ITEM_TYPE = new ItemType(0, Material.BLAZE_POWDER, "Перки", new String[]{
                    "Перки дают возможность получить",
                    "Новые умения и возможности"
    });


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
