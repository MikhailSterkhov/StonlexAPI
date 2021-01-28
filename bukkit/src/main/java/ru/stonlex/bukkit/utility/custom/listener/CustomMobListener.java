package ru.stonlex.bukkit.utility.custom.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import ru.lattycraft.bukkit.util.PercentUtil;
import ru.stonlex.bukkit.utility.custom.CustomMob;

public class CustomMobListener implements Listener {

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {
        CustomMob customMob = CustomMob.REGISTERED_CUSTOM_ENTITY_LIST.stream()
                .filter(customEntity1 -> customEntity1.getEntityType().equals(event.getEntityType()))
                .findAny()
                .orElse(null);

        if (customMob == null || !event.getLocation().getWorld().getName().equalsIgnoreCase(customMob.getEntityWorldName())) {
            return;
        }

        if (PercentUtil.acceptRandomPercent(customMob.getSpawnChance())) {

            customMob.spawnEntity(event.getLocation());
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onEntityMove(EntityTargetEvent event) {
        int entityId = event.getEntity().getEntityId();

        CustomMob customMob = CustomMob.CUSTOM_ENTITY_MAP.get(entityId);
        if (customMob == null || !(event.getTarget() instanceof Player)) {
            return;
        }

        Player player = ((Player) event.getTarget());
        customMob.onTarget(player);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        int entityId = event.getDamager().getEntityId();

        CustomMob customMob = CustomMob.CUSTOM_ENTITY_MAP.get(entityId);
        if (customMob == null || !(event.getEntity() instanceof Player)) {
            return;
        }

        event.setDamage(customMob.getOriginalDamage());
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        int entityId = event.getEntity().getEntityId();

        CustomMob customMob = CustomMob.CUSTOM_ENTITY_MAP.get(entityId);
        if (customMob == null) {
            return;
        }

        customMob.onDeath(event.getEntity().getLocation());

        event.setDroppedExp(0);
        event.getDrops().clear();

        customMob.drop(event.getEntity().getLocation());
    }

}
