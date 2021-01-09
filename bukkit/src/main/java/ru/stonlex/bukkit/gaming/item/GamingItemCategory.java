package ru.stonlex.bukkit.gaming.item;

import com.google.common.base.Joiner;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import ru.stonlex.bukkit.gaming.player.GamingPlayer;
import ru.stonlex.bukkit.utility.ItemUtil;
import ru.stonlex.global.utility.PercentUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

@RequiredArgsConstructor
public class GamingItemCategory {

    @Getter protected int id;

    @Getter protected @NonNull String categoryName;
    @Getter protected @NonNull MaterialData materialData;

    protected final TIntObjectMap<GamingItem> gamingItemMap = new TIntObjectHashMap<>();

    @Setter
    @Getter
    protected Collection<String> categoryDescription = new LinkedList<>();

/* ============================================== // DEFAULT CATEGORIES // ============================================== */

    public static final GamingItemCategory SQUARE_CATEGORY            = registerItemCategory(10, new GamingItemCategory("Клетки", new MaterialData(Material.STAINED_GLASS, (byte) 5)));
    public static final GamingItemCategory PERK_CATEGORY              = registerItemCategory(20, new GamingItemCategory("Перки", new MaterialData(Material.APPLE)));
    public static final GamingItemCategory KIT_CATEGORY               = registerItemCategory(30, new GamingItemCategory("Наборы", new MaterialData(Material.IRON_AXE)));
    public static final GamingItemCategory ARROW_EFFECT_CATEGORY      = registerItemCategory(40, new GamingItemCategory("Эффекты стрел", new MaterialData(Material.ARROW)));
    public static final GamingItemCategory SHOWBALL_EFFECT_CATEGORY   = registerItemCategory(50, new GamingItemCategory("Эффекты снежков", new MaterialData(Material.SNOW_BALL)));

/* ============================================== // DEFAULT CATEGORIES // ============================================== */


    public void addItemToCategory(@NonNull GamingItem gamingItem) {
        gamingItemMap.put((gamingItem.id = gamingItemMap.size()), gamingItem);
    }

    public GamingItem getGamingItem(int itemId) {
        return gamingItemMap.get(itemId);
    }

    public Collection<GamingItem> getLoadedItems() {
        return Collections.unmodifiableCollection(gamingItemMap.valueCollection());
    }

    public int getLoadedItemsCount() {
        return gamingItemMap.size();
    }

    public ItemStack toBukkitItem(@NonNull GamingPlayer gamingPlayer) {
        int boughtItemCount = gamingPlayer.getBoughtItems(this).size();
        GamingItem selectedItem = gamingPlayer.getItemSelection(this);

        return ItemUtil.newBuilder(materialData)
                .setName(ChatColor.YELLOW + categoryName)

                .addLore("")
                .addLoreArray(ChatColor.GRAY + Joiner.on("\n§7")
                        .join(categoryDescription.toArray(new String[0])))

                .addLore("")
                .addLore("§7Открыто: §e" + boughtItemCount + " из " + getLoadedItemsCount() +
                        " §8" + PercentUtil.getPercent(boughtItemCount, getLoadedItemsCount()))

                .addLore(selectedItem != null ? "§7Выбрано: §e" + selectedItem.getItemName() : "§cНичего не выбрано")

                .addLore("")
                .addLore("§e▸ Нажмите, чтобы открыть")

                .setGlowing(selectedItem != null)
                .build();
    }


// =================================================== // CATEGORY FACTORY // =================================================== //

    public static final TIntObjectMap<GamingItemCategory> CATEGORY_MAP = new TIntObjectHashMap<>();

    public static GamingItemCategory registerItemCategory(int categoryId, @NonNull GamingItemCategory gamingItemCategory) {
        return CATEGORY_MAP.put((gamingItemCategory.id = categoryId), gamingItemCategory);
    }

    public static GamingItemCategory of(int categoryId) {
        return CATEGORY_MAP.get(categoryId);
    }

// =================================================== // CATEGORY FACTORY // =================================================== //

}
