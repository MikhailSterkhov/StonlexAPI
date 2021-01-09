package ru.stonlex.bukkit.inventory.addon;

import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.inventory.PaginatedBaseInventory;
import ru.stonlex.global.utility.query.ResponseHandler;

public interface PaginatedBaseInventorySorting {

    /**
     * Получить инвентарь, в котором сортируем
     * выставленные в разметку предметы
     */
    PaginatedBaseInventory getInventory();


    /**
     * Отсортировать предмет, получив его
     * индекс в списке предметов
     *
     * @param responseHandler - функция сортировки
     */
    PaginatedBaseInventorySorting sortItem(ResponseHandler<Integer, ItemStack> responseHandler);

    /**
     * Перевернуть список отсортированных предметов,
     * выставив их таким образом, чтобы в инвентаре
     * они показывались от большего к меньшему
     */
    PaginatedBaseInventorySorting reversed();


    /**
     * Перестраиваем положение предметов
     */
    void rebuildInventory();

}
