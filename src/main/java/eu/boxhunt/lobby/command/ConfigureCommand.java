package eu.boxhunt.lobby.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import eu.boxhunt.lobby.LobbyPlugin;
import eu.boxhunt.lobby.object.Kit;
import eu.boxhunt.lobby.object.region.Region;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static eu.boxhunt.lobby.utils.TextUtil.sendMessage;

@RequiredArgsConstructor
@Command(name = "configure")
@Permission("core.command.configure")
public class ConfigureCommand {

    private final LobbyPlugin lobbyPlugin;

    @Execute(name = "reloadItems")
    public void executeReloadItems(@Context Player player) {
        val itemManager = lobbyPlugin.getItemManager();
        Bukkit.getOnlinePlayers().forEach(players -> {
            itemManager.giveItems(player);
        });

        sendMessage(player, lobbyPlugin.getMessageConfiguration().getConfigure().getReloadedItems());
    }



    @Execute(name = "setLobby")
    public void execute(@Context Player player) {
        lobbyPlugin.getPluginConfiguration().setLobby(player.getLocation());
        lobbyPlugin.getPluginConfiguration().save();

        sendMessage(player, lobbyPlugin.getMessageConfiguration().getConfigure().getSetLobbyLocation());
    }

    @Execute(name = "setPvpItems")
    public void executeSetPvpItems(@Context Player player) {
        val inventory = player.getInventory();
        lobbyPlugin.getArenaConfiguration().setDefaultKit(new Kit(inventory.getContents(), inventory.getItemInOffHand()));
        lobbyPlugin.getArenaConfiguration().save();

        sendMessage(player, lobbyPlugin.getMessageConfiguration().getConfigure().getSetPvpItems());
    }

    @Execute(name = "setArenaRegion")
    public void executeSetArenaRegion(@Context Player player) {
        val selector = lobbyPlugin.getSelectorManager().getSelector(player);
        if(selector.getPos1() == null || selector.getPos2() == null) {
            sendMessage(player, lobbyPlugin.getMessageConfiguration().getConfigure().getMissingPos());
            return;
        }

        lobbyPlugin.getArenaConfiguration().setArenaRegion(new Region(selector.getPos1(), selector.getPos2()));
        lobbyPlugin.getArenaConfiguration().save();
        sendMessage(player, lobbyPlugin.getMessageConfiguration().getConfigure().getSetPvpRegion());
    }

    @Execute(name = "setPvpSpawn")
    public void executeSetPvpSpawn(@Context Player player) {
        lobbyPlugin.getArenaConfiguration().setArenaSpawn(player.getLocation());
        lobbyPlugin.getArenaConfiguration().save();
        sendMessage(player, lobbyPlugin.getMessageConfiguration().getConfigure().getSetPvpSpawn());
    }

    @Execute(name = "getSelector")
    public void executeGetSelector(@Context Player player) {
        player.getInventory().addItem(lobbyPlugin.getPluginConfiguration().getSelectorItem());
    }
}
