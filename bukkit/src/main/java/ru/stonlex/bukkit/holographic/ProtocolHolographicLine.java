package ru.stonlex.bukkit.holographic;

import lombok.NonNull;
import org.bukkit.Location;
import ru.stonlex.bukkit.protocollib.entity.impl.FakeArmorStand;

public interface ProtocolHolographicLine
        extends ProtocolHolographicSpawnable {

    double DEFAULT_LINE_DISTANCE = 0.27;


    int getLineIndex();

    double getLineDistance();

    Location getLocation();

    ProtocolHolographic getHolographic();

    FakeArmorStand getFakeArmorStand();

    String getLineText();

    void setLineText(String lineText);

    void initialize();

    void update();

    void remove();

    void teleport(Location location);


    default Location modifyNormalizeLocation(@NonNull Location normalizedLocation) {
        return normalizedLocation;
    }

    default Location normalizeLocation(@NonNull Location mainLocation) {
        double y = getHolographic().getHolographicLines()
                .stream()

                .filter(line -> line.getLineIndex() < getLineIndex())
                .mapToDouble(ProtocolHolographicLine::getLineDistance)

                .sum() + getLineDistance();

        return modifyNormalizeLocation(mainLocation.clone().subtract(0, y, 0));
    }

}
