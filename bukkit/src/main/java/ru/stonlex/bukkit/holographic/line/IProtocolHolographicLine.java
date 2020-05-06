package ru.stonlex.bukkit.holographic.line;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.holographic.IProtocolHolographic;
import ru.stonlex.bukkit.holographic.IProtocolHolographicSpawnable;

public interface IProtocolHolographicLine extends IProtocolHolographicSpawnable {

    int getLineIndex();

    Location getLocation();

    IProtocolHolographic getHolographic();


    String getLineText();

    void setLineText(String lineText);


    void create();

    void update();

    void remove();


    void teleport(Location location);
}
