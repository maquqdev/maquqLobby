package eu.boxhunt.lobby.object;

import lombok.Getter;
import org.bukkit.Particle;

@Getter
public enum CosmeticType {
    CIRCLE(Particle.REDSTONE),
    HELIX(Particle.END_ROD),
    SPIRAL(Particle.FALLING_LAVA),
    FOUNTAIN(Particle.WATER_DROP),
    WINGS(Particle.DRAGON_BREATH);

    private final Particle particle;

    CosmeticType(Particle particle) {
        this.particle = particle;
    }

}