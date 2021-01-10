package ru.stonlex.bukkit.inventory.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.inventory.BasePaginatedInventory;
import ru.stonlex.bukkit.inventory.addon.BasePaginatedInventorySorting;
import ru.stonlex.bukkit.inventory.button.BaseInventoryButton;
import ru.stonlex.global.utility.query.ResponseHandler;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SimplePaginatedInventorySorting implements BasePaginatedInventorySorting {

    @Getter
    private final BasePaginatedInventory inventory;

    @Getter
    private final List<BaseInventoryButton> sortedButtons = new LinkedList<>();


    private boolean reversed;


    @Override
    public BasePaginatedInventorySorting sortItem(ResponseHandler<Integer, ItemStack> responseHandler) {
        sortedButtons.clear();

        sortedButtons.addAll(inventory.getPageButtons().stream()
                .sorted(Comparator.comparing(inventoryButton -> responseHandler.handleResponse(inventoryButton.getItemStack())))
                .collect(Collectors.toList()));

        return this;
    }

    @Override
    public BasePaginatedInventorySorting reversed() {
        this.reversed = !reversed;

        return this;
    }

    @Override
    public void rebuildInventory() {
        inventory.getPageButtons().clear();

        if (reversed) {
            for (int i = sortedButtons.size() ; i > 0; i--) {
                BaseInventoryButton inventoryButton = sortedButtons.get(i - 1);

                if (inventoryButton == null) {
                    continue;
                }

                inventory.getPageButtons().add(inventoryButton);
            }

            return;
        }

        sortedButtons.forEach(inventoryButton -> inventory.getPageButtons().add(inventoryButton));
    }

}
