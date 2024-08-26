package eu.boxhunt.lobby.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import eu.boxhunt.lobby.LobbyPlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import static eu.boxhunt.lobby.utils.TextUtil.sendMessage;

@RequiredArgsConstructor
@Command(name = "lobby", aliases = "spawn")
public class LobbyCommand {

    private final LobbyPlugin lobbyPlugin;

    @Execute
    public void execute(@Context Player player) {
        player.teleportAsync(lobbyPlugin.getPluginConfiguration().getLobby()).thenAccept(accept -> {
            if(!accept) return;

            sendMessage(player, lobbyPlugin.getMessageConfiguration().getLobby().getTeleported());
        });
    }

    @Execute
    public void execute(@Context Player player, @Arg("target") Player target) {
        target.teleportAsync(lobbyPlugin.getPluginConfiguration().getLobby()).thenAccept(accept -> {
            if(!accept) return;

            sendMessage(player, lobbyPlugin.getMessageConfiguration().getLobby().getTeleported());
            sendMessage(
                    target,
                    lobbyPlugin.getMessageConfiguration().getLobby().getTeleportedOther()
                            .replace("[PLAYER]", target.getName())
            );
        });
    }
}
