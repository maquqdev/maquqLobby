package eu.boxhunt.lobby.storage.config.other;

import eu.boxhunt.lobby.object.Kit;
import eu.boxhunt.lobby.object.region.Region;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;

@Getter
public class ArenaConfiguration extends OkaeriConfig {

    private boolean showParticles = true;
    private Particle particle = Particle.FLAME;

    @Setter
    private Region arenaRegion = new Region(
            new Location(Bukkit.getWorlds().get(0), 100, 40, 0),
            new Location(Bukkit.getWorlds().get(0), 0, 40, 0)
    );

    @Setter
    private Location arenaSpawn = new Location(Bukkit.getWorlds().get(0), 50, 41, 50);

    @Setter
    private Kit defaultKit = new Kit(null, null);
}
