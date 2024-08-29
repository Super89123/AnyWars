package org.relaxertime.anyWars.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {
    public static String locationToString(Location location) {
        return location.getWorld().getName() + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ();
    }

    public static Location locationFromString(String string) {
        String[] parts = string.split(";");
        if (parts.length == 4) {
            return new Location(Bukkit.getWorld(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        }
        return null;
    }
}
