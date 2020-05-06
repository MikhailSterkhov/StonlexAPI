package ru.stonlex.bukkit.holographic;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.holographic.addon.IProtocolHolographicTracker;
import ru.stonlex.bukkit.holographic.addon.IProtocolHolographicUpdater;
import ru.stonlex.bukkit.holographic.line.IProtocolHolographicLine;

import java.util.List;
import java.util.function.Consumer;

public interface IProtocolHolographic extends IProtocolHolographicSpawnable {

    Location getLocation();


    List<IProtocolHolographicLine> getHolographicLines();

    IProtocolHolographicUpdater getHolographicUpdater();

    IProtocolHolographicLine getHolographicLine(int lineIndex);


    void setHolographicLine(int lineIndex, IProtocolHolographicLine holographicLine);


    void setOriginalHolographicLine(int lineIndex, String holographicLine);

    void setClickHolographicLine(int lineIndex, String holographicLine, Consumer<Player> clickAction);

    void setHeadHolographicLine(int lineIndex, String headTexture, boolean small);

    void setEmptyHolographicLine(int lineIndex);


    void addHolographicLine(IProtocolHolographicLine holographicLine);

    void addOriginalHolographicLine(String holographicLine);

    void addClickHolographicLine(String holographicLine, Consumer<Player> clickAction);

    void addHeadHolographicLine(String headTexture, boolean small);

    void addEmptyHolographicLine();


    void teleport(Location location);

    void registerHolographicTracker(IProtocolHolographicTracker holographicTracker);


    void setHolographicUpdater(long updateTicks, IProtocolHolographicUpdater holographicUpdater);
}
