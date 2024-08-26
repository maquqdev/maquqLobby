package eu.boxhunt.lobby.manager;

import eu.boxhunt.lobby.object.region.Selector;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class SelectorManager {

    private final Map<UUID, Selector> selectorLocation = new HashMap<>();

    public void setPos1(Player player, Location pos1) {
        selectorLocation.compute(
                player.getUniqueId(),
                (ignored, selector) ->
                        new Selector(
                                pos1,
                                selector != null
                                        ? selector.getPos2() :
                                        null)
        );
    }

    public void setPos2(Player player, Location pos2) {
        selectorLocation.compute(
                player.getUniqueId(),
                (ignored, selector) ->
                        new Selector(
                                selector != null
                                        ? selector.getPos1() :
                                        null, pos2)
        );
    }

    public Selector getSelector(Player player) {
        return selectorLocation.get(player.getUniqueId());
    }
}
