package ru.stonlex.bukkit.holographic.manager;

import lombok.Getter;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.holographic.IProtocolHolographic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ProtocolHolographicManager {

    @Getter
    private final Map<Player, List<IProtocolHolographic>> playerHolographics = new HashMap<>();


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

}
