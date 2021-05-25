package ru.stonlex.bukkit.holographic;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public interface ProtocolHolographic extends ProtocolHolographicSpawnable {

    Location getLocation();


    Set<Player> getViewers();

    Set<Player> getReceivers();


    List<ProtocolHolographicLine> getHolographicLines();

    ProtocolHolographicUpdater getHolographicUpdater();

    ProtocolHolographicLine getHolographicLine(int lineIndex);


    void setHolographicLine(int lineIndex, ProtocolHolographicLine holographicLine);


    void setTextLine(int lineIndex, String holographicLine);

    void setClickLine(int lineIndex, String holographicLine, Consumer<Player> clickAction);

    void setSkullLine(int lineIndex, String headTexture, boolean small);

    void setDropLine(int lineIndex, ItemStack itemStack);

    void setEmptyLine(int lineIndex);


    void addHolographicLine(ProtocolHolographicLine holographicLine);

    void addTextLine(String holographicLine);

    void addClickLine(String holographicLine, Consumer<Player> clickAction);

    void addSkullLine(String headTexture, boolean small);

    void addDropLine(ItemStack itemStack);

    void addEmptyLine();


    void teleport(Location location);


    void setUpdater(long updateTicks, ProtocolHolographicUpdater holographicUpdater);
}
