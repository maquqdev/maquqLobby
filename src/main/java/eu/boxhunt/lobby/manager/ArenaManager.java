package eu.boxhunt.lobby.manager;

import eu.boxhunt.lobby.LobbyPlugin;
import eu.boxhunt.lobby.object.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

@RequiredArgsConstructor
public class ArenaManager {

    private final LobbyPlugin lobbyPlugin;

    @Getter
    private final List<UUID> arenaPlayers = new ArrayList<>();

    public void joinToArena(User user, boolean teleport) {
        if(isInArena(user.getPlayer().getUniqueId()))
            return;

        val player = user.getPlayer();
        player.closeInventory();

        lobbyPlugin.getItemManager().givePvpItems(user);
        arenaPlayers.add(player.getUniqueId());

        if(teleport) player.teleportAsync(lobbyPlugin.getArenaConfiguration().getArenaSpawn());
    }

    public void leaveFromArena(Player player) {
        player.closeInventory();

        arenaPlayers.remove(player.getUniqueId());
        player.teleportAsync(lobbyPlugin.getPluginConfiguration().getLobby()).thenAccept(accept ->{
            if (!accept) return;

            getServer().getScheduler().runTaskLater(
                    LobbyPlugin.getInstance(),
                    () ->
                            lobbyPlugin.getItemManager().giveItems(player), 20L
            );
        });
    }

    public boolean isInArena(UUID uuid) {
        return arenaPlayers.contains(uuid);
    }
}
