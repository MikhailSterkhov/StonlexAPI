package ru.stonlex.bukkit.inventory.manager;

import lombok.Getter;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.inventory.IBukkitInventory;

import java.util.HashMap;
import java.util.Map;

public final class BukkitInventoryManager {

    @Getter
    private final Map<String, IBukkitInventory> playerInventoryMap = new HashMap<>();


    /**
     * Получить открытый самописный инвентарь игрока
     *
     * @param playerName - ник игрока
     */
    public IBukkitInventory getOpenInventory(String playerName) {
        return playerInventoryMap.get(playerName.toLowerCase());
    }

    /**
     * Получить открытый самописный инвентарь игрока
     *
     * @param player - игрок
     */
    public IBukkitInventory getOpenInventory(Player player) {
        return getOpenInventory(player.getName());
    }


    public void addOpenInventoryToPlayer(String playerName, IBukkitInventory bukkitInventory) {
        playerInventoryMap.put(playerName.toLowerCase(), bukkitInventory);
    }

    public void addOpenInventoryToPlayer(Player player, IBukkitInventory bukkitInventory) {
        addOpenInventoryToPlayer(player.getName(), bukkitInventory);
    }


    public void removeOpenInventoryToPlayer(String playerName) {
        playerInventoryMap.remove(playerName.toLowerCase());
    }

    public void removeOpenInventoryToPlayer(Player player) {
        removeOpenInventoryToPlayer(player.getName());
    }
}
