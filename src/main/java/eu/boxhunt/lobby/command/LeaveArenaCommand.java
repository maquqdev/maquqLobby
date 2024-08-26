package eu.boxhunt.lobby.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import eu.boxhunt.lobby.LobbyPlugin;
import lombok.AllArgsConstructor;
import lombok.val;
import org.bukkit.entity.Player;

import static eu.boxhunt.lobby.utils.TextUtil.sendMessage;

@AllArgsConstructor
@Command(name = "leavearena")
public class LeaveArenaCommand {

    private final LobbyPlugin lobbyPlugin;

    @Execute
    public void executeLeaveArena(@Context Player player) {
        if (!lobbyPlugin.getArenaManager().isInArena(player.getUniqueId())) {
            sendMessage(player, lobbyPlugin.getMessageConfiguration().getMisc().getNotInArena());
            return;
        }

        sendMessage(player, lobbyPlugin.getMessageConfiguration().getMisc().getLeftFromArena());
        lobbyPlugin.getArenaManager().leaveFromArena(player);
    }
}
