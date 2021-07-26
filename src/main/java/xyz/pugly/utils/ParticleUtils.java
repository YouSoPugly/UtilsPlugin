package xyz.pugly.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public class ParticleUtils {

    public static void circle(double size, int particles, Particle particle, Location location) {
        for (int i = 0; i < particles; i ++) {
            location.getWorld().spawnParticle(particle, location.clone().add(Math.cos(2*Math.PI/particles * i)*size, 0, Math.sin(2*Math.PI/particles * i)*size), 1, 0, 0, 0, 0);
        }
    }

    public static void helix(double radius, double height, int particles, Particle particle, Location location) {
        for (int i = 0; i < particles; i ++) {
            double rad = 2*Math.PI/particles * i;
            double dist = (radius-(radius/particles)*i);
            location.getWorld().spawnParticle(particle, location.clone().add(Math.cos(rad)*dist, (height/particles)*i, Math.sin(rad)*dist), 1, 0, 0, 0, 0);
            location.getWorld().spawnParticle(particle, location.clone().add(-Math.cos(rad)*dist, (height/particles)*i, -Math.sin(rad)*dist), 1, 0, 0, 0, 0);
        }
    }

    public static void randomLightning(double blocks, int dirChanges, int strands, Particle particle, Location location) {
        for (int i = 0; i < strands; i ++) {
            Location locationClone = location.clone();
            for (int k = 0; k < dirChanges; k++) {
                Vector dir = new Vector(Math.random() - 0.5, Math.random()/2, Math.random() - 0.5).multiply(0.3);
                for (int j = 0; j < blocks*3; j++) {
                    location.getWorld().spawnParticle(particle, locationClone.add(dir).add( new Vector(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5).multiply(0.3).multiply(0.4)), 1, 0, 0, 0, 0);
                }
            }
        }
    }

    public static void line(Location loc1, Location loc2, int particles, Particle particle, double variation) {
        double distance = loc1.distance(loc2);
        Location start = loc1.clone();
        double particlesPerUnit = particles/distance;
        Vector dir = loc1.toVector().clone().subtract(loc2.toVector()).normalize().divide(new Vector(particlesPerUnit, particlesPerUnit, particlesPerUnit));

        for (int i = 0; i < particles; i++) {
            Vector offset = Vector.getRandom().subtract(new Vector(0.5, 0.5, 0.5)).multiply(variation);
            loc1.getWorld().spawnParticle(particle, start.add(dir).clone().add(offset), 1);
        }
    }

    public static void lightning(Location start, Location end, int dirChanges, int particlesPerLine, Particle particle) {

        Location minPoint = MiscUtils.minCorner(start, end);
        Location maxPoint = MiscUtils.maxCorner(start, end);
        Location prev = start.clone();

        for (int i = 0; i < dirChanges-1; i++) {

            double x = Math.random()*(maxPoint.getX() - minPoint.getX()) + minPoint.getX();
            double y = Math.random()*(maxPoint.getY() - minPoint.getY()) + minPoint.getY();
            double z = Math.random()*(maxPoint.getZ() - minPoint.getZ()) + minPoint.getZ();

            Location random = new Location(start.getWorld(), x, y, z);

            line(prev, random, particlesPerLine, particle, 0.4);
            prev = random;
        }

        line(prev, end, particlesPerLine, particle, 0.4);
    }
}
