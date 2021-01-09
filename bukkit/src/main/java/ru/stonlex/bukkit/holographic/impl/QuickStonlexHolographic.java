package ru.stonlex.bukkit.holographic.impl;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.stonlex.bukkit.StonlexBukkitApiPlugin;
import ru.stonlex.bukkit.holographic.IProtocolHolographic;
import ru.stonlex.bukkit.holographic.addon.IProtocolHolographicTracker;
import ru.stonlex.bukkit.holographic.addon.IProtocolHolographicUpdater;
import ru.stonlex.bukkit.holographic.line.IProtocolHolographicLine;
import ru.stonlex.bukkit.holographic.line.impl.ClickableStonlexHolographicLine;
import ru.stonlex.bukkit.holographic.line.impl.EmptyStonlexHolographicLine;
import ru.stonlex.bukkit.holographic.line.impl.HeadStonlexHolographicLine;
import ru.stonlex.bukkit.holographic.line.impl.OriginalStonlexHolographicLine;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class QuickStonlexHolographic implements IProtocolHolographic {

    private Location location;

    private IProtocolHolographicUpdater holographicUpdater;
    private IProtocolHolographicTracker holographicTracker;

    private final List<IProtocolHolographicLine> holographicLines = new LinkedList<>();
    private final List<Player> receivers = new LinkedList<>();


    public QuickStonlexHolographic(Location location) {
        this.location = location;
    }


    @Override
    public IProtocolHolographicLine getHolographicLine(int lineIndex) {
        return holographicLines.get(lineIndex);
    }


    @Override
    public void setHolographicLine(int lineIndex, @NonNull IProtocolHolographicLine holographicLine) {
        holographicLines.get(lineIndex).remove();
        holographicLines.set(lineIndex, holographicLine);

        receivers.forEach(player -> {

            if (player == null) {
                return;
            }

            holographicLine.showToPlayer(player);
        });
    }

    @Override
    public void setOriginalHolographicLine(int lineIndex, String holographicLine) {
        setHolographicLine(lineIndex, new OriginalStonlexHolographicLine(lineIndex, holographicLine, this));
    }

    @Override
    public void setClickHolographicLine(int lineIndex, String holographicLine, Consumer<Player> clickAction) {
        setHolographicLine(lineIndex, new ClickableStonlexHolographicLine(lineIndex, holographicLine, this, clickAction));
    }

    @Override
    public void setHeadHolographicLine(int lineIndex, String headTexture, boolean small) {
        setHolographicLine(lineIndex, new HeadStonlexHolographicLine(lineIndex, headTexture, small,this));
    }

    @Override
    public void setEmptyHolographicLine(int lineIndex) {
        setHolographicLine(lineIndex, new EmptyStonlexHolographicLine(lineIndex, this));
    }


    @Override
    public void addHolographicLine(@NonNull IProtocolHolographicLine holographicLine) {
        holographicLines.add(holographicLine);
    }

    @Override
    public void addOriginalHolographicLine(String holographicLine) {
        addHolographicLine(new OriginalStonlexHolographicLine(holographicLines.size(), holographicLine, this));
    }

    @Override
    public void addClickHolographicLine(String holographicLine, Consumer<Player> clickAction) {
        addHolographicLine(new ClickableStonlexHolographicLine(holographicLines.size(), holographicLine, this, clickAction));
    }

    @Override
    public void addHeadHolographicLine(String headTexture, boolean small) {
        addHolographicLine(new HeadStonlexHolographicLine(holographicLines.size(), headTexture, small, this));
    }

    @Override
    public void addEmptyHolographicLine() {
        addHolographicLine(new EmptyStonlexHolographicLine(holographicLines.size(), this));
    }


    @Override
    public boolean isSpawnedToPlayer(@NonNull Player player) {
        return receivers.contains(player);
    }

    @Override
    public void showToPlayer(@NonNull Player player) {
        showToPlayer(player, 20 * 3);
    }

    /**
     * Показать голограмму игроку на указанный срок времени
     *
     * @param player - игрок
     * @param hideTicks - время в тиках
     */
    public void showToPlayer(@NonNull Player player, long hideTicks) {
        receivers.add(player);

        for (IProtocolHolographicLine holographicLine : holographicLines) {
            holographicLine.showToPlayer(player);
        }

        StonlexBukkitApiPlugin.getInstance().getHolographicManager()
                .addProtocolHolographic(player, this);

        //удаляем голограмму через указанное время
        new BukkitRunnable() {

            @Override
            public void run() {
                hideToPlayer(player);
            }

        }.runTaskLater(StonlexBukkitApiPlugin.getInstance(), hideTicks);
    }

    @Override
    public void hideToPlayer(@NonNull Player player) {
        receivers.remove(player);

        for (IProtocolHolographicLine holographicLine : holographicLines) {
            holographicLine.hideToPlayer(player);
        }

        StonlexBukkitApiPlugin.getInstance().getHolographicManager()
                .getPlayerHolographics().remove(player);
    }


    @Override
    public void teleport(@NonNull Location location) {
        this.location = location;

        for (IProtocolHolographicLine holographicLine : holographicLines) {
            holographicLine.teleport(location);
        }
    }

    @Override
    public void setHolographicTracker(@NonNull IProtocolHolographicTracker holographicTracker) {
        this.holographicTracker = holographicTracker;

        StonlexBukkitApiPlugin.getInstance().getHolographicManager()
                .addHolographicToTracking(this);
    }

    @Override
    public void setHolographicUpdater(long updateTicks, @NonNull IProtocolHolographicUpdater holographicUpdater) {
        this.holographicUpdater = holographicUpdater;

        holographicUpdater.setEnable(true);
        holographicUpdater.startUpdater(updateTicks);
    }
}
