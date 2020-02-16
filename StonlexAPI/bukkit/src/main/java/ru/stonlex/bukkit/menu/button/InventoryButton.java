package ru.stonlex.bukkit.menu.button;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.global.Clickable;

public interface InventoryButton {

    Clickable<Player> getCommand();
    ItemStack getItem();
}
