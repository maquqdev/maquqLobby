package eu.boxhunt.lobby.gui;

import eu.boxhunt.lobby.LobbyPlugin;
import eu.boxhunt.lobby.libs.ClickableItem;
import eu.boxhunt.lobby.libs.SmartInventory;
import eu.boxhunt.lobby.libs.content.InventoryContents;
import eu.boxhunt.lobby.libs.content.InventoryProvider;
import eu.boxhunt.lobby.object.Cosmetic;
import eu.boxhunt.lobby.object.CosmeticType;
import eu.boxhunt.lobby.utils.Util;
import lombok.AllArgsConstructor;
import lombok.val;
import org.bukkit.entity.Player;

import static eu.boxhunt.lobby.utils.TextUtil.fixColor;
import static eu.boxhunt.lobby.utils.TextUtil.sendMessage;

@AllArgsConstructor
public class CosmeticChangeGui implements InventoryProvider {

    private final LobbyPlugin lobbyPlugin;

    @Override
    public void init(Player player, InventoryContents contents) {
        lobbyPlugin.getUserManager().get(player.getUniqueId()).ifPresent(user -> {
            for (CosmeticType value : CosmeticType.values()) {
                contents.add(ClickableItem.of(lobbyPlugin.getPluginConfiguration().getCosmetics().get(value), false, event -> {
                    user.setCosmetic(new Cosmetic(value));

                    sendMessage(player, lobbyPlugin.getMessageConfiguration().getMisc().getSetCosmetic());
                }));
            }

            val clearCosmetic = lobbyPlugin.getGuiConfiguration().getCosmeticClear().entrySet().stream().findFirst().orElse(null);
            if (clearCosmetic == null) return;
            contents.set(Util.of(clearCosmetic.getKey()), ClickableItem.of(clearCosmetic.getValue(), false, event -> {
                user.setCosmetic(null);
                sendMessage(player, lobbyPlugin.getMessageConfiguration().getMisc().getClearCosmetic());
            }));
        });
    }

    public void open(Player player) {
        SmartInventory.builder()
                .title(fixColor(lobbyPlugin.getGuiConfiguration().getCosmeticChooseTitle()))
                .size(lobbyPlugin.getGuiConfiguration().getCosmeticChangeRows(), 9)
                .provider(this)
                .build()
                .open(player);
    }
}
