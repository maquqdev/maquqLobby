package eu.boxhunt.lobby.object;

import com.destroystokyo.paper.ParticleBuilder;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class Cosmetic {
    private final CosmeticType type;
    private double animationStep = 0;

    public Cosmetic(CosmeticType type) {
        this.type = type;
    }

    public void display(Player player) {
        Location loc = player.getLocation();
        ParticleBuilder particleBuilder = new ParticleBuilder(type.getParticle())
                .count(1)
                .offset(0, 0, 0)
                .extra(0);

        if (type.getParticle() == Particle.REDSTONE) {
            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.GRAY, 1);
            particleBuilder.data(dustOptions);
        }

        switch (type) {
            case CIRCLE:
                displayCircle(loc, particleBuilder);
                break;
            case HELIX:
                displayHelix(loc, particleBuilder);
                break;
            case SPIRAL:
                displaySpiral(loc, particleBuilder);
                break;
            case FOUNTAIN:
                displayFountain(loc, particleBuilder);
                break;
            case WINGS:
                displayWings(loc, particleBuilder);
                break;
        }

        animationStep += 0.1;
        if (animationStep > Math.PI * 2) {
            animationStep = 0;
        }
    }

    private void displayCircle(Location center, ParticleBuilder particleBuilder) {
        for (int i = 0; i < 360; i += 10) {
            double angle = i * Math.PI / 180;
            double x = center.getX() + Math.cos(angle);
            double z = center.getZ() + Math.sin(angle);
            particleBuilder.location(new Location(center.getWorld(), x, center.getY() + 1, z)).spawn();
        }
    }

    private void displayHelix(Location center, ParticleBuilder particleBuilder) {
        for (double y = 0; y <= 2; y += 0.1) {
            double x = center.getX() + Math.cos(y * Math.PI * 2 + animationStep);
            double z = center.getZ() + Math.sin(y * Math.PI * 2 + animationStep);
            particleBuilder.location(new Location(center.getWorld(), x, center.getY() + y, z)).spawn();
        }
    }

    private void displaySpiral(Location center, ParticleBuilder particleBuilder) {
        for (double y = 0; y <= 2; y += 0.1) {
            double radius = 1 - (y / 2);
            double x = center.getX() + Math.cos(y * Math.PI * 4 + animationStep) * radius;
            double z = center.getZ() + Math.sin(y * Math.PI * 4 + animationStep) * radius;
            particleBuilder.location(new Location(center.getWorld(), x, center.getY() + y, z)).spawn();
        }
    }

    private void displayFountain(Location center, ParticleBuilder particleBuilder) {
        for (int i = 0; i < 20; i++) {
            double angle = i * Math.PI * 2 / 20;
            double x = center.getX() + Math.cos(angle) * 0.5;
            double z = center.getZ() + Math.sin(angle) * 0.5;
            double y = center.getY() + 2 + Math.sin(animationStep + i * 0.5) * 0.5;
            particleBuilder.location(new Location(center.getWorld(), x, y, z)).spawn();
        }
    }

    private void displayWings(Location center, ParticleBuilder particleBuilder) {
        for (int side = -1; side <= 1; side += 2) {
            for (double y = 0; y <= 1; y += 0.1) {
                double x = center.getX() + side * Math.sin(y * Math.PI) * Math.cos(animationStep);
                double z = center.getZ() + Math.cos(y * Math.PI) * 0.3;
                particleBuilder.location(new Location(center.getWorld(), x, center.getY() + y + 0.5, z)).spawn();
            }
        }
    }
}