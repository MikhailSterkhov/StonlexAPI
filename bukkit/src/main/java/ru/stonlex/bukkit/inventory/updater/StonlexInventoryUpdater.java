package ru.stonlex.bukkit.inventory.updater;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.inventory.addon.IBukkitInventoryUpdater;

import java.util.function.Consumer;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class StonlexInventoryUpdater extends BukkitRunnable implements IBukkitInventoryUpdater, Consumer<Player> {

    private final Player player;

    private boolean enable;
    private boolean cancelled;


    @Override
    public void run() {
        this.accept(player);
    }

    @Override
    public void startUpdater(long periodTick) {
        this.cancelled = !cancelled;

        if (isCancelled()) {
            runTaskTimer(BukkitAPI.getInstance(), 0, periodTick);
        }
    }

    @Override
    public void cancelUpdater() {
        this.cancelled = !cancelled;

        if (!isCancelled()) {
            cancel();
        }
    }

}
