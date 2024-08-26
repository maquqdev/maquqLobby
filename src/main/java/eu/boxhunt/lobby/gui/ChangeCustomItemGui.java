package eu.boxhunt.lobby.gui;

import eu.boxhunt.lobby.LobbyPlugin;
import eu.boxhunt.lobby.libs.ClickableItem;
import eu.boxhunt.lobby.libs.SmartInventory;
import eu.boxhunt.lobby.libs.content.InventoryContents;
import eu.boxhunt.lobby.libs.content.InventoryProvider;
import eu.boxhunt.lobby.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bukkit.entity.Player;

import static eu.boxhunt.lobby.utils.TextUtil.fixColor;
import static eu.boxhunt.lobby.utils.TextUtil.sendMessage;
import static eu.boxhunt.lobby.utils.Util.getSlots;

@RequiredArgsConstructor
public class ChangeCustomItemGui implements InventoryProvider {

    private final LobbyPlugin lobbyPlugin;

    @Override
    public void init(Player player, InventoryContents contents) {
        lobbyPlugin.getGuiConfiguration().getCustomItemFilters().forEach((slot, item) -> {
            getSlots(slot).forEach(slots -> {
                contents.set(Util.of(slots), ClickableItem.empty(item, false));
            });
        });

        lobbyPlugin.getUserManager().get(player.getUniqueId()).ifPresent(user -> {
            lobbyPlugin.getPluginConfiguration().getFunctionalItems().forEach((slot, itemAction) -> {
                if(user.getChosenItem().equals(itemAction.getItem())) return;

                contents.add(ClickableItem.of(itemAction.getItem(), false, event -> {
                    user.setChosenItem(itemAction.getItem());
                    sendMessage(player, lobbyPlugin.getMessageConfiguration().getChosenItem());
                    lobbyPlugin.getItemManager().giveItems(player);
                    player.closeInventory();
                }));
            });
        });
    }

    public void open(Player player) {
        SmartInventory.builder()
                .title(fixColor(lobbyPlugin.getGuiConfiguration().getCustomItemTitle()))
                .size(lobbyPlugin.getGuiConfiguration().getCustomItemRows(), 9)
                .provider(this)
                .build()
                .open(player);
    }
}
