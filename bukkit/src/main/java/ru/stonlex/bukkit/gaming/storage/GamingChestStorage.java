package ru.stonlex.bukkit.gaming.storage;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import ru.stonlex.bukkit.StonlexBukkitApiPlugin;
import ru.stonlex.global.utility.NumberUtil;

import java.util.Collection;
import java.util.function.Supplier;

@RequiredArgsConstructor
public abstract class GamingChestStorage {

    @Getter
    protected final int storageLevel;

    @Getter
    protected boolean autoRefill;
    protected BukkitRunnable autoRefillRunnable;


    protected Inventory chestInventory;
    protected Location currentLocation;


    public abstract void onPlace(@NonNull Location location);
    public abstract void onBreak();

    public void onRefillAdd() { }
    public void onRefillRemove() { }


    public void place(@NonNull Location location) {
        (currentLocation = location).getBlock().setType(Material.CHEST);

        Chest chest = ((Chest) currentLocation.getBlock().getState());
        this.chestInventory = chest.getBlockInventory();

        onPlace(location);
    }

    public void refill(@NonNull Collection<ItemStack> itemCollection) {
        chestInventory.clear();

        for (ItemStack itemStack : itemCollection) {

            int inventorySlot = NumberUtil.randomInt(0, 36);
            chestInventory.setItem(inventorySlot, itemStack);
        }
    }

    public void addAutoRefill(int delayTick, @NonNull Collection<ItemStack> itemCollection, Supplier<Boolean> updateAccessSupplier) {
        if (autoRefillRunnable != null) {
            return;
        }

        this.autoRefill = true;
        this.autoRefillRunnable = new BukkitRunnable() {
            protected int counter = delayTick;

            @Override
            public void run() {
                if (!autoRefill) {
                    cancel();

                    return;
                }

                counter--;

                if (counter <= 0) {
                    refill(itemCollection);

                    counter = delayTick;
                    return;
                }

                if (updateAccessSupplier != null && updateAccessSupplier.get()) {
                    removeAutoRefill();
                }
            }
        };

        this.autoRefillRunnable
                .runTaskTimer(StonlexBukkitApiPlugin.getInstance(), 0, 20);

        onRefillAdd();
    }

    public void removeAutoRefill() {
        if (!isAutoRefill()) {
            return;
        }

        this.autoRefill = false;

        this.autoRefillRunnable.cancel();
        this.autoRefillRunnable = null;

        onRefillRemove();
    }

    public void remove() {
        removeAutoRefill();

        currentLocation.getBlock().setType(Material.AIR);
        onBreak();
    }

}
