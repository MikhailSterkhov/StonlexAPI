package ru.stonlex.bukkit.menu;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.menu.button.applicable.ButtonApplicable;
import ru.stonlex.bukkit.menu.button.InventoryButton;
import ru.stonlex.bukkit.menu.info.InventoryInfo;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class StonlexMenu {

    @Getter
    private static final Map<String, StonlexMenu> inventoryMap = new HashMap<>();


    private Inventory bukkitInventory;

    private final InventoryInfo inventoryInfo;
    private final Map<Integer, InventoryButton> buttonMap;

    /**
     * Инициализация инвентаря
     *
     * @param inventoryTitle - Заголовок инвентаря
     * @param inventoryRows  - Количество строк в инвентаре
     */
    public StonlexMenu(String inventoryTitle, int inventoryRows) {
        this.buttonMap = new HashMap<>();
        this.inventoryInfo = new InventoryInfo(inventoryRows * 9, inventoryRows, inventoryTitle);

        this.bukkitInventory = Bukkit.createInventory(null, inventoryInfo.getInventorySize(), inventoryTitle);
    }

    /**
     * Передающийся метод.
     *
     * Вызывается перед открытием инвентаря
     */
    public void onOpen(Player player) { }

    /**
     * Передающийся метод.
     *
     * Вызывается после закрытия инвентаря
     */
    public void onClose(Player player) { }

    /**
     * Процесс генерации инвентаря, выставление предметов в сам инвентарь
     */
    public abstract void drawInventory(Player player);

    /**
     * Установка названия инвенетарю
     */
    protected void setTitle(String title) {
        this.bukkitInventory = Bukkit.createInventory(null, bukkitInventory.getSize(), title);
    }

    /**
     * Установка размера инвентаря (rows * 9)
     */
    protected void setSize(int size) {
        this.bukkitInventory = Bukkit.createInventory(null, size, bukkitInventory.getTitle());
    }

    /**
     * Установка количества строк в инвентаре
     */
    protected void setRows(int rows) {
        setSize(rows * 9);
    }

    /**
     * Установка предмета в инвентарь
     */
    public void setItem(int slot, InventoryButton button) {
        buttonMap.put(slot, button);
    }

    /**
     * Установка предмета в инвентарь
     */
    public void setItem(int slot, ItemStack itemStack, ButtonApplicable buttonApplicable) {
        InventoryButton button = new InventoryButton(itemStack, buttonApplicable);

        setItem(slot, button);
    }

    /**
     * Установка предмета в инвентарь
     */
    public void setItem(int slot, ItemStack itemStack) {
        InventoryButton button = new InventoryButton(itemStack, (player, event) -> {});

        setItem(slot, button);
    }

    /**
     * Очистка предметов в инвентаре
     */
    public void clearInventory() {
        buttonMap.clear();
        bukkitInventory.clear();
    }

    /**
     * Приватное открытие инвентаря игроку
     */
    public void openInventory(Player player) {
        inventoryMap.put(player.getName().toLowerCase(), this);

        drawInventory(player);
        setupItems();

        player.openInventory(bukkitInventory);
    }

    /**
     * Обновление инвентаря игроку
     */
    public void updateInventory(Player player) {
        clearInventory();
        drawInventory(player);

        setupItems();
    }

    /**
     * Установка предметов
     */
    private void setupItems() {
        for (Map.Entry<Integer, InventoryButton> buttonEntry : buttonMap.entrySet()) {
            getBukkitInventory().setItem(buttonEntry.getKey() - 1, buttonEntry.getValue().getItemStack());
        }
    }

}
