package eu.boxhunt.lobby.listener;

import eu.boxhunt.lobby.LobbyPlugin;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static eu.boxhunt.lobby.utils.TextUtil.sendMessage;

@RequiredArgsConstructor
public class PlayerAsynChatListener implements Listener {

    private final LobbyPlugin lobbyPlugin;

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        val player = event.getPlayer();

        if (!player.isOp()) {
            event.setCancelled(true);
            return;
        }
        Bukkit.getOnlinePlayers().forEach(players -> {
            sendMessage(
                    players,
                    lobbyPlugin.getMessageConfiguration().getMessageFormat()
                            .replace("[PLAYER]", player.getName())
                            .replace("[MESSAGE]", event.getMessage())
            );
        });
        event.setCancelled(true);
    }
}
