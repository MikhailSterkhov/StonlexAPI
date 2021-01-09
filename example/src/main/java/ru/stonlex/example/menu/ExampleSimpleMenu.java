package ru.stonlex.example.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.inventory.impl.BaseSimpleInventory;
import ru.stonlex.bukkit.utility.ItemUtil;

public class ExampleSimpleMenu extends BaseSimpleInventory {

    public ExampleSimpleMenu() {
        super("Example", 3);
    }

    @Override
    public void drawInventory(Player player) {
        setClickItem(5, ItemUtil.newBuilder(Material.STONE)
                .setName("§eБаклажан")
                .build(), (player1, event) -> {

            player.sendMessage("§eКлик прошел, закрываю инвентарь");
            player.closeInventory();
        });

        setClickItem(6, ItemUtil.newBuilder(Material.CHEST)
                .setName("§aОбновить инвентарь").build(), (player1, event) -> updateInventory(player));
    }


    @Override //передающийся метод
    public void onOpen(Player player) {
        player.sendMessage("§aТы открыл Example-инвентарь");
    }

    @Override //передающийся метод
    public void onClose(Player player) {
        player.sendMessage("§cТы закрыл Example-инвентарь");
    }

}
