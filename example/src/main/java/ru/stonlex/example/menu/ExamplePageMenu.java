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

        addRowToPageSize(2, 1, true);
        addRowToPageSize(3, 0, false);
        addRowToPageSize(4, 1,  true);

        addItemToPage(new ItemStack(Material.STONE), Player::closeInventory);
        addItemToPage(new ItemStack(Material.DIAMOND), Player::closeInventory);
        addItemToPage(new ItemStack(Material.BANNER), Player::closeInventory);
        addItemToPage(new ItemStack(Material.BARRIER), Player::closeInventory);
        addItemToPage(new ItemStack(Material.CACTUS), Player::closeInventory);
    }

    @Override //передающийся метод
    public void onOpen(Player player) {
        player.sendMessage("§aТы открыл страничный Example-инвентарь");
    }

    @Override //передающийся метод
    public void onClose(Player player) {
        player.sendMessage("§cТы закрыл страничный Example-инвентарь");
    }

}
