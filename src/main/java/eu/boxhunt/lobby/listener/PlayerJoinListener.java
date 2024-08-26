package eu.boxhunt.lobby.listener;

import eu.boxhunt.lobby.LobbyPlugin;
import eu.boxhunt.lobby.object.user.User;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

import static eu.boxhunt.lobby.utils.Util.*;

@RequiredArgsConstructor
public class PlayerJoinListener implements Listener {

    private final LobbyPlugin lobbyPlugin;

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = lobbyPlugin.getUserManager().compute(player.getUniqueId()).join();
        user.setPlayer(player);
        user.setChosenItem(lobbyPlugin.getPluginConfiguration().getFunctionalItems().values().stream().findFirst().orElse(null).getItem());


        lobbyPlugin.getItemManager().giveItems(player);

        lobbyPlugin.getUserManager().getUserMap()
                .values()
                .stream()
                .filter(userMap -> !userMap.isVisiblePlayers())
                .forEach(userMap -> {
                    userMap.getPlayer().hidePlayer(lobbyPlugin, player);
                });

        player.teleportAsync(lobbyPlugin.getPluginConfiguration().getLobby()).thenAccept(accepted -> {
            if (!accepted) return;

            processWelcomeActions(player, lobbyPlugin.getMessageConfiguration().getWelcomeActions());
        });
        event.setJoinMessage(null);
    }

    public void processWelcomeActions(Player player, List<String> welcomeActions) {
        welcomeActions.forEach(action -> {
            String[] parts = action.split(":", 2);
            String actionType = parts[0];
            String actionValue = parts[1];

            switch (actionType) {
                case "[TITLE]" -> processTitleAction(player, actionValue);
                case "[FIREROCKET]" -> processFireworkAction(player, actionValue);
                case "[SOUND]" -> processSoundAction(player, actionValue);
            }
        });
    }


}
