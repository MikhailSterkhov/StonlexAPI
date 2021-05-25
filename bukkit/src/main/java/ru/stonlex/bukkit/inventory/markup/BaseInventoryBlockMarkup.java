package ru.stonlex.bukkit.inventory.markup;

public class BaseInventoryBlockMarkup extends BaseInventoryStandardMarkup {

    public BaseInventoryBlockMarkup(int inventoryRows, int firstRowIndex, int tabSize) {
        super(inventoryRows);

        for (int rowIndex = firstRowIndex; rowIndex < inventoryRows; rowIndex++) {
            addHorizontalRow(rowIndex, tabSize);
        }
    }

    public BaseInventoryBlockMarkup(int inventoryRows, int firstRowIndex) {
        this(inventoryRows, firstRowIndex, 1);
    }

    public BaseInventoryBlockMarkup(int inventoryRows) {
        this(inventoryRows, 2, 1);
    }
}
