package ru.stonlex.bukkit.game.item.impl;

import org.bukkit.Material;
import ru.stonlex.bukkit.game.item.GameItem;

public abstract class GamePerkItem extends GameItem {

    public GamePerkItem(int itemCost, Material baseMaterial, String itemName, String... itemDescription) {
        super(itemCost, baseMaterial, itemName, itemDescription);
    }

}
