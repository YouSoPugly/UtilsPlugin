package xyz.pugly.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import java.util.List;

public class MiscUtils {

    // Checks if two locations are the same, surprisingly useful lol
    public static boolean sameLocation(Location l1, Location l2) {
        if (l1.getWorld().equals(l2.getWorld())) {
            if (l1.getBlockX() == l2.getBlockX() && l1.getBlockY() == l2.getBlockY() && l1.getBlockZ() == l2.getBlockZ())
                return true;
        }
        return false;
    }

    public static double horizontalDistance(Location l1, Location l2) {
        return (Math.sqrt(Math.pow(l1.getX()-l2.getX(), 2) + Math.pow(l1.getZ()-l2.getZ(), 2)));
    }

    public static double verticalDistance(Location l1, Location l2) {
        return Math.abs(l1.getY() - l2.getY());
    }

    // Gets the entities that a minecraft selector selects, so @a would be all players
    public static List<Entity> getEntities(CommandSender sender, String selector) {

        return Bukkit.selectEntities(sender, selector);

    }

    // Checks if a player's view intersects with a sphere at a location without ray casting
    public static boolean viewIntersection(Location playerEyeLocation, Location intersect, double radius) {

        double xc = intersect.getX();
        double yc = intersect.getY();
        double zc = intersect.getZ();

        double x0 = playerEyeLocation.getX();
        double x1 = playerEyeLocation.getX() + playerEyeLocation.getDirection().multiply(6.3).getX();

        double y0 = playerEyeLocation.getY();
        double y1 = playerEyeLocation.getY() + playerEyeLocation.getDirection().multiply(6.3).getY();

        double z0 = playerEyeLocation.getZ();
        double z1 = playerEyeLocation.getZ() + playerEyeLocation.getDirection().multiply(6.3).getZ();

        double a = Math.pow(x0 - xc, 2) + Math.pow(y0 - yc, 2) + Math.pow(z0 - zc, 2) - Math.pow(radius, 2);
        double c = Math.pow(x0-x1, 2) + Math.pow(y0-y1, 2) + Math.pow(z0-z1, 2);
        double b = Math.pow(x1 - xc, 2) + Math.pow(y1 - yc, 2) + Math.pow(z1 - zc, 2) - a - c - Math.pow(radius, 2);

        double t = (-b + Math.sqrt(Math.pow(b, 2) - 4*a*c)) / (2*c);

        return (t>=0 && t <= 1);
    }

    public static Location minCorner(Location loc1, Location loc2) {
        return new Location(loc1.getWorld(), Math.min(loc1.getX(), loc2.getX()), Math.min(loc1.getY(), loc2.getY()), Math.min(loc1.getZ(), loc2.getZ()));
    }

    public static Location maxCorner(Location loc1, Location loc2) {
        return new Location(loc1.getWorld(), Math.max(loc1.getX(), loc2.getX()), Math.max(loc1.getY(), loc2.getY()), Math.max(loc1.getZ(), loc2.getZ()));
    }

}
