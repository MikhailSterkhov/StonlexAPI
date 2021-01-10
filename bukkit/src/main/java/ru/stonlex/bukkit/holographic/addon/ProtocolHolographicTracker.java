package ru.stonlex.bukkit.holographic.addon;

import org.bukkit.entity.Player;
import ru.stonlex.bukkit.holographic.ProtocolHolographic;

public interface ProtocolHolographicTracker {

    /**
     * Что будем делать при показе голограммы игроку
     *
     * @param player - игрок
     */
    void onHolographicShow(Player player);

    /**
     * Что будем делать при скрытии голограммы игроку
     *
     * @param player - игрок
     */
    void onHolographicHide(Player player);


    /**
     * Получить дистанцию трекинга голограммы
     * в блоках
     */
    int getTrackDistance();


    /**
     * Получить голограмму, с которой работаем
     */
    ProtocolHolographic getHolographic();
}
