package ru.stonlex.bukkit.utility.custom;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public abstract class CustomItem {

    @Getter
    private final ItemStack itemStack;


    public abstract void onInteract(@NonNull Player player, @NonNull Action mouseAction, Location location);

    public void onPickup(@NonNull PlayerPickupItemEvent event) { }
    public void onDrop(@NonNull PlayerDropItemEvent event) { }


    public static final Map<ItemStack, CustomItem> CUSTOM_ITEM_MAP = new HashMap<>();

    public void give(@NonNull Player player) {
        player.getInventory().addItem(itemStack);
    }

    public void register() {
        CUSTOM_ITEM_MAP.put(itemStack, this);
    }

}
