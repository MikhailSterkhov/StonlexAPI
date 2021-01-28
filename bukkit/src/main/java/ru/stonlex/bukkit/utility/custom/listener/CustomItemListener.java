package ru.stonlex.bukkit.utility.custom.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import ru.lattycraft.bukkit.util.cooldown.PlayerCooldownUtil;
import ru.stonlex.bukkit.utility.custom.CustomItem;

public class CustomItemListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        ItemStack itemStack = player.getItemInHand();

        if (itemStack != null) {
            CustomItem customItem = CustomItem.CUSTOM_ITEM_MAP.get(itemStack);

            if (customItem == null) {
                return;
            }

            event.setCancelled(true);

            if (PlayerCooldownUtil.hasCooldown("custom_click", player)) {
                return;
            }

            customItem.onInteract(player, action, event.hasBlock() ? event.getClickedBlock().getLocation() : null);
            PlayerCooldownUtil.putCooldown("custom_click", player, 100);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        ItemStack itemStack = event.getItem().getItemStack();

        if (itemStack != null) {
            CustomItem customItem = CustomItem.CUSTOM_ITEM_MAP.get(itemStack);

            if (customItem == null) {
                return;
            }

            customItem.onPickup(event);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        ItemStack itemStack = event.getItemDrop().getItemStack();

        if (itemStack != null) {
            CustomItem customItem = CustomItem.CUSTOM_ITEM_MAP.get(itemStack);

            if (customItem == null) {
                return;
            }

            customItem.onDrop(event);
        }
    }

}
