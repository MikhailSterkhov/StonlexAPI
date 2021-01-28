package ru.stonlex.example.custom;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import ru.stonlex.bukkit.utility.ItemUtil;
import ru.stonlex.bukkit.utility.custom.CustomRecipe;

public class ExampleCustomRecipe extends CustomRecipe {

    public ExampleCustomRecipe() {
        super(ItemUtil.newBuilder(Material.DIAMOND_PICKAXE)
                .addEnchantment(Enchantment.LUCK, 5)
                .build());
    }

    @Override
    public void onRecipeCreate() {
        setCraftSlot(1, Material.LEAVES);
        setCraftSlot(2, Material.LEAVES);
        setCraftSlot(3, Material.LEAVES);

        setCraftSlot(5, Material.STICK);
        setCraftSlot(8, Material.STICK);
    }

}
