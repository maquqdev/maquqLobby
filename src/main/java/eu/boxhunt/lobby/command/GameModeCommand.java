package eu.boxhunt.lobby.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import eu.boxhunt.lobby.LobbyPlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Map;

import static eu.boxhunt.lobby.utils.TextUtil.sendMessage;

@Command(name = "gamemode", aliases = {"gm"})
@Permission("core.command.gamemode")
@RequiredArgsConstructor
public class GameModeCommand {

    private final LobbyPlugin lobbyPlugin;
    @Execute
    public void execute(@Context Player player, @Arg GameMode gameMode) {
        player.setGameMode(gameMode);
        sendMessage(
                player,
                lobbyPlugin.getMessageConfiguration().getGamemode().getChangedGamemode()
                        .replace("[GAMEMODE]", gameMode.toString().toLowerCase())
        );
    }

    @Execute
    public void execute(@Context Player player, @Arg("gamemode") GameMode gameMode, @Arg("target") Player target) {
        target.setGameMode(gameMode);
        sendMessage(
                player,
                lobbyPlugin.getMessageConfiguration().getGamemode().getChangedGamemode()
                        .replace("[GAMEMODE]", gameMode.toString().toLowerCase())
                        .replace("[PLAYER]", target.getName())
        );
    }
}