package ru.stonlex.bukkit.menu.button.applicable;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface ButtonApplicable {

    void execute(Player player, InventoryClickEvent inventoryClickEvent);
}
