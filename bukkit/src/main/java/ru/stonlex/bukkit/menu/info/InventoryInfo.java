package ru.stonlex.bukkit.menu.info;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class InventoryInfo {

    private final String title;
    private final int size;
    private final int rows;
}
