package ru.stonlex.bukkit.inventory.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.inventory.IPaginatedBukkitInventory;
import ru.stonlex.bukkit.inventory.addon.IPaginatedBukkitInventorySortable;
import ru.stonlex.bukkit.inventory.button.IBukkitInventoryButton;
import ru.stonlex.global.ResponseHandler;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StonlexInventorySortable implements IPaginatedBukkitInventorySortable {

    @Getter
    private final IPaginatedBukkitInventory inventory;

    @Getter
    private final List<IBukkitInventoryButton> sortedButtons = new LinkedList<>();


    private boolean reversed;


    @Override
    public IPaginatedBukkitInventorySortable sortItem(ResponseHandler<Integer, ItemStack> responseHandler) {
        sortedButtons.clear();

        sortedButtons.addAll(inventory.getPageButtons().stream()
                .sorted(Comparator.comparing(inventoryButton -> responseHandler.handleResponse(inventoryButton.getItemStack())))
                .collect(Collectors.toList()));

        return this;
    }

    @Override
    public IPaginatedBukkitInventorySortable reversed() {
        this.reversed = !reversed;

        return this;
    }

    @Override
    public void rebuildInventory() {
        inventory.getPageButtons().clear();

        if (reversed) {
            for (int i = sortedButtons.size() ; i > 0; i--) {
                IBukkitInventoryButton inventoryButton = sortedButtons.get(i - 1);

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
