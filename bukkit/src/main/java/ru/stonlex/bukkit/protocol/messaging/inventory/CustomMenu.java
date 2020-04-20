package ru.stonlex.bukkit.protocol.messaging.inventory;

import org.bukkit.entity.Player;
import ru.stonlex.bukkit.menu.StonlexMenu;
import ru.stonlex.bukkit.protocol.messaging.inventory.applicable.MenuApplicable;

public class CustomMenu extends StonlexMenu {

    private final MenuApplicable menuApplicable;


    public CustomMenu(String inventoryTitle, int inventoryRows, MenuApplicable menuApplicable) {
        super(inventoryTitle, inventoryRows);

        this.menuApplicable = menuApplicable;
    }

    @Override
    public void drawInventory(Player player) {
        menuApplicable.apply(player, this);
    }

}
