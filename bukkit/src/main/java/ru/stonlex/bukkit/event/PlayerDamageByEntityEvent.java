package ru.stonlex.bukkit.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

@AllArgsConstructor
@Getter
public class PlayerDamageByEntityEvent extends BaseCustomEvent {

    private final Entity damager;
    private final Player target;

    private final EntityDamageEvent.DamageCause damageCause;

    @Setter
    private double damage;

}
