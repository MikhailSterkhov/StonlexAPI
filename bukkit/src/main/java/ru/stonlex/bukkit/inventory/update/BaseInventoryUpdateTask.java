package ru.stonlex.bukkit.inventory.update;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.stonlex.bukkit.inventory.BaseInventory;

@RequiredArgsConstructor
@Getter
public class BaseInventoryUpdateTask {

    private final BaseInventory baseInventory;

    private final long updateTaskDelay;
    private final Runnable inventoryUpdateTask;
}
