package ru.stonlex.bukkit.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.stonlex.bukkit.StonlexBukkitApiPlugin;
import ru.stonlex.bukkit.event.EntityDamageByPlayerEvent;
import ru.stonlex.bukkit.event.PlayerDamageByEntityEvent;
import ru.stonlex.bukkit.event.PlayerDamageByPlayerEvent;
import ru.stonlex.bukkit.event.PlayerDamageEvent;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity target = event.getEntity();

        if (damager instanceof Player && target instanceof Player) {
            PlayerDamageByPlayerEvent playerDamageByPlayerEvent = new PlayerDamageByPlayerEvent((Player) damager, (Player) target, event.getCause(), event.getDamage());
            Bukkit.getPluginManager().callEvent(playerDamageByPlayerEvent);

            event.setCancelled(playerDamageByPlayerEvent.isCancelled());
        }

        else if (damager instanceof Player) {
            EntityDamageByPlayerEvent entityDamageByPlayerEvent = new EntityDamageByPlayerEvent((Player) damager, target, event.getCause(), event.getDamage());
            Bukkit.getPluginManager().callEvent(entityDamageByPlayerEvent);

            event.setCancelled(entityDamageByPlayerEvent.isCancelled());
        }

        else if (target instanceof Player) {
            PlayerDamageByEntityEvent playerDamageByEntityEvent = new PlayerDamageByEntityEvent(damager, (Player) target, event.getCause(), event.getDamage());
            Bukkit.getPluginManager().callEvent(playerDamageByEntityEvent);

            event.setCancelled(playerDamageByEntityEvent.isCancelled());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        EntityDamageEvent.DamageCause damageCause = event.getCause();

        if (entity instanceof Player) {
            PlayerDamageEvent playerDamageEvent = new PlayerDamageEvent(((Player) entity), damageCause, event.getDamage());
            Bukkit.getPluginManager().callEvent(playerDamageEvent);

            event.setCancelled(playerDamageEvent.isCancelled());
        }

    }

}
