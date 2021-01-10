package ru.stonlex.bukkit.holographic.line;

import org.bukkit.Location;
import ru.stonlex.bukkit.holographic.ProtocolHolographic;
import ru.stonlex.bukkit.holographic.ProtocolHolographicSpawnable;

public interface ProtocolHolographicLine extends ProtocolHolographicSpawnable {

    int getLineIndex();

    Location getLocation();

    ProtocolHolographic getHolographic();


    String getLineText();

    void setLineText(String lineText);


    void create();

    void update();

    void remove();


    void teleport(Location location);
}
