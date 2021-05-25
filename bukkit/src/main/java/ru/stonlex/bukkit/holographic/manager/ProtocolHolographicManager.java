package ru.stonlex.bukkit.holographic.manager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import ru.stonlex.bukkit.holographic.ProtocolHolographic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProtocolHolographicManager implements Listener {

    public static final ProtocolHolographicManager INSTANCE = new ProtocolHolographicManager();

    protected final Map<Player, List<ProtocolHolographic>> playerHolographics = new HashMap<>();


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

}
