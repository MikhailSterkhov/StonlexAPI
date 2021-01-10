package ru.stonlex.bukkit.holographic.updater;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.scheduler.BukkitRunnable;
import ru.stonlex.bukkit.StonlexBukkitApiPlugin;
import ru.stonlex.bukkit.holographic.ProtocolHolographic;
import ru.stonlex.bukkit.holographic.addon.ProtocolHolographicUpdater;

import java.util.function.Consumer;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class SimpleHolographicUpdater extends BukkitRunnable implements ProtocolHolographicUpdater, Consumer<ProtocolHolographic> {

    private final ProtocolHolographic holographic;

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
            runTaskTimer(StonlexBukkitApiPlugin.getInstance(), 0, periodTick);
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
