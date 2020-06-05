package ru.stonlex.bukkit.inventory.updater;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.inventory.IBukkitInventory;
import ru.stonlex.bukkit.inventory.addon.IBukkitInventoryUpdater;

import java.util.function.Consumer;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class StonlexInventoryUpdater implements IBukkitInventoryUpdater {

    private IBukkitInventory inventory;

    private final Player player;

    private boolean enable;
    private boolean cancelled;


    @Override
    public void startUpdater(long periodTicks) {
        this.cancelled = !cancelled;

        if (isCancelled()) {
            BukkitAPI.getInstance().getInventoryManager().addInventoryUpdater(this, periodTicks);
        }
    }

    @Override
    public void cancelUpdater() {
        this.cancelled = !cancelled;

        if (!isCancelled()) {
            BukkitAPI.getInstance().getInventoryManager().removeInventoryUpdater(this);
        }
    }

}
