package ru.stonlex.bukkit.holographic.updater;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.scheduler.BukkitRunnable;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.holographic.IProtocolHolographic;
import ru.stonlex.bukkit.holographic.addon.IProtocolHolographicUpdater;

import java.util.function.Consumer;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class StonlexHolographicUpdater extends BukkitRunnable implements IProtocolHolographicUpdater, Consumer<IProtocolHolographic> {

    private final IProtocolHolographic holographic;

    private boolean enable;
    private boolean cancelled;


    @Override
    public void run() {
        this.accept(holographic);
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
