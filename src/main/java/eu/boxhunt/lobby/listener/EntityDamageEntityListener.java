package eu.boxhunt.lobby.listener;

import eu.boxhunt.lobby.LobbyPlugin;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

//no1. bukkit hater
@RequiredArgsConstructor
public class EntityDamageEntityListener implements Listener {

    private final LobbyPlugin lobbyPlugin;

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;
        if(!(event.getEntity() instanceof Player target)) return;

        val arenaRegion = lobbyPlugin.getArenaConfiguration().getArenaRegion();
        if(arenaRegion.isLocationInRegion(player.getLocation()) && arenaRegion.isLocationInRegion(target.getLocation())) return;

        event.setCancelled(true);
    }
}
