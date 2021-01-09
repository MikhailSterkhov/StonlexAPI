package ru.stonlex.bukkit.holographic.manager;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import ru.stonlex.bukkit.holographic.IProtocolHolographic;
import ru.stonlex.bukkit.holographic.addon.IProtocolHolographicTracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ProtocolHolographicManager implements Listener {

    @Getter
    private final Map<Player, List<IProtocolHolographic>> playerHolographics = new HashMap<>();

    @Getter
    private final List<IProtocolHolographic> holographicTrackingList = new ArrayList<>();


    @EventHandler
    public void onHolographicTrack(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        for (IProtocolHolographic protocolHolographic : holographicTrackingList) {
            IProtocolHolographicTracker holographicTracker = protocolHolographic.getHolographicTracker();

            int trackDistance = holographicTracker.getTrackDistance();

            //show hologram
            if (protocolHolographic.getLocation().distance(player.getLocation()) <= trackDistance && !protocolHolographic.isSpawnedToPlayer(player)) {
                holographicTracker.onHolographicShow(player);
                protocolHolographic.showToPlayer(player);

            }

            //hide hologram
            if (protocolHolographic.getLocation().distance(player.getLocation()) > trackDistance && protocolHolographic.isSpawnedToPlayer(player)) {
                holographicTracker.onHolographicHide(player);
                protocolHolographic.hideToPlayer(player);
            }
        }
    }


    /**
     * Получить список голограм, которые были показаны
     * для указанного игрока
     *
     * @param player - игрок
     */
    public List<IProtocolHolographic> getProtocolHolographics(Player player) {
        return playerHolographics.get(player);
    }

    /**
     * Кешировать голограмму для указанного игрока
     *
     * @param player - игрок
     * @param protocolHolographic - голограмма
     */
    public void addProtocolHolographic(Player player, IProtocolHolographic protocolHolographic) {
        playerHolographics.getOrDefault(player, new ArrayList<>()).add(protocolHolographic);
    }

    /**
     * Добавить голограмму в трекинг
     *
     * @param protocolHolographic - голограмма
     */
    public void addHolographicToTracking(IProtocolHolographic protocolHolographic) {
        holographicTrackingList.add(protocolHolographic);
    }

}
