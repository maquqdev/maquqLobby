package eu.boxhunt.lobby.listener;

import lombok.val;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerFishListener implements Listener {

    private final ConcurrentHashMap<UUID, Location> fishLocation = new ConcurrentHashMap<>();

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerFish(PlayerFishEvent event) {
        val player = event.getPlayer();

        val state = event.getState();
        if(state == PlayerFishEvent.State.FISHING) fishLocation.put(player.getUniqueId(), event.getHook().getLocation());
        else if(state == PlayerFishEvent.State.REEL_IN || state == PlayerFishEvent.State.IN_GROUND) {
            val location = fishLocation.get(player.getUniqueId());
            if(location == null) return;

            val clone = location.clone();
            val playerLocation = player.getLocation();
            clone.setPitch(playerLocation.getPitch());
            clone.setYaw(playerLocation.getYaw());
            player.setVelocity(clone.getDirection().multiply(4));

            fishLocation.remove(player.getUniqueId());
        }
    }


}
