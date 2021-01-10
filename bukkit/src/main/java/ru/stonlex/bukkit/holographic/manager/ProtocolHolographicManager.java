package ru.stonlex.bukkit.holographic.manager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import ru.stonlex.bukkit.holographic.ProtocolHolographic;
import ru.stonlex.bukkit.holographic.addon.ProtocolHolographicTracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProtocolHolographicManager implements Listener {

    public static final ProtocolHolographicManager INSTANCE = new ProtocolHolographicManager();


    protected final Map<Player, List<ProtocolHolographic>> playerHolographics = new HashMap<>();
    protected final List<ProtocolHolographic> holographicTrackingList = new ArrayList<>();


    @EventHandler
    public void onHolographicTrack(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        for (ProtocolHolographic protocolHolographic : holographicTrackingList) {
            ProtocolHolographicTracker holographicTracker = protocolHolographic.getHolographicTracker();

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
    public List<ProtocolHolographic> getProtocolHolographics(Player player) {
        return playerHolographics.get(player);
    }

    /**
     * Кешировать голограмму для указанного игрока
     *
     * @param player - игрок
     * @param protocolHolographic - голограмма
     */
    public void addProtocolHolographic(Player player, ProtocolHolographic protocolHolographic) {
        playerHolographics.getOrDefault(player, new ArrayList<>()).add(protocolHolographic);
    }

    /**
     * Добавить голограмму в трекинг
     *
     * @param protocolHolographic - голограмма
     */
    public void addHolographicToTracking(ProtocolHolographic protocolHolographic) {
        holographicTrackingList.add(protocolHolographic);
    }

}
