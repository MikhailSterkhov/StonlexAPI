package ru.stonlex.bukkit.utility.custom;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.utility.ItemUtil;
import ru.stonlex.bukkit.utility.MetadataUtil;
import ru.stonlex.global.utility.PercentUtil;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public abstract class CustomBlock {

    protected final String displayName;

    protected final Material material;

    protected final int chancePercent;
    protected final int durability;

    protected final Map<ItemStack, Integer> itemDropCollection = new HashMap<>();


    public static CustomBlock toCustomBlock(@NonNull Block bukkitBlock) {
        if (!MetadataUtil.hasMetadata(bukkitBlock, "custom")) {
            return null;
        }

        return MetadataUtil.getMetadata(bukkitBlock, "custom", CustomBlock.class);
    }


// ================================================== // Передающиеся методы // ================================================ //

    public abstract void onBlockCreate();

// ============================================== // Переопределяющиеся методы // ============================================== //

    public void onPlayerPlace(@NonNull BlockPlaceEvent blockPlaceEvent) { }
    public void onPlayerBreak(@NonNull BlockBreakEvent blockBreakEvent) { }

    public void onCustomPlace(@NonNull Location location) { }
    public void onCustomBreak(@NonNull Location location) { }

    public void onDropItem(@NonNull Location location, @NonNull ItemStack itemStack) { }

// ============================================================================================================================= //

    public ItemStack toItemStack() {
        return ItemUtil.newBuilder(material).setDurability(durability).setName(displayName)
                .build();
    }

    public void addItemDrop(@NonNull ItemStack itemStack, int dropPercent) {
        if (dropPercent > 100) {
            return;
        }

        itemDropCollection.put(itemStack, dropPercent);
    }

    public void placeBlock(@NonNull Location location) {
        onBlockCreate();

        location.getBlock().setTypeIdAndData(material.getId(), (byte) durability, true);
        MetadataUtil.setMetadata(location.getBlock(), "custom", this);

        onCustomPlace(location);
    }

    public void breakBlock(@NonNull Location location) {
        drop(location);

        location.getBlock().setType(Material.AIR);
        location.getWorld().playSound(location, Sound.BLOCK_STONE_BREAK, 1, 1);

        onCustomBreak(location);
    }

    public final void drop(@NonNull Location location) {
        itemDropCollection.forEach((itemStack, percent) -> {

            if (!PercentUtil.acceptRandomPercent(percent)) {
                return;
            }

            location.getWorld().dropItemNaturally(location, itemStack);
            onDropItem(location, itemStack);
        });
    }

}
