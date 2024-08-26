package eu.boxhunt.lobby.object.region;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

@AllArgsConstructor
@Getter
@Setter
public class Selector {

    private Location pos1;
    private Location pos2;
}
