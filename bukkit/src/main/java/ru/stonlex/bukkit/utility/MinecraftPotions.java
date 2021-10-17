package ru.stonlex.bukkit.utility;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@UtilityClass
public class MinecraftPotions {

    public PotionEffect getInfinityPotion(@NonNull PotionEffectType potionEffectType) {
        return getPotion(potionEffectType, 9999 * 20);
    }

    public PotionEffect getInfinityPotion(@NonNull PotionEffectType potionEffectType, int amplifier) {
        return getPotion(potionEffectType, 9999 * 20, amplifier);
    }

    public PotionEffect getPotion(@NonNull PotionEffectType potionEffectType, int duration) {
        return getPotion(potionEffectType, duration, 1);
    }

    public PotionEffect getPotion(@NonNull PotionEffectType potionEffectType, int duration, int amplifier) {
        return new PotionEffect(potionEffectType, duration, amplifier);
    }

}
