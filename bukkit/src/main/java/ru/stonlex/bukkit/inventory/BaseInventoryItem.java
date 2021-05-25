package ru.stonlex.bukkit.inventory;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public interface BaseInventoryItem {

    int getSlot();
    void setSlot(int itemSlot);

    ItemStack getItemStack();


    void onDraw(@NonNull BaseInventory baseInventory);
}
