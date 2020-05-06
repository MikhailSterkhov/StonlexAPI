package ru.stonlex.bukkit.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.inventory.addon.IPaginatedBukkitInventorySortable;
import ru.stonlex.bukkit.inventory.button.IBukkitInventoryButton;
import ru.stonlex.bukkit.inventory.button.action.impl.ClickableButtonAction;
import ru.stonlex.bukkit.inventory.button.action.impl.DraggableButtonAction;

import java.util.List;

public interface IPaginatedBukkitInventory extends IBukkitInventory {

    List<IBukkitInventoryButton> getPageButtons();


    IPaginatedBukkitInventorySortable getInventorySort();

    void setInventorySort(IPaginatedBukkitInventorySortable inventorySort);


    /**
     * Добавить предмет в разметку страницы
     *
     * @param inventoryButton - предмет
     */
    void addItemToMarkup(IBukkitInventoryButton inventoryButton);

    /**
     * Добавить предмет в разметку страницы
     *
     * @param itemStack - предмет
     */
    void addOriginalItemToMarkup(ItemStack itemStack);

    /**
     * Добавить предмет в разметку страницы
     *
     * @param itemStack - предмет
     */
    void addClickItemToMarkup(ItemStack itemStack, ClickableButtonAction buttonAction);

    /**
     * Добавить предмет в разметку страницы
     *
     * @param itemStack - предмет
     */
    void addDragItemToMarkup(ItemStack itemStack, DraggableButtonAction buttonAction);


    /**
     * Установить количество используемых слотов на страницу
     *
     * @param slotArray - слоты
     */
    void setMarkupSlots(Integer... slotArray);

    /**
     * Установить количество используемых слотов на страницу
     *
     * @param slotList - слоты
     */
    void setMarkupSlots(List<Integer> slotList);

    /**
     * Добавить линию в разметку страницы
     *
     * @param rowIndex    - индекс линии
     * @param sideTab     - отступ по слотам для боков линии
     */
    void addRowToMarkup(int rowIndex, int sideTab);


    /**
     * На страницу назад
     *
     * @param player - игрок
     */
    void backwardPage(Player player);

    /**
     * На страницу вперед
     *
     * @param player - игрок
     * @param allPagesCount - всего страниц
     */
    void forwardPage(Player player, int allPagesCount);
}
