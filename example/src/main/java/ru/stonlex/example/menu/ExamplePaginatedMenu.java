package ru.stonlex.example.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.inventory.impl.BasePaginatedInventory;
import ru.stonlex.bukkit.utility.ItemUtil;

public class ExamplePaginatedMenu extends BasePaginatedInventory {

    public ExamplePaginatedMenu() {
        super("Example Page menu", 5);
    }

    @Override
    public void drawInventory(Player player) {
        setOriginalItem(5, ItemUtil.newBuilder(Material.SIGN)
                .setName("§aИнформация")
                .setLore("§7Страница: §e" + (currentPage + 1)).build());

        addRowToMarkup(2, 1);
        addRowToMarkup(3, 0);
        addRowToMarkup(4, 1);

        addClickItemToMarkup(new ItemStack(Material.STONE), (player1, event) -> player.closeInventory());
        addClickItemToMarkup(new ItemStack(Material.DIAMOND), (player1, event) -> player.closeInventory());
        addClickItemToMarkup(new ItemStack(Material.BANNER), (player1, event) -> player.closeInventory());
        addClickItemToMarkup(new ItemStack(Material.BARRIER), (player1, event) -> player.closeInventory());
        addClickItemToMarkup(new ItemStack(Material.CACTUS), (player1, event) -> player.closeInventory());
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
