package ru.stonlex.bukkit.protocol.messaging.inventory.applicable;

import org.bukkit.entity.Player;
import ru.stonlex.bukkit.menu.StonlexMenu;

public interface MenuApplicable {

    void apply(Player player, StonlexMenu menu);
}
