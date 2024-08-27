package eu.boxhunt.lobby.listener;

import eu.boxhunt.lobby.LobbyPlugin;
import lombok.AllArgsConstructor;
import lombok.val;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@AllArgsConstructor
public class EntityDamageListener implements Listener {

    private final LobbyPlugin lobbyPlugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDamage(EntityDamageEvent event) {
        val entity = event.getEntity();
        if(!(entity instanceof Player player)) return;

        val arenaRegion = lobbyPlugin.getArenaConfiguration().getArenaRegion();
        if(arenaRegion.isLocationInRegion(player.getLocation())) return;


        if(event.getCause() == EntityDamageEvent.DamageCause.FALL) return;

        event.setCancelled(true);
    }
}
