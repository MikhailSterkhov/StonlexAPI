package ru.stonlex.bukkit.inventory;

import gnu.trove.list.TIntList;

public interface BaseInventoryMarkup {

    TIntList getMarkupList();

    void addHorizontalRow(int rowIndex);
    void addHorizontalRow(int rowIndex, int tabSize);

    void removeHorizontalRow(int rowIndex);
    void removeHorizontalRow(int rowIndex, int tabSize);

    void addVerticalRow(int rowIndex);
    void addVerticalRow(int rowIndex, int tabSize);

    void removeVerticalRow(int rowIndex);
    void removeVerticalRow(int rowIndex, int tabSize);

    void addInventorySlot(int inventorySlot);
    void removeInventorySlot(int inventorySlot);

    boolean hasInventorySlot(int inventorySlot);

}
