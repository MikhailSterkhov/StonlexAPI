package ru.stonlex.bukkit.holographic.addon;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import ru.stonlex.bukkit.holographic.IProtocolHolographic;

public interface IProtocolHolographicTracker {

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
    IProtocolHolographic getHolographic();
}
