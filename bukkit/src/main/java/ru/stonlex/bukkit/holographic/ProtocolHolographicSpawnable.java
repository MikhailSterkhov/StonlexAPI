package ru.stonlex.bukkit.holographic;

import org.bukkit.entity.Player;

public interface ProtocolHolographicSpawnable {

    /**
     * Проверить, видит ли игрок голограмму
     *
     * @param player - игрок
     */
    boolean isSpawnedToPlayer(Player player);

    /**
     * Показать голограмму игроку
     *
     * @param player - игрок
     */
    void showToPlayer(Player player);

    /**
     * Скрыть голограмму от игрока
     *
     * @param player - игрок
     */
    void hideToPlayer(Player player);

}
