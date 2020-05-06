package ru.stonlex.bukkit.inventory.addon;

import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.inventory.IPaginatedBukkitInventory;
import ru.stonlex.global.ResponseHandler;

public interface IPaginatedBukkitInventorySortable {

    /**
     * Получить инвентарь, в котором сортируем
     * выставленные в разметку предметы
     */
    IPaginatedBukkitInventory getInventory();


    /**
     * Отсортировать предмет, получив его
     * индекс в списке предметов
     *
     * @param responseHandler - функция сортировки
     */
    IPaginatedBukkitInventorySortable sortItem(ResponseHandler<Integer, ItemStack> responseHandler);

    /**
     * Перевернуть список отсортированных предметов,
     * выставив их таким образом, чтобы в инвентаре
     * они показывались от большего к меньшему
     */
    IPaginatedBukkitInventorySortable reversed();


    /**
     * Перестраиваем положение предметов
     */
    void rebuildInventory();

}
