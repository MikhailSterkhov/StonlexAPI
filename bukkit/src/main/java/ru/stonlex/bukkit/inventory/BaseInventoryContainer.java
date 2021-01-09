package ru.stonlex.bukkit.inventory;

import gnu.trove.map.TIntObjectMap;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.inventory.button.BaseInventoryButton;
import ru.stonlex.bukkit.inventory.button.action.impl.ClickableButtonAction;
import ru.stonlex.bukkit.inventory.button.action.impl.DraggableButtonAction;

public interface BaseInventoryContainer {

    /**
     * Получить название инвентаря
     */
    String getInventoryTitle();

    /**
     * Получить колчество линий разметки инвентаря
     */
    int getInventoryRows();

    /**
     * Получить размер инвентаря
     */
    int getInventorySize();


    /**
     * Придать инвентарю новое название
     *
     * @param title - название
     */
    void setInventoryTitle(String title);

    /**
     * Установить инвентарю новое количество
     * линий в рзаметке
     *
     * @param rowsCount - кол-во линий в разметке
     */
    void setInventoryRows(int rowsCount);

    /**
     * Установить инвентарю новый размер
     *
     * @param inventorySize - размер
     */
    void setInventorySize(int inventorySize);


    /**
     * Установить предмет в разметку инвентаря
     *
     * @param inventoryButton - предмет
     */
    void setItem(int buttonSlot, BaseInventoryButton inventoryButton);

    /**
     * Установить оригинальный предмет в разметку инвентаря
     *
     * @param buttonSlot - слот предмета
     * @param itemStack - предмет
     */
    void setOriginalItem(int buttonSlot, ItemStack itemStack);

    /**
     * Установить кликабельный предмет в разметку инвентаря
     *
     * @param buttonSlot - слот предмета
     * @param itemStack - предмет
     * @param buttonAction - действие при клике на предмет
     */
    void setClickItem(int buttonSlot,
                 ItemStack itemStack,
                 ClickableButtonAction buttonAction);

    /**
     * Установить кликабельный предмет в разметку инвентаря
     *
     * @param buttonSlot - слот предмета
     * @param itemStack - предмет
     * @param buttonAction - действие при клике на предмет
     */
    void setDragItem(int buttonSlot,
                 ItemStack itemStack,
                 DraggableButtonAction buttonAction);


    /**
     * Получить кнокпи, инициализированные в инвентаре
     */
    TIntObjectMap<BaseInventoryButton> getButtons();

}
