package ru.stonlex.bukkit.utility.location;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Objects;

@UtilityClass
public class LocationUtil {

    /**
     * Преобразование локации в строку подобного вида:
     *  - 'world_name, x, y, z, yaw, pitch'
     */
    public String locationToString(Location location) {
        if (location == null) {
            return null;
        }

        return String.format("%s, %s, %s, %s, %s, %s", location.getWorld().getName(),
                location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    /**
     * Преобразование строки с координатами в саму локацию
     */
    public Location stringToLocation(String locString) {
        if (locString == null) {
            return null;
        }

        String[] locData = locString.split(", ");
        World world = Bukkit.getWorld(locData[0]);

        Objects.requireNonNull(world, "world is null");

        return new Location(world, Double.parseDouble(locData[1]), Double.parseDouble(locData[2]), Double.parseDouble(locData[3]),
                Float.parseFloat(locData[4]), Float.parseFloat(locData[5]));
    }

    /**
     * Проверка дистанции локаций
     */
    public boolean isDistance(Location location1, Location location2, double distance) {
        return location1.distance(location2) <= distance;
    }

}
