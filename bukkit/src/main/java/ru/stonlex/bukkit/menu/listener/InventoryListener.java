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
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        StonlexMenu inventory = StonlexMenu.getInventoryMap().get(player.getName().toLowerCase());

        int slot = e.getSlot();

        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || inventory == null || !inventory.getButtonMap().containsKey(slot + 1)) {
            return;
        }

        e.setCancelled(true);

        InventoryButton button = inventory.getButtonMap().get(slot + 1);

        button.getCommand().onClick(player);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        StonlexMenu inventory = StonlexMenu.getInventoryMap().get(player.getName().toLowerCase());

        if (inventory == null) {
            return;
        }

        StonlexMenu.getInventoryMap().remove(player.getName().toLowerCase());

        inventory.onClose(player);
    }

}
