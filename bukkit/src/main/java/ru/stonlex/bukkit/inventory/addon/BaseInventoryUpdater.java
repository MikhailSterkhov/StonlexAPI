package ru.stonlex.bukkit.inventory.addon;

import org.bukkit.entity.Player;

public interface BaseInventoryUpdater {

    /**
     * Запустить шедулер автообновления
     * инвентаря
     *
     * @param periodTick - период обновления в тиках
     */
    void startUpdater(long periodTick);

    /**
     * Отменить и выключить автообновление
     * инвентаря
     */
    void cancelUpdater();


    /**
     * Включить автообновление инвентаря
     *
     * @param enable - условный режим (вкл/выкл)
     */
    void setEnable(boolean enable);

    /**
     * Проверить автообновление на то,
     * включена ли она
     */
    boolean isEnable();


    /**
     * Проверить на то, запущено ли
     * автообновление для данного инвентаря, или выключен
     */
    boolean isCancelled();


    /**
     * Применить для игрока
     *
     * @param targetPlayer - игрок
     */
    void applyRunnable(Player targetPlayer);
}
