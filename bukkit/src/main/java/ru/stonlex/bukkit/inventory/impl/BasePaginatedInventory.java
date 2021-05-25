package ru.stonlex.bukkit.inventory.impl;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.StonlexBukkitApi;
import ru.stonlex.bukkit.inventory.BaseInventory;
import ru.stonlex.bukkit.inventory.BaseInventoryItem;
import ru.stonlex.bukkit.inventory.BaseInventoryMarkup;
import ru.stonlex.bukkit.inventory.handler.impl.BaseInventoryClickHandler;
import ru.stonlex.bukkit.inventory.handler.impl.BaseInventoryDisplayableHandler;
import ru.stonlex.bukkit.inventory.handler.impl.BaseInventoryUpdateHandler;
import ru.stonlex.bukkit.inventory.item.BaseInventoryClickItem;
import ru.stonlex.bukkit.inventory.item.BaseInventorySelectItem;
import ru.stonlex.bukkit.inventory.item.BaseInventoryStackItem;
import ru.stonlex.bukkit.inventory.update.BaseInventoryUpdateTask;
import ru.stonlex.bukkit.utility.ItemUtil;

import java.util.LinkedList;
import java.util.List;

@Getter
public abstract class BasePaginatedInventory implements BaseInventory {

    protected final int inventoryRows;
    protected final String inventoryTitle;

    protected int currentPage;

    protected BaseInventoryInfo inventoryInfo;
    protected Inventory inventory;

    protected BaseInventoryMarkup inventoryMarkup;
    protected final List<BaseInventoryItem> pageButtonList = new LinkedList<>();

    protected final BaseInventorySettings inventorySettings = new BaseInventorySettings();


    public BasePaginatedInventory(int inventoryRows, @NonNull String inventoryTitle) {
        this.inventoryRows = inventoryRows;
        this.inventoryTitle = inventoryTitle;

        this.inventoryInfo = new BaseInventoryInfo(this, inventoryTitle, inventoryRows * 9, inventoryRows);

        createInventory();
    }

    private void createInventory() {
        this.inventory = Bukkit.createInventory(null, inventoryInfo.getInventorySize(), inventoryTitle + " | " + (currentPage + 1));
    }


    protected void backwardPage(@NonNull Player player) {
        if (currentPage - 1 < 0) {
            throw new RuntimeException(String.format("Page cannot be < 0 (%s - 1 < 0)", currentPage));
        }

        this.currentPage--;

        createInventory();
        openInventory(player);
    }

    protected void forwardPage(@NonNull Player player, int allPagesCount) {
        if (currentPage >= allPagesCount) {
            throw new RuntimeException(String.format("Page cannot be >= max pages count (%s >= %s)", currentPage, allPagesCount));
        }

        this.currentPage++;

        createInventory();
        openInventory(player);
    }

    protected void drawPage(@NonNull Player player) {
        pageButtonList.clear();

        if (inventoryMarkup != null) {
            inventoryMarkup.getMarkupList().clear();
        }

        clearInventory();
        drawInventory(player);

        int pagesCount = pageButtonList.size() / inventoryMarkup.getMarkupList().size();

        if (!(currentPage >= pagesCount)) {
            addItem(inventoryInfo.getInventorySize() - 3, ItemUtil.newBuilder(Material.SKULL_ITEM)
                            .setTextureValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDRiZThhZWVjMTE4NDk2OTdhZGM2ZmQxZjE4OWIxNjY0MmRmZjE5ZjI5NTVjMDVkZWFiYTY4YzlkZmYxYmUifX19")

                            .setName("§eСледующая страница")
                            .addLore("§7Нажмите, чтобы открыть следующую страницу!")

                            .build(),

                    (baseInventory, event) -> forwardPage(player, pagesCount));
        }

        if (!(currentPage - 1 < 0)) {
            addItem(inventoryInfo.getInventorySize() - 5, ItemUtil.newBuilder(Material.SKULL_ITEM)
                            .setTextureValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzYyNTkwMmIzODllZDZjMTQ3NTc0ZTQyMmRhOGY4ZjM2MWM4ZWI1N2U3NjMxNjc2YTcyNzc3ZTdiMWQifX19")

                            .setName("§eПредыдущая страница")
                            .addLore("§7Нажмите, чтобы открыть предыдущую страницу!")

                            .build(),

                    (baseInventory, event) -> backwardPage(player));
        }

        for (int i = 0; i < inventoryMarkup.getMarkupList().size(); i++) {
            int itemIndex = currentPage * inventoryMarkup.getMarkupList().size() + i;

            if (pageButtonList.size() > itemIndex) {
                int buttonSlot = inventoryMarkup.getMarkupList().get(i);

                BaseInventoryItem baseInventoryItem = pageButtonList.get(itemIndex);
                baseInventoryItem.setSlot(buttonSlot - 1);

                addItem(baseInventoryItem);
            }
        }
    }

