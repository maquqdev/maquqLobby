package eu.boxhunt.lobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInventory(InventoryClickEvent event) {
        if(event.getWhoClicked().hasPermission("core.admin")) return;
        if (event.getWhoClicked().getInventory() == event.getClickedInventory()) event.setCancelled(true);
    }
}
