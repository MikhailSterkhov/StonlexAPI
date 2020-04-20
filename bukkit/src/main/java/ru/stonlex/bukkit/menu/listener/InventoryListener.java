package ru.stonlex.bukkit.menu.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import ru.stonlex.bukkit.menu.StonlexMenu;
import ru.stonlex.bukkit.menu.button.InventoryButton;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        StonlexMenu inventory = StonlexMenu.getInventoryMap().get(player.getName().toLowerCase());

        int slot = event.getSlot();

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || inventory == null || !inventory.getButtonMap().containsKey(slot + 1)) {
            return;
        }

        event.setCancelled(true);

        InventoryButton button = inventory.getButtonMap().get(slot + 1);
        button.getButtonApplicable().execute(player, event);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        StonlexMenu inventory = StonlexMenu.getInventoryMap().get(player.getName().toLowerCase());

        if (inventory == null) {
            return;
        }

        StonlexMenu.getInventoryMap().remove(player.getName().toLowerCase());

        inventory.onClose(player);
    }

}
