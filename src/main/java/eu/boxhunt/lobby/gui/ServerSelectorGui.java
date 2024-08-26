package eu.boxhunt.lobby.gui;

import eu.boxhunt.lobby.libs.ClickableItem;
import eu.boxhunt.lobby.libs.SmartInventory;
import eu.boxhunt.lobby.libs.content.InventoryContents;
import eu.boxhunt.lobby.libs.content.InventoryProvider;
import eu.boxhunt.lobby.storage.config.other.GuiConfiguration;
import eu.boxhunt.lobby.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.val;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static eu.boxhunt.lobby.utils.TextUtil.fixColor;
import static eu.boxhunt.lobby.utils.Util.getSlots;

@RequiredArgsConstructor
public class ServerSelectorGui implements InventoryProvider {

    private final GuiConfiguration guiConfiguration;

    @Override
    public void init(Player player, InventoryContents contents) {
        guiConfiguration.getFilters().forEach((slots, item) -> {
            getSlots(slots).forEach(slot -> {
                contents.set(Util.of(slot), ClickableItem.empty(item, false));
            });
        });

        guiConfiguration.getServers().forEach((slot, commandItem) -> {
            val item = commandItem.getItem().clone();
            var lore = item.getLore();
            if (lore != null) lore = PlaceholderAPI.setPlaceholders(player, lore);
            item.setLore(lore);

            contents.set(Util.of(slot), ClickableItem.of(item, false, event -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandItem.getCommand().replace("[PLAYER]", player.getName()));
            }));
        });
    }

    public void open(Player player) {
        SmartInventory.builder()
                .title(fixColor(guiConfiguration.getTitle()))
                .size(guiConfiguration.getRows(), 9)
                .provider(this)
                .build()
                .open(player);
    }
}
