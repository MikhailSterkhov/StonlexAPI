package ru.stonlex.bukkit.menu.info;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class InventoryInfo {

    private final int inventorySize;
    private final int inventoryRows;

    private final String inventoryTitle;
}
