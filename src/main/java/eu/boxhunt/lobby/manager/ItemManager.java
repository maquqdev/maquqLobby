package eu.boxhunt.lobby.manager;

import eu.boxhunt.lobby.LobbyPlugin;
import eu.boxhunt.lobby.object.user.User;
import lombok.Getter;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static eu.boxhunt.lobby.utils.TextUtil.sendMessage;
import static eu.boxhunt.lobby.utils.TextUtil.sendTitle;

@Getter
public class ItemManager {


    private final LobbyPlugin lobbyPlugin;

    private final NamespacedKey functionallItemKey;

    public ItemManager(LobbyPlugin lobbyPlugin) {
        this.lobbyPlugin = lobbyPlugin;
        this.functionallItemKey = new NamespacedKey(lobbyPlugin, "funItem");
    }

    public void giveItems(Player player) {
        player.getInventory().clear();
        lobbyPlugin.getUserManager().get(player.getUniqueId()).ifPresent(user -> {
            val inventory = player.getInventory();
            lobbyPlugin.getPluginConfiguration().getItems().forEach((slot, funcItem) -> {
                inventory.setItem(slot, funcItem.getItem());
            });

            ItemStack chosenItem = user.getChosenItem();

            var slot = lobbyPlugin.getPluginConfiguration().getFunctionalItemSlot(chosenItem);
            if (slot == null) return;
            if(!chosenItem.equals(lobbyPlugin.getPluginConfiguration().getFunctionalItems().values().stream().findFirst().orElse(null).getItem()))
                slot -= 1;

            inventory.setItem(slot, chosenItem);

            healPlayer(player);
        });
    }

    public void changePlayerVisibility(User user) {
        val player = user.getPlayer();

        user.setVisiblePlayers(!user.isVisiblePlayers());
        if (user.isVisiblePlayers()) {
            Bukkit.getOnlinePlayers().forEach(players -> {
                player.showPlayer(lobbyPlugin, players);
            });

            sendTitle(player, " ", lobbyPlugin.getMessageConfiguration().getMisc().getEnabledVisibility());
        } else {
            Bukkit.getOnlinePlayers().forEach(players -> {
                player.hidePlayer(lobbyPlugin, players);
            });

            sendTitle(player, " ", lobbyPlugin.getMessageConfiguration().getMisc().getEnabledInvisibility());
        }
    }


    public void givePvpItems(User user) {
        val player = user.getPlayer();
        player.getInventory().clear();

        // TODO: Custom Player Layout
        val defaultKit = lobbyPlugin.getArenaConfiguration().getDefaultKit();
        if (defaultKit.getContents() == null || defaultKit.getOffHand() == null) {
            sendMessage(player, "&cPvP module is not configured yet");
            giveItems(player);
            return;
        }


        healPlayer(player);

        val inventory = player.getInventory();
        inventory.setContents(defaultKit.getContents());
        inventory.setItemInOffHand(defaultKit.getOffHand());
    }

    private void healPlayer(Player player) {
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.setFireTicks(0);
    }
}
