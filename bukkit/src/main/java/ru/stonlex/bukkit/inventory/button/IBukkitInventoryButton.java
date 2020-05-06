package ru.stonlex.bukkit.inventory.button;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.inventory.button.action.EnumButtonAction;

public interface IBukkitInventoryButton {

    /**
     * Получить объект предмета
     */
    ItemStack getItemStack();

    /**
     * Получить кликабельность объекта
     *
     * Если ее нет, то метод будет
     * возвращать null
     */
    IInventoryButtonAction getButtonAction();


    interface IInventoryButtonAction {

        /**
         * Произвести клик по кнопки
         *
         * @param player       - игрок, который кликает
         * @param buttonAction - действие клика
         * @param event        - ивент, когда происходит взаимодействие с прелметом
         */
        void buttonAction(Player player, EnumButtonAction buttonAction, InventoryInteractEvent event);
    }

}
