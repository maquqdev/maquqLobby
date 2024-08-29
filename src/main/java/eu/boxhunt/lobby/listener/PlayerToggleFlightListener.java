package eu.boxhunt.lobby.listener;

import eu.boxhunt.lobby.LobbyPlugin;
import lombok.AllArgsConstructor;
import lombok.val;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import static org.bukkit.Bukkit.getServer;

@AllArgsConstructor
public class PlayerToggleFlightListener implements Listener {

    private final LobbyPlugin lobbyPlugin;
    private final Map<UUID, Long> lastJumpTime = new HashMap<>();

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        val player = event.getPlayer();
        if(lobbyPlugin.getArenaConfiguration().getArenaRegion().isLocationInRegion(player.getLocation())) return;

        if (player.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
            if (canJump(player)) {
                performDoubleJump(player);
            }
        }
    }

    private boolean canJump(Player player) {
        long currentTime = System.currentTimeMillis();
        long lastJump = lastJumpTime.getOrDefault(player.getUniqueId(), 0L);
        return currentTime - lastJump >= 1000;
    }

    private void performDoubleJump(Player player) {
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(1));
        lastJumpTime.put(player.getUniqueId(), System.currentTimeMillis());

        lobbyPlugin.getServer().getScheduler().runTaskLater(
                lobbyPlugin,
                () -> {
                    if (player.isOnGround())
                        player.setAllowFlight(true);
                     else
                        checkGroundAndResetFlight(player);
                },
                20L
        );
    }

    private void checkGroundAndResetFlight(Player player) {
            lobbyPlugin.getServer().getScheduler().runTaskTimer(
                lobbyPlugin,
                () -> {
                    if (player.isOnGround())
                        player.setAllowFlight(true);

                },
                0L,
                1L
        );
    }
}