package eu.boxhunt.lobby.listener;

import eu.boxhunt.lobby.LobbyPlugin;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

@RequiredArgsConstructor
public class PlayerMoveListener implements Listener {

    private final LobbyPlugin lobbyPlugin;

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent e) {
        val player = e.getPlayer();
        //prevent falling to void xd
        if (player.getLocation().getY() <= 0)
            player.teleportAsync(lobbyPlugin.getPluginConfiguration().getLobby());

        //TODO better method to figure it out
        if (!lobbyPlugin.getArenaConfiguration().getArenaRegion().isLocationInRegion(player.getLocation())) {
//            if (!lobbyPlugin.getArenaManager().isInArena(player.getUniqueId())) return;

//            lobbyPlugin.getArenaManager().leaveFromArena(player);
            return;
        }

        lobbyPlugin.getUserManager().get(player.getUniqueId()).ifPresent(user ->
                lobbyPlugin.getArenaManager().joinToArena(user, false)
        );
    }
}
