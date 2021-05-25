package ru.stonlex.example.menu;

import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.inventory.handler.impl.BaseInventoryDisplayableHandler;
import ru.stonlex.bukkit.inventory.impl.BaseSimpleInventory;
import ru.stonlex.bukkit.utility.ItemUtil;

public class ExampleSimpleMenu extends BaseSimpleInventory {

    public ExampleSimpleMenu() {
        super(5, "Example");

        addHandler(BaseInventoryDisplayableHandler.class, new BaseInventoryDisplayableHandler() {

            @Override
            public void onOpen(@NonNull Player player) {
                player.sendMessage("§aТы открыл Example-инвентарь");
            }

            @Override
            public void onClose(@NonNull Player player) {
                player.sendMessage("§cТы закрыл Example-инвентарь");
            }
        });
    }

    @Override
    public void drawInventory(Player player) {
        addItem(5, ItemUtil.newBuilder(Material.STONE)
                .setName("§eБаклажан")
                .build(), (player1, event) -> {

            player.sendMessage("§eКлик прошел, закрываю инвентарь");
            player.closeInventory();
        });

        addItem(6, ItemUtil.newBuilder(Material.CHEST)
                .setName("§aОбновить инвентарь").build(), (player1, event) -> updateInventory(player));
    }

}
