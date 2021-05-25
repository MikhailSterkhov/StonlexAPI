package ru.stonlex.bukkit.inventory.markup;

import gnu.trove.list.TIntList;
import gnu.trove.list.linked.TIntLinkedList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.stonlex.bukkit.inventory.BaseInventoryMarkup;
import ru.stonlex.global.utility.NumberUtil;

@RequiredArgsConstructor
public class BaseInventoryStandardMarkup implements BaseInventoryMarkup {

    private final int inventoryRows;

    @Getter
    private final TIntList markupList = new TIntLinkedList();


    @Override
    public void addHorizontalRow(int rowIndex) {
        addHorizontalRow(rowIndex, 0);
    }

    @Override
    public void addHorizontalRow(int rowIndex, int tabSize) {
        if (rowIndex <= 0) {
            throw new IllegalArgumentException("row index must be > 0");
        }

        int startSlotIndex = (rowIndex * 9) - 8;
        int endSlotIndex = startSlotIndex + 8;

        if (tabSize < 0) {
            throw new IllegalArgumentException("tab side must be > 0");
        }

        startSlotIndex += tabSize;
        endSlotIndex -= tabSize;

        for (int slotIndex : NumberUtil.toManyArray(startSlotIndex, endSlotIndex + 1)) {
            addInventorySlot(slotIndex);
        }
    }

    @Override
    public void removeHorizontalRow(int rowIndex) {
        removeHorizontalRow(rowIndex, 0);
    }

    @Override
    public void removeHorizontalRow(int rowIndex, int tabSize) {
        if (rowIndex <= 0) {
            throw new IllegalArgumentException("row index must be > 0");
        }

        int startSlotIndex = (rowIndex * 9) - 8;
        int endSlotIndex = startSlotIndex + 8;

        if (tabSize < 0) {
            throw new IllegalArgumentException("tab side must be > 0");
        }

        startSlotIndex += tabSize;
        endSlotIndex -= tabSize;

        for (int slotIndex : NumberUtil.toManyArray(startSlotIndex, endSlotIndex + 1)) {
            removeInventorySlot(slotIndex);
        }
    }

    @Override
    public void addVerticalRow(int rowIndex) {
        addVerticalRow(rowIndex, 0);
    }

    @Override
    public void addVerticalRow(int rowIndex, int tabSize) {
        if (rowIndex <= 0) {
            throw new IllegalArgumentException("row index must be > 0");
        }

        if (rowIndex >= 10) {
            throw new IllegalArgumentException("row index must be < 9");
        }

        if (tabSize < 0) {
            throw new IllegalArgumentException("tab side must be > 0");
        }

        if (tabSize > inventoryRows || tabSize > inventoryRows / 2) {
            throw new IllegalArgumentException("tab side must be < inventory rows (" + inventoryRows + ")");
        }

        for (int inventoryRow : NumberUtil.toManyArray(tabSize + 1, inventoryRows - tabSize)) {
            addInventorySlot((inventoryRow - 1) * 9 + rowIndex - 1);
        }
    }

    @Override
    public void removeVerticalRow(int rowIndex) {
        removeVerticalRow(rowIndex, 0);
    }

    @Override
    public void removeVerticalRow(int rowIndex, int tabSize) {
        if (rowIndex <= 0) {
            throw new IllegalArgumentException("row index must be > 0");
        }

        if (rowIndex >= 10) {
            throw new IllegalArgumentException("row index must be < 9");
        }

        if (tabSize < 0) {
            throw new IllegalArgumentException("tab side must be > 0");
        }

        if (tabSize > inventoryRows || tabSize > inventoryRows / 2) {
            throw new IllegalArgumentException("tab side must be < inventory rows (" + inventoryRows + ")");
        }

        for (int inventoryRow : NumberUtil.toManyArray(tabSize + 1, inventoryRows - tabSize)) {
            removeInventorySlot((inventoryRow - 1) * 9 + rowIndex - 1);
        }
    }

    @Override
    public void addInventorySlot(int inventorySlot) {
        if (hasInventorySlot(inventorySlot)) {
            return;
        }

        markupList.add(inventorySlot);
    }

    @Override
    public void removeInventorySlot(int inventorySlot) {
        markupList.remove(inventorySlot);
    }

    @Override
    public boolean hasInventorySlot(int inventorySlot) {
        return markupList.contains(inventorySlot);
    }
}
