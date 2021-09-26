package ru.stonlex.bukkit.holographic.impl;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.StonlexBukkitApi;
import ru.stonlex.bukkit.holographic.ProtocolHolographic;
import ru.stonlex.bukkit.holographic.ProtocolHolographicLine;
import ru.stonlex.bukkit.holographic.ProtocolHolographicUpdater;
import ru.stonlex.bukkit.holographic.line.*;

import java.util.*;
import java.util.function.Consumer;

@Getter
public class SimpleHolographic implements ProtocolHolographic {

    private Location location;

    private ProtocolHolographicUpdater holographicUpdater;

    private final List<ProtocolHolographicLine> holographicLines = new LinkedList<>();

    private final Set<Player> receivers    = new LinkedHashSet<>();
    private final Set<Player> viewers      = new LinkedHashSet<>();


    public SimpleHolographic(Location location) {
        this.location = location;
    }


    @Override
    public ProtocolHolographicLine getHolographicLine(int lineIndex) {
        if (lineIndex >= holographicLines.size())
            return null;

        return holographicLines.get(lineIndex);
    }


    @Override
    public void setHolographicLine(int lineIndex, @NonNull ProtocolHolographicLine holographicLine) {
        if (lineIndex >= holographicLines.size()) {
            addHolographicLine(holographicLine);
            return;
        }

        ProtocolHolographicLine oldLine = getHolographicLine(lineIndex);
        if (oldLine != null && oldLine.getClass().equals(holographicLine.getClass())) {

            oldLine.setLineText(holographicLine.getLineText());
            oldLine.update();
            return;
        }

        holographicLines.set(lineIndex, holographicLine);

        if (oldLine != null) {
            oldLine.remove();
        }

        holographicLine.initialize();
        holographicLine.spawn();
    }

    @Override
    public void setTextLine(int lineIndex, String holographicLine) {
        setHolographicLine(lineIndex, new TextHolographicLine(lineIndex, holographicLine, this));
    }

    @Override
    public void setClickLine(int lineIndex, String holographicLine, Consumer<Player> clickAction) {
        setHolographicLine(lineIndex, new ActionHolographicLine(lineIndex, holographicLine, this, clickAction));
    }

    @Override
    public void setSkullLine(int lineIndex, String headTexture, boolean small) {
        setHolographicLine(lineIndex, new SkullHolographicLine(lineIndex, headTexture, small, this));
    }

    @Override
    public void setDropLine(int lineIndex, ItemStack itemStack) {
        setHolographicLine(lineIndex, new ItemHolographicLine(lineIndex, itemStack, this));
    }

    @Override
    public void setEmptyLine(int lineIndex) {
        setHolographicLine(lineIndex, new EmptyHolographicLine(lineIndex, this));
    }


    @Override
    public void addHolographicLine(@NonNull ProtocolHolographicLine holographicLine) {
        holographicLine.initialize();
        holographicLines.add(holographicLine);

        holographicLine.addReceivers(receivers.toArray(new Player[0]));
    }

    @Override
    public void addTextLine(String holographicLine) {
        addHolographicLine(new TextHolographicLine(holographicLines.size(), holographicLine, this));
    }

    @Override
    public void addClickLine(String holographicLine, Consumer<Player> clickAction) {
        addHolographicLine(new ActionHolographicLine(holographicLines.size(), holographicLine, this, clickAction));
    }

    @Override
    public void addSkullLine(String headTexture, boolean small) {
        addHolographicLine(new SkullHolographicLine(holographicLines.size(), headTexture, small, this));
    }

    @Override
    public void addDropLine(ItemStack itemStack) {
        addHolographicLine(new ItemHolographicLine(holographicLines.size(), itemStack, this));
    }

    @Override
    public void addEmptyLine() {
        addHolographicLine(new EmptyHolographicLine(holographicLines.size(), this));
    }


    @Override
    public boolean hasReceiver(@NonNull Player player) {
        return receivers.contains(player);
    }

    @Override
    public void addReceivers(@NonNull Player... players) {
        receivers.addAll(Arrays.asList(players));
        addViewers(players);

        for (ProtocolHolographicLine holographicLine : holographicLines) {
            holographicLine.addReceivers(players);
        }
    }

    @Override
    public void removeReceivers(@NonNull Player... players) {
        receivers.removeAll(Arrays.asList(players));
        removeViewers(players);

        for (ProtocolHolographicLine holographicLine : holographicLines) {
            holographicLine.removeReceivers(players);
        }
    }

    @Override
    public boolean hasViewer(@NonNull Player player) {
        return viewers.contains(player);
    }

    @Override
    public void addViewers(@NonNull Player... players) {
        viewers.addAll(Arrays.asList(players));

        for (ProtocolHolographicLine holographicLine : holographicLines) {
            holographicLine.addViewers(players);
        }

        for (Player player : players) {
            StonlexBukkitApi.HOLOGRAPHIC_MANAGER.addProtocolHolographic(player, this);
        }
    }

    @Override
    public void removeViewers(@NonNull Player... players) {
        viewers.removeAll(Arrays.asList(players));

        for (ProtocolHolographicLine holographicLine : holographicLines) {
            holographicLine.removeViewers(players);
        }

        for (Player player : players) {
            StonlexBukkitApi.HOLOGRAPHIC_MANAGER.removeProtocolHolographic(player, this);
        }
    }


    @Override
    public void spawn() {
        for (ProtocolHolographicLine holographicLine : holographicLines)
            holographicLine.spawn();
    }

    @Override
    public void remove() {
        for (ProtocolHolographicLine holographicLine : holographicLines)
            holographicLine.remove();
    }

    @Override
    public void update() {
        for (ProtocolHolographicLine holographicLine : holographicLines) {

            holographicLine.addReceivers(receivers.toArray(new Player[0]));
            holographicLine.addViewers(viewers.toArray(new Player[0]));

            holographicLine.update();
        }
    }


    @Override
    public void teleport(@NonNull Location location) {
        this.location = location;

        for (ProtocolHolographicLine holographicLine : holographicLines) {
            holographicLine.teleport(location);
        }
    }

    @Override
    public void setFullClickAction(@NonNull Consumer<Player> clickAction) {
        for (ProtocolHolographicLine holographicLine : holographicLines) {
            if (holographicLine.getFakeArmorStand() == null)
                continue;

            holographicLine.getFakeArmorStand().setClickAction(clickAction);
        }
    }

    @Override
    public void setUpdater(long updateTicks, @NonNull ProtocolHolographicUpdater holographicUpdater) {
        this.holographicUpdater = holographicUpdater;

        holographicUpdater.setEnable(true);
        holographicUpdater.startUpdater(updateTicks);
    }

}
