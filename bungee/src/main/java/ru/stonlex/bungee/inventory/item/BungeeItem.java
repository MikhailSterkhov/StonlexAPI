package ru.stonlex.bungee.inventory.item;

import lombok.Getter;
import lombok.Setter;
import ru.stonlex.bungee.inventory.item.material.BungeeMaterial;

import java.util.List;

@Getter
public class BungeeItem {

    private final BungeeMaterial material;

    private final int durability;
    private final int amount;

    @Setter
    private String playerSkull;

    @Setter
    private String displayName;

    @Setter
    private List<String> displayLore;


    public BungeeItem(String playerSkull, int durability, int amount) {
        this(BungeeMaterial.SKULL_ITEM, durability, amount);

        this.playerSkull = playerSkull;
    }

    public BungeeItem(BungeeMaterial material, int durability, int amount) {
        this.material = material;
        this.durability = durability;
        this.amount = amount;
    }
}
