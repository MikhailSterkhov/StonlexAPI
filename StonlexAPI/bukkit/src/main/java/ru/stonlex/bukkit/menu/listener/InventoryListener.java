package ru.stonlex.bukkit.menu.listener;

import lombok.RequiredArgsConstructor;
import ru.stonlex.bukkit.BukkitAPI;
import me.moonways.bukkit.menu.MoonInventory;
import ru.stonlex.bukkit.menu.button.InventoryButton;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

@RequiredArgsConstructor
public class InventoryListener implements Listener {

    private final BukkitAPI bukkitAPI;


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        MoonInventory inventory = MoonInventory.getInventories().get(player.getName().toLowerCase());

        int slot = e.getSlot();

        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || inventory == null || !inventory.getButtons().containsKey(slot + 1)) {
            return;
        }

        e.setCancelled(true);

        InventoryButton button = inventory.getButtons().get(slot + 1);

        button.getCommand().onClick(player);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        MoonInventory inventory = MoonInventory.getInventories().get(player.getName().toLowerCase());

        if (inventory == null) {
            return;
        }

        MoonInventory.getInventories().remove(player.getName().toLowerCase());

        inventory.onClose(player);
    }

}
