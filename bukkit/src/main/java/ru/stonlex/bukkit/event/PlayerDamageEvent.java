package ru.stonlex.bukkit.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

@AllArgsConstructor
@Getter
public class PlayerDamageEvent extends BaseCustomEvent {

    private final Player player;
    private final EntityDamageEvent.DamageCause damageCause;

    @Setter
    private double damage;

}
