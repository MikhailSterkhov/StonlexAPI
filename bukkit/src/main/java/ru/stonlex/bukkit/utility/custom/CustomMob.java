package ru.stonlex.bukkit.utility.custom;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.stonlex.global.utility.PercentUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public abstract class CustomMob {

    protected final int spawnChance;

    protected final EntityType entityType;
    protected final String entityWorldName;

    protected LivingEntity bukkitEntity;
    protected final Map<ItemStack, Integer> itemDropMap = new HashMap<>();

    @Setter
    protected int originalDamage;


// ================================================== // Передающиеся методы // ================================================ //

    public abstract void onEntityCreate();

// ============================================== // Переопределяющиеся методы // ============================================== //

    // public void onMove(@NonNull Location fromLocation,
    //                    @NonNull Location toLocation) { }

    public void onTarget(@NonNull Player player) { }
    public void onDropItem(@NonNull Location location, @NonNull ItemStack itemStack) { }

    public void onDeath(@NonNull Location location) { }

// ============================================================================================================================= //

    public static final List<CustomMob> REGISTERED_CUSTOM_ENTITY_LIST = new ArrayList<>();
    public static final TIntObjectMap<CustomMob> CUSTOM_ENTITY_MAP = new TIntObjectHashMap<>();


    public final void addEquipment(@NonNull EquipmentSlot equipmentSlot, @NonNull ItemStack itemStack,
                                   float itemDropChance) {

        EntityEquipment entityEquipment = bukkitEntity.getEquipment();

        switch (equipmentSlot) {
            case HEAD:
                entityEquipment.setHelmet(itemStack);
                entityEquipment.setHelmetDropChance(itemDropChance);

                break;
            case CHEST:
                entityEquipment.setChestplate(itemStack);
                entityEquipment.setChestplateDropChance(itemDropChance);

                break;
            case LEGS:
                entityEquipment.setLeggings(itemStack);
                entityEquipment.setLeggingsDropChance(itemDropChance);

                break;
            case FEET:
                entityEquipment.setBoots(itemStack);
                entityEquipment.setBootsDropChance(itemDropChance);

                break;
            case HAND:
                entityEquipment.setItemInMainHand(itemStack);
                entityEquipment.setItemInMainHandDropChance(itemDropChance);

                break;
            case OFF_HAND:
                entityEquipment.setItemInOffHand(itemStack);
                entityEquipment.setItemInOffHandDropChance(itemDropChance);

                break;
        }
    }

    public final void addEquipment(@NonNull EquipmentSlot equipmentSlot, @NonNull ItemStack itemStack) {
        addEquipment(equipmentSlot, itemStack, 5);
    }

    public final void addItemDrop(@NonNull ItemStack itemStack, int dropPercent) {
        itemDropMap.put(itemStack, dropPercent);
    }

    public final void drop(@NonNull Location location) {
        itemDropMap.forEach((itemStack, percent) -> {

            if (!PercentUtil.acceptRandomPercent(percent)) {
                return;
            }

            location.getWorld().dropItemNaturally(location, itemStack);
            onDropItem(location, itemStack);
        });
    }

    public final void addEffect(@NonNull PotionEffectType potionEffectType,
                          int duration, int amplifier) {

        PotionEffect potionEffect = new PotionEffect(potionEffectType, duration, amplifier);
        bukkitEntity.addPotionEffect(potionEffect);
    }

    public final void addCustomName(@NonNull String customName) {
        bukkitEntity.setCustomName(customName);
    }

    public final void addEffect(@NonNull PotionEffectType potionEffectType, int amplifier) {
        addEffect(potionEffectType, Integer.MAX_VALUE, amplifier);
    }

    public void setMaxHealth(int maxHealth) {
        bukkitEntity.setMaxHealth(maxHealth);
        bukkitEntity.setHealth(maxHealth);
    }

    public final World getWorld() {
        return Bukkit.getWorld(entityWorldName);
    }

    public void teleport(@NonNull Location location) {
        bukkitEntity.teleport(location);
    }

    public void teleport(@NonNull LivingEntity livingEntity) {
        bukkitEntity.teleport(livingEntity);
    }


    public final void register() {
        REGISTERED_CUSTOM_ENTITY_LIST.add(this);
    }

    public final void spawnEntity(@NonNull Location location) {
        if (entityWorldName == null || getWorld() == null) {
            return;
        }

        this.bukkitEntity = (LivingEntity) getWorld().spawnEntity(location, entityType);
        onEntityCreate();

        CUSTOM_ENTITY_MAP.put(bukkitEntity.getEntityId(), this);
    }


}