    @Override
    public void openInventory(@NonNull Player player) {
        closeInventory(player);
        drawPage(player);

        for (BaseInventoryItem baseInventoryItem : inventoryInfo.getInventoryItemMap().valueCollection()) {
            inventory.setItem(baseInventoryItem.getSlot(), baseInventoryItem.getItemStack());
        }

        StonlexBukkitApi.INVENTORY_MANAGER
                .createInventory(player, this);

        player.openInventory(inventory);
    }

    @Override
    public void openInventory(@NonNull Player player, @NonNull BaseInventoryDisplayableHandler inventoryDisplayableHandler) {
        addHandler(BaseInventoryDisplayableHandler.class, inventoryDisplayableHandler);

        openInventory(player);
    }

    @Override
    public void clearInventory() {
        inventoryInfo.getInventoryItemMap().clear();
        inventory.clear();
    }

    @Override
    public void updateInventory(@NonNull Player player) {
        drawPage(player);

        for (BaseInventoryItem baseInventoryItem : inventoryInfo.getInventoryItemMap().valueCollection()) {
            inventory.setItem(baseInventoryItem.getSlot(), baseInventoryItem.getItemStack());
        }
    }

    @Override
    public void updateInventory(@NonNull Player player, @NonNull BaseInventoryUpdateHandler inventoryUpdateHandler) {
        updateInventory(player);

        inventoryUpdateHandler.onUpdate(this, player);
    }

    @Override
    public void enableAutoUpdate(@NonNull Player player, BaseInventoryUpdateHandler inventoryUpdateHandler, long secondDelay) {
        StonlexBukkitApi.INVENTORY_MANAGER.addInventoryUpdateTask(this, new BaseInventoryUpdateTask(this, secondDelay, () -> {

            if (inventoryUpdateHandler != null)
                updateInventory(player, inventoryUpdateHandler);
            else
                updateInventory(player);
        }));
    }

    @Override
    public void closeInventory(@NonNull Player player) {
        if (player.getOpenInventory() == null) {
            return;
        }

        player.closeInventory();

        StonlexBukkitApi.INVENTORY_MANAGER.removeInventory(player, this);
        StonlexBukkitApi.INVENTORY_MANAGER.removeInventoryUpdateTask(this);
    }

    @Override
    public void addItem(@NonNull BaseInventoryItem baseInventoryItem) {
        baseInventoryItem.onDraw(this);
        inventoryInfo.addItem(baseInventoryItem.getSlot() - 1, baseInventoryItem);
    }

    @Override
    public void setItemMarkup(@NonNull BaseInventoryMarkup baseInventoryMarkup) {
        this.inventoryMarkup = baseInventoryMarkup;
    }


    public void addItem(int itemSlot, @NonNull ItemStack itemStack) {
        addItem(new BaseInventoryStackItem(itemSlot - 1, itemStack));
    }

    public void addItem(int itemSlot, @NonNull ItemStack itemStack, @NonNull BaseInventoryClickHandler inventoryClickHandler) {
        addItem(new BaseInventoryClickItem(itemSlot - 1, itemStack, inventoryClickHandler));
    }

    public void addItemSelect(int itemSlot, @NonNull ItemStack itemStack, @NonNull BaseInventoryClickHandler inventoryClickHandler, boolean isEnchanting) {
        addItem(new BaseInventorySelectItem(itemSlot - 1, itemStack, inventoryClickHandler, isEnchanting, false));
    }

    public void addItemSelect(int itemSlot, @NonNull ItemStack itemStack, @NonNull BaseInventoryClickHandler inventoryClickHandler) {
        addItemSelect(itemSlot, itemStack, inventoryClickHandler, true);
    }


    public void addItemToMarkup(@NonNull BaseInventoryItem baseInventoryItem) {
        pageButtonList.add(baseInventoryItem);
    }

    public void addItemToMarkup(@NonNull ItemStack itemStack) {
        addItemToMarkup(new BaseInventoryStackItem(0, itemStack));
    }

    public void addItemToMarkup(@NonNull ItemStack itemStack, @NonNull BaseInventoryClickHandler inventoryClickHandler) {
        addItemToMarkup(new BaseInventoryClickItem(0, itemStack, inventoryClickHandler));
    }

    public void addItemSelectToMarkup(@NonNull ItemStack itemStack, @NonNull BaseInventoryClickHandler inventoryClickHandler, boolean isEnchanting) {
        addItemToMarkup(new BaseInventorySelectItem(0, itemStack, inventoryClickHandler, isEnchanting, false));
    }

    public void addItemSelectToMarkup(@NonNull ItemStack itemStack, @NonNull BaseInventoryClickHandler inventoryClickHandler) {
        addItemSelectToMarkup(itemStack, inventoryClickHandler, true);
    }


    @Override
    public abstract void drawInventory(@NonNull Player player);

}
