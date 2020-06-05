package ru.stonlex.bukkit.depend.messaging.inventory;

import org.bukkit.entity.Player;
import ru.stonlex.bukkit.inventory.impl.StonlexInventory;
import ru.stonlex.bukkit.depend.messaging.inventory.applicable.MenuApplicable;

public class CustomMenu extends StonlexInventory {

    private final MenuApplicable menuApplicable;


    public CustomMenu(String inventoryTitle, int inventoryRows, MenuApplicable menuApplicable) {
        super(inventoryTitle, inventoryRows);

        this.menuApplicable = menuApplicable;
    }

    @Override
    public void drawInventory(Player player) {
        menuApplicable.apply(player, this);
    }

    @Override
    public void onOpen(Player player) { }

    @Override
    public void onClose(Player player) { }
}
