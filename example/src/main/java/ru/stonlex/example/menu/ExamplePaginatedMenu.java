package ru.stonlex.example.menu;

import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.inventory.handler.impl.BaseInventoryDisplayableHandler;
import ru.stonlex.bukkit.inventory.impl.BasePaginatedInventory;
import ru.stonlex.bukkit.inventory.markup.BaseInventoryBlockMarkup;
import ru.stonlex.bukkit.utility.ItemUtil;

public class ExamplePaginatedMenu extends BasePaginatedInventory {

    public ExamplePaginatedMenu() {
        super(5, "Example Page menu");

        addHandler(BaseInventoryDisplayableHandler.class, new BaseInventoryDisplayableHandler() {

            @Override
            public void onOpen(@NonNull Player player) {
                player.sendMessage("§aТы открыл страничный Example-инвентарь");
            }

            @Override
            public void onClose(@NonNull Player player) {
                player.sendMessage("§cТы закрыл страничный Example-инвентарь");
            }
        });
    }

    @Override
    public void drawInventory(Player player) {
        setItemMarkup(new BaseInventoryBlockMarkup(inventoryRows));

        addItem(5, ItemUtil.newBuilder(Material.SIGN)
                .setName("§aИнформация")
                .setLore("§7Страница: §e" + (currentPage + 1)).build());

        addItemToMarkup(new ItemStack(Material.STONE), (player1, event) -> player.closeInventory());
        addItemToMarkup(new ItemStack(Material.DIAMOND), (player1, event) -> player.closeInventory());
        addItemToMarkup(new ItemStack(Material.BANNER), (player1, event) -> player.closeInventory());
        addItemToMarkup(new ItemStack(Material.BARRIER), (player1, event) -> player.closeInventory());
        addItemToMarkup(new ItemStack(Material.CACTUS), (player1, event) -> player.closeInventory());
    }

}
