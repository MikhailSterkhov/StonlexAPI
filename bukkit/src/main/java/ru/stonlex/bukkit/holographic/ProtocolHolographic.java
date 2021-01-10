package ru.stonlex.bukkit.holographic;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.holographic.addon.ProtocolHolographicTracker;
import ru.stonlex.bukkit.holographic.addon.ProtocolHolographicUpdater;
import ru.stonlex.bukkit.holographic.line.ProtocolHolographicLine;

import java.util.List;
import java.util.function.Consumer;

public interface ProtocolHolographic extends ProtocolHolographicSpawnable {

    ProtocolHolographicTracker getHolographicTracker();

    Location getLocation();


    List<ProtocolHolographicLine> getHolographicLines();

    ProtocolHolographicUpdater getHolographicUpdater();

    ProtocolHolographicLine getHolographicLine(int lineIndex);


    void setHolographicLine(int lineIndex, ProtocolHolographicLine holographicLine);


    void setOriginalHolographicLine(int lineIndex, String holographicLine);

    void setClickHolographicLine(int lineIndex, String holographicLine, Consumer<Player> clickAction);

    void setHeadHolographicLine(int lineIndex, String headTexture, boolean small);

    void setEmptyHolographicLine(int lineIndex);


    void addHolographicLine(ProtocolHolographicLine holographicLine);

    void addOriginalHolographicLine(String holographicLine);

    void addClickHolographicLine(String holographicLine, Consumer<Player> clickAction);

    void addHeadHolographicLine(String headTexture, boolean small);

    void addEmptyHolographicLine();


    void teleport(Location location);

    void setHolographicTracker(ProtocolHolographicTracker holographicTracker);


    void setHolographicUpdater(long updateTicks, ProtocolHolographicUpdater holographicUpdater);
}
