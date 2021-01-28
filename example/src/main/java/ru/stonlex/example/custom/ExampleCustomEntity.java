package ru.stonlex.example.custom;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import ru.stonlex.bukkit.utility.custom.CustomMob;

public class ExampleCustomEntity extends CustomMob {

    public ExampleCustomEntity() {
        super(35, EntityType.ZOMBIE, "world");
    }

    @Override
    public void onEntityCreate() {
        setMaxHealth(50);
        setOriginalDamage(10);

        addCustomName("Zombie boss");

        addEffect(PotionEffectType.HEAL, 5);
        addEquipment(EquipmentSlot.CHEST, new ItemStack(Material.DIAMOND_CHESTPLATE));

        addItemDrop(new ItemStack(Material.DIAMOND, 2), 50);
    }

}
