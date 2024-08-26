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
        else if(state == PlayerFishEvent.State.REEL_IN) {
            val location = fishLocation.get(player.getUniqueId());
            if(location == null) return;

            player.setVelocity(location.getDirection().multiply(4));
//            val pushVector = location.toVector().subtract(player.getLocation().toVector()).normalize().multiply(3);
//            player.setVelocity(pushVector);

            fishLocation.remove(player.getUniqueId());
        }
    }


}
