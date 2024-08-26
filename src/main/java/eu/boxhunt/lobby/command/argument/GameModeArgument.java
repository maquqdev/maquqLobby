package eu.boxhunt.lobby.command.argument;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import eu.boxhunt.lobby.LobbyPlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

import static eu.boxhunt.lobby.utils.TextUtil.sendMessage;

@RequiredArgsConstructor
@SuppressWarnings("deprecation")
public class GameModeArgument extends ArgumentResolver<CommandSender, GameMode> {

    private final LobbyPlugin lobbyPlugin;
    private static final Map<String, GameMode> GAME_MODE_ARGUMENTS = new HashMap<>();

    static {
        for (GameMode value : GameMode.values()) {
            GAME_MODE_ARGUMENTS.put(value.name().toLowerCase(), value);
            GAME_MODE_ARGUMENTS.put(String.valueOf(value.getValue()), value);
        }
    }

    @Override
    protected ParseResult<GameMode> parse(Invocation<CommandSender> invocation, Argument<GameMode> context, String argument) {
        CommandSender sender = invocation.sender();
        GameMode gameMode = GAME_MODE_ARGUMENTS.get(argument.toLowerCase());

        if (gameMode == null) {
            sendMessage(invocation.sender(), lobbyPlugin.getMessageConfiguration().getGamemode().getInvalidGamemode());
            return ParseResult.failure(null);
        }

        if (!sender.hasPermission("core.command.gamemode." + gameMode.name().toLowerCase())) {
            sendMessage(invocation.sender(), lobbyPlugin.getMessageConfiguration().getNoPermission());
            return ParseResult.failure(null);
        }

        return ParseResult.success(GameMode.valueOf(argument.toUpperCase()));
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<GameMode> argument, SuggestionContext context) {
        return SuggestionResult.of(GAME_MODE_ARGUMENTS.keySet());
    }
}