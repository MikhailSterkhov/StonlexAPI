package ru.stonlex.bukkit.inventory.manager;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.inventory.IBukkitInventory;
import ru.stonlex.bukkit.inventory.addon.IBukkitInventoryUpdater;

import java.util.HashMap;
import java.util.Map;

public final class BukkitInventoryManager {

    @Getter
    private final Map<String, IBukkitInventory> playerInventoryMap = new HashMap<>();

    @Getter
    private final Map<IBukkitInventoryUpdater, Long> inventoryUpdaterMap = new HashMap<>();


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


    public void addInventoryUpdater(IBukkitInventoryUpdater inventoryUpdater, long periodTicks) {
        inventoryUpdaterMap.put(inventoryUpdater, periodTicks);
    }

    public void removeInventoryUpdater(IBukkitInventoryUpdater inventoryUpdater) {
        inventoryUpdaterMap.remove(inventoryUpdater);
    }

    public void startInventoryUpdaters() {
        new BukkitRunnable() {

            private long currentTicks = 1;

            @Override
            public void run() {
                inventoryUpdaterMap.forEach((inventoryUpdater, periodTicks) -> {
                    if (currentTicks % periodTicks != 0) {
                        return;
                    }

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        IBukkitInventory playerInventory = getOpenInventory(player);

                        if (playerInventory != null && playerInventory.getInventoryUpdater().equals(inventoryUpdater)) {
                            inventoryUpdater.applyRunnable(player);
                        }
                    }
                });

                currentTicks++;
            }

        }.runTaskTimer(BukkitAPI.getInstance(), 0, 1);
    }

}
