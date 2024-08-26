package eu.boxhunt.lobby.listener;

import eu.boxhunt.lobby.LobbyPlugin;
import eu.boxhunt.lobby.gui.ChangeCustomItemGui;
import eu.boxhunt.lobby.gui.ServerSelectorGui;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

@RequiredArgsConstructor
public class PlayerInteractListener implements Listener {

    private final LobbyPlugin lobbyPlugin;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;

        val item = event.getItem();
        if(item == null) return;
        if (!lobbyPlugin.getPluginConfiguration().isFunctionalItem(item)) return;

        val itemAction = lobbyPlugin.getPluginConfiguration().getAction(item);
        val player = event.getPlayer();

        switch (itemAction) {
            case "pushPlayer" -> {
                if (event.getAction() != Action.RIGHT_CLICK_AIR) return;
                player.setVelocity(player.getLocation().getDirection().multiply(4));
            }

            case "serverSelector" -> {
                new ServerSelectorGui(lobbyPlugin.getGuiConfiguration()).open(player);
            }

            case "changeVisibility" -> {
                lobbyPlugin.getUserManager().get(player.getUniqueId())
                        .ifPresent(user ->
                                lobbyPlugin.getItemManager().changePlayerVisibility(user)
                        );
            }

            case "changeCustomItem" -> {
                new ChangeCustomItemGui(lobbyPlugin)
                        .open(player);
            }

            case "pvp" -> {
                lobbyPlugin.getUserManager().get(player.getUniqueId())
                        .ifPresent(user ->
                                lobbyPlugin.getArenaManager().joinToArena(user, true)
                        );
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void handlePlayerInteract(PlayerInteractEvent event) {
        val player = event.getPlayer();
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;
        if (!player.getInventory().getItemInMainHand().isSimilar(lobbyPlugin.getPluginConfiguration().getSelectorItem())) return;

        val block = event.getClickedBlock();
        if (block == null) return;

        val action = event.getAction();

        if (action == Action.LEFT_CLICK_BLOCK)
            lobbyPlugin.getSelectorManager().setPos1(player, block.getLocation());
        else if (action == Action.RIGHT_CLICK_BLOCK)
            lobbyPlugin.getSelectorManager().setPos2(player, block.getLocation());

        event.setCancelled(true);
    }
}
