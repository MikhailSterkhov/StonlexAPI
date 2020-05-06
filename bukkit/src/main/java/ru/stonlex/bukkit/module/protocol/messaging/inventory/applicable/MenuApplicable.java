package ru.stonlex.bukkit.module.protocol.messaging.inventory.applicable;

import org.bukkit.entity.Player;
import ru.stonlex.bukkit.inventory.impl.StonlexInventory;

public interface MenuApplicable {

    void apply(Player player, StonlexInventory menu);
}
