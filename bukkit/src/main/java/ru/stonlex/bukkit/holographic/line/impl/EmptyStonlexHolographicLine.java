package ru.stonlex.bukkit.holographic.line.impl;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.holographic.IProtocolHolographic;
import ru.stonlex.bukkit.holographic.line.IProtocolHolographicLine;

@Getter
public class EmptyStonlexHolographicLine implements IProtocolHolographicLine {

    private final int lineIndex;

    private final IProtocolHolographic holographic;

    private Location location;


    public EmptyStonlexHolographicLine(int lineIndex, IProtocolHolographic holographic) {
        this.lineIndex = lineIndex;
        this.holographic = holographic;
        this.location = holographic.getLocation();

        this.create();
    }


    @Override
    public String getLineText() {
        return "";
    }

    @Override
    public void setLineText(String lineText) { }

    @Override
    public void create() {
        this.location = location.clone().add(0, -(0.25 * lineIndex), 0);
    }

    @Override
    public void update() { }

    @Override
    public void remove() { }

    @Override
    public void teleport(Location location) {
        this.location = location;

        create();
    }

    @Override
    public boolean isSpawnedToPlayer(Player player) {
        return true;
    }

    @Override
    public void showToPlayer(Player player) { }

    @Override
    public void hideToPlayer(Player player) { }

}
