package ru.stonlex.bukkit.inventory.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.inventory.IBukkitInventory;
import ru.stonlex.bukkit.inventory.button.IBukkitInventoryButton;
import ru.stonlex.bukkit.inventory.button.impl.ClickableStonlexInventoryButton;
import ru.stonlex.bukkit.inventory.button.impl.DraggableStonlexInventoryButton;

public class StonlexInventoryListener implements Listener {

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Player player = ((Player) event.getWhoClicked());
        IBukkitInventory bukkitInventory = BukkitAPI.getInstance().getInventoryManager().getOpenInventory(player);

        if (bukkitInventory == null) {
            return;
        }

        for (int rawSlot : event.getRawSlots()) {
            IBukkitInventoryButton inventoryButton = bukkitInventory.getButtons().get(rawSlot + 1);

            if (!(inventoryButton instanceof DraggableStonlexInventoryButton)) {
                return;
            }

            event.setCancelled(true);

            ((DraggableStonlexInventoryButton) inventoryButton).getButtonAction().buttonDrag(player, event);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = ((Player) event.getWhoClicked());
        IBukkitInventory bukkitInventory = BukkitAPI.getInstance().getInventoryManager().getOpenInventory(player);

        if (bukkitInventory == null) {
            return;
        }

        IBukkitInventoryButton inventoryButton = bukkitInventory.getButtons().get(event.getSlot() + 1);

        //вдруг это OriginalItem? лучше на всякий отменить клик уж)
        event.setCancelled(true);

        if (!(inventoryButton instanceof ClickableStonlexInventoryButton)) {
            return;
        }

        ((ClickableStonlexInventoryButton) inventoryButton).getButtonAction().buttonClick(player, event);
    }


    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        Player player = ((Player) event.getPlayer());
        IBukkitInventory bukkitInventory = BukkitAPI.getInstance().getInventoryManager().getOpenInventory(player);

        if (bukkitInventory == null) {
            return;
        }

        bukkitInventory.onOpen(player);
    }


    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = ((Player) event.getPlayer());
        IBukkitInventory bukkitInventory = BukkitAPI.getInstance().getInventoryManager().getOpenInventory(player);

        if (bukkitInventory == null) {
            return;
        }

        if (bukkitInventory.getInventoryUpdater() != null && bukkitInventory.getInventoryUpdater().isEnable()) {
            bukkitInventory.getInventoryUpdater().cancelUpdater();
        }

        BukkitAPI.getInstance().getInventoryManager().removeOpenInventoryToPlayer(player);

        bukkitInventory.onClose(player);
    }

}
