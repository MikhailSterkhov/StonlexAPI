package ru.stonlex.example.custom;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.utility.custom.CustomBlock;

public class ExampleCustomBlock extends CustomBlock {

    public ExampleCustomBlock() {
        super("§e§lCustomBlock", Material.STAINED_GLASS, 100, 14);
    }

    @Override
    public void onBlockCreate() {
        addItemDrop(new ItemStack(Material.EGG), 100);
        addItemDrop(new ItemStack(Material.APPLE), 56);
    }

}
