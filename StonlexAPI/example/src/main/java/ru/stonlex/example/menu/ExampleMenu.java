package ru.stonlex.example.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.menu.StonlexMenu;
import ru.stonlex.bukkit.utility.ItemUtil;

public class ExampleMenu extends StonlexMenu {

    public ExampleMenu() {
        super("Example", 3);
    }

    @Override
    public void drawInventory(Player player) {
        setItem(5, ItemUtil.newBuilder(Material.STONE)
                .setName("§eБаклажан")
                .build(), player1 -> {

            player.sendMessage("§eКлик прошел, закрываю инвентарь");
            player.closeInventory();
        });

        setItem(6, ItemUtil.newBuilder(Material.CHEST)
                .setName("§aОбновить инвентарь").build(), this::updateInventory);
    }

}
