package ru.stonlex.bukkit.inventory.updater;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.StonlexBukkitApiPlugin;
import ru.stonlex.bukkit.inventory.BaseInventory;
import ru.stonlex.bukkit.inventory.addon.BaseInventoryUpdater;
import ru.stonlex.bukkit.inventory.manager.BukkitInventoryManager;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class SimpleInventoryUpdater implements BaseInventoryUpdater {

    private BaseInventory inventory;

    private final Player player;

    private boolean enable;
    private boolean cancelled;


    @Override
    public void startUpdater(long periodTicks) {
        this.cancelled = !cancelled;

        if (isCancelled()) {
            BukkitInventoryManager.INSTANCE.addInventoryUpdater(this, periodTicks);
        }
    }

    @Override
    public void cancelUpdater() {
        this.cancelled = !cancelled;

        if (!isCancelled()) {
            BukkitInventoryManager.INSTANCE.removeInventoryUpdater(this);
        }
    }

}
