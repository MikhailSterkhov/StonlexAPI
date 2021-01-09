package ru.stonlex.bukkit.inventory.manager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.stonlex.bukkit.StonlexBukkitApiPlugin;
import ru.stonlex.bukkit.inventory.BaseInventory;
import ru.stonlex.bukkit.inventory.addon.BaseInventoryUpdater;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BukkitInventoryManager {

    public static final BukkitInventoryManager INSTANCE = new BukkitInventoryManager();


    @Getter
    private final Map<String, BaseInventory> playerInventoryMap = new HashMap<>();

    @Getter
    private final Map<BaseInventoryUpdater, Long> inventoryUpdaterMap = new HashMap<>();


    /**
     * Получить открытый самописный инвентарь игрока
     *
     * @param playerName - ник игрока
     */
    public BaseInventory getOpenInventory(String playerName) {
        return playerInventoryMap.get(playerName.toLowerCase());
    }

    /**
     * Получить открытый самописный инвентарь игрока
     *
     * @param player - игрок
     */
    public BaseInventory getOpenInventory(Player player) {
        return getOpenInventory(player.getName());
    }


    public void addOpenInventoryToPlayer(String playerName, BaseInventory bukkitInventory) {
        playerInventoryMap.put(playerName.toLowerCase(), bukkitInventory);
    }

    public void addOpenInventoryToPlayer(Player player, BaseInventory bukkitInventory) {
        addOpenInventoryToPlayer(player.getName(), bukkitInventory);
    }


    public void removeOpenInventoryToPlayer(String playerName) {
        playerInventoryMap.remove(playerName.toLowerCase());
    }

    public void removeOpenInventoryToPlayer(Player player) {
        removeOpenInventoryToPlayer(player.getName());
    }


    public void addInventoryUpdater(BaseInventoryUpdater inventoryUpdater, long periodTicks) {
        inventoryUpdaterMap.put(inventoryUpdater, periodTicks);
    }

    public void removeInventoryUpdater(BaseInventoryUpdater inventoryUpdater) {
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
                        BaseInventory playerInventory = getOpenInventory(player);

                        if (playerInventory != null && playerInventory.getInventoryUpdater().equals(inventoryUpdater)) {
                            inventoryUpdater.applyRunnable(player);
                        }
                    }
                });

                currentTicks++;
            }

        }.runTaskTimer(StonlexBukkitApiPlugin.getInstance(), 0, 1);
    }

}
