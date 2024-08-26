package eu.boxhunt.lobby.object.region;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class Region implements Serializable {

    private final Location pos1;
    private final Location pos2;

     public boolean isLocationInRegion(Location location) {
        double minX = Math.min(pos1.getX(), pos2.getX());
        double maxX = Math.max(pos1.getX(), pos2.getX());

        double minY = Math.min(pos1.getY(), pos2.getY());
        double maxY = Math.max(pos1.getY(), pos2.getY());

        double minZ = Math.min(pos1.getZ(), pos2.getZ());
        double maxZ = Math.max(pos1.getZ(), pos2.getZ());

        double locX = location.getX();
        double locY = location.getY();
        double locZ = location.getZ();

        return (locX >= minX && locX <= maxX) &&
                (locY >= minY && locY <= maxY) &&
                (locZ >= minZ && locZ <= maxZ);
    }

    public void showParticles(Particle particle, double step) {
        World world = pos1.getWorld();
        double minX = Math.min(pos1.getX(), pos2.getX());
        double maxX = Math.max(pos1.getX(), pos2.getX());

        double minY = Math.min(pos1.getY(), pos2.getY());
        double maxY = Math.max(pos1.getY(), pos2.getY());

        double minZ = Math.min(pos1.getZ(), pos2.getZ());
        double maxZ = Math.max(pos1.getZ(), pos2.getZ());

        // X
        for (double x = minX; x <= maxX; x += step) {
            world.spawnParticle(particle, new Location(world, x, minY, minZ), 1);
            world.spawnParticle(particle, new Location(world, x, minY, maxZ), 1);
            world.spawnParticle(particle, new Location(world, x, maxY, minZ), 1);
            world.spawnParticle(particle, new Location(world, x, maxY, maxZ), 1);
        }

        // Y
        for (double y = minY; y <= maxY; y += step) {
            world.spawnParticle(particle, new Location(world, minX, y, minZ), 1);
            world.spawnParticle(particle, new Location(world, minX, y, maxZ), 1);
            world.spawnParticle(particle, new Location(world, maxX, y, minZ), 1);
            world.spawnParticle(particle, new Location(world, maxX, y, maxZ), 1);
        }

        // Z
        for (double z = minZ; z <= maxZ; z += step) {
            world.spawnParticle(particle, new Location(world, minX, minY, z), 1);
            world.spawnParticle(particle, new Location(world, minX, maxY, z), 1);
            world.spawnParticle(particle, new Location(world, maxX, minY, z), 1);
            world.spawnParticle(particle, new Location(world, maxX, maxY, z), 1);
        }
    }
}