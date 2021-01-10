package ru.stonlex.bukkit.holographic.impl;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.StonlexBukkitApi;
import ru.stonlex.bukkit.holographic.ProtocolHolographic;
import ru.stonlex.bukkit.holographic.addon.ProtocolHolographicTracker;
import ru.stonlex.bukkit.holographic.addon.ProtocolHolographicUpdater;
import ru.stonlex.bukkit.holographic.line.ProtocolHolographicLine;
import ru.stonlex.bukkit.holographic.line.impl.ActionHolographicLine;
import ru.stonlex.bukkit.holographic.line.impl.EmptyHolographicLine;
import ru.stonlex.bukkit.holographic.line.impl.HeadHolographicLine;
import ru.stonlex.bukkit.holographic.line.impl.SimpleHolographicLine;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class SimpleHolographic implements ProtocolHolographic {

    private Location location;

    private ProtocolHolographicUpdater holographicUpdater;
    private ProtocolHolographicTracker holographicTracker;

    private final List<ProtocolHolographicLine> holographicLines = new LinkedList<>();
    private final List<Player> receivers = new LinkedList<>();


    public SimpleHolographic(Location location) {
        this.location = location;
    }


    @Override
    public ProtocolHolographicLine getHolographicLine(int lineIndex) {
        return holographicLines.get(lineIndex);
    }


    @Override
    public void setHolographicLine(int lineIndex, @NonNull ProtocolHolographicLine holographicLine) {
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
        setHolographicLine(lineIndex, new SimpleHolographicLine(lineIndex, holographicLine, this));
    }

    @Override
    public void setClickHolographicLine(int lineIndex, String holographicLine, Consumer<Player> clickAction) {
        setHolographicLine(lineIndex, new ActionHolographicLine(lineIndex, holographicLine, this, clickAction));
    }

    @Override
    public void setHeadHolographicLine(int lineIndex, String headTexture, boolean small) {
        setHolographicLine(lineIndex, new HeadHolographicLine(lineIndex, headTexture, small, this));
    }

    @Override
    public void setEmptyHolographicLine(int lineIndex) {
        setHolographicLine(lineIndex, new EmptyHolographicLine(lineIndex, this));
    }


    @Override
    public void addHolographicLine(@NonNull ProtocolHolographicLine holographicLine) {
        holographicLines.add(holographicLine);
    }

    @Override
    public void addOriginalHolographicLine(String holographicLine) {
        addHolographicLine(new SimpleHolographicLine(holographicLines.size(), holographicLine, this));
    }

    @Override
    public void addClickHolographicLine(String holographicLine, Consumer<Player> clickAction) {
        addHolographicLine(new ActionHolographicLine(holographicLines.size(), holographicLine, this, clickAction));
    }

    @Override
    public void addHeadHolographicLine(String headTexture, boolean small) {
        addHolographicLine(new HeadHolographicLine(holographicLines.size(), headTexture, small, this));
    }

    @Override
    public void addEmptyHolographicLine() {
        addHolographicLine(new EmptyHolographicLine(holographicLines.size(), this));
    }


    @Override
    public boolean isSpawnedToPlayer(@NonNull Player player) {
        return receivers.contains(player);
    }

    @Override
    public void showToPlayer(@NonNull Player player) {
        receivers.add(player);

        for (ProtocolHolographicLine holographicLine : holographicLines) {
            holographicLine.showToPlayer(player);
        }

        StonlexBukkitApi.HOLOGRAPHIC_MANAGER.addProtocolHolographic(player, this);
    }

    @Override
    public void hideToPlayer(@NonNull Player player) {
        receivers.remove(player);

        for (ProtocolHolographicLine holographicLine : holographicLines) {
            holographicLine.hideToPlayer(player);
        }

        StonlexBukkitApi.HOLOGRAPHIC_MANAGER.getPlayerHolographics().remove(player);
    }


    @Override
    public void teleport(@NonNull Location location) {
        this.location = location;

        for (ProtocolHolographicLine holographicLine : holographicLines) {
            holographicLine.teleport(location);
        }
    }

    @Override
    public void setHolographicTracker(@NonNull ProtocolHolographicTracker holographicTracker) {
        this.holographicTracker = holographicTracker;

        StonlexBukkitApi.HOLOGRAPHIC_MANAGER.addHolographicToTracking(this);
    }

    @Override
    public void setHolographicUpdater(long updateTicks, @NonNull ProtocolHolographicUpdater holographicUpdater) {
        this.holographicUpdater = holographicUpdater;

        holographicUpdater.setEnable(true);
        holographicUpdater.startUpdater(updateTicks);
    }

}
