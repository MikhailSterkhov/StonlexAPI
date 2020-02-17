package ru.stonlex.example.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.menu.PageStonlexMenu;
import ru.stonlex.bukkit.utility.ItemUtil;

public class ExamplePageMenu extends PageStonlexMenu {

    public ExamplePageMenu() {
        super("Example Page menu", 5);
    }

    @Override
    public void drawPagedInventory(Player player, int page) {
        setItem(5, ItemUtil.newBuilder(Material.SIGN)
                .setName("§aИнформация")
                .setLore("§7Страница: §e" + page).build());

        setPageSize(11, 12, 13, 14, 15, 16, 17,
                20, 21, 22, 23, 24, 25, 26,
                29, 30, 31, 32, 33, 34, 35);

        addItemToPage(new ItemStack(Material.STONE), Player::closeInventory);
        addItemToPage(new ItemStack(Material.DIAMOND), Player::closeInventory);
        addItemToPage(new ItemStack(Material.BANNER), Player::closeInventory);
        addItemToPage(new ItemStack(Material.BARRIER), Player::closeInventory);
        addItemToPage(new ItemStack(Material.CACTUS), Player::closeInventory);
    }
}
