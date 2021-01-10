package ru.stonlex.bukkit.inventory.addon;

import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.inventory.BasePaginatedInventory;
import ru.stonlex.global.utility.query.ResponseHandler;

public interface BasePaginatedInventorySorting {

    /**
     * Получить инвентарь, в котором сортируем
     * выставленные в разметку предметы
     */
    BasePaginatedInventory getInventory();


    /**
     * Отсортировать предмет, получив его
     * индекс в списке предметов
     *
     * @param responseHandler - функция сортировки
     */
    BasePaginatedInventorySorting sortItem(ResponseHandler<Integer, ItemStack> responseHandler);

    /**
     * Перевернуть список отсортированных предметов,
     * выставив их таким образом, чтобы в инвентаре
     * они показывались от большего к меньшему
     */
    BasePaginatedInventorySorting reversed();


    /**
     * Перестраиваем положение предметов
     */
    void rebuildInventory();

}
