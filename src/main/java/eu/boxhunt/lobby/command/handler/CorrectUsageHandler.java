package eu.boxhunt.lobby.command.handler;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.schematic.Schematic;
import org.bukkit.command.CommandSender;

import static eu.boxhunt.lobby.utils.TextUtil.sendMessage;

public class CorrectUsageHandler implements InvalidUsageHandler<CommandSender> {

    @Override
    public void handle(Invocation<CommandSender> invocation, InvalidUsage<CommandSender> commandSenderInvalidUsage, ResultHandlerChain<CommandSender> resultHandlerChain) {
        var sender = invocation.sender();
        Schematic schematic = commandSenderInvalidUsage.getSchematic();

        if (schematic.isOnlyFirst()) {
            sendMessage(sender, "&fPoprawne użycie komendy: &b" + schematic.first());
            return;
        }

        sendMessage(sender, "&fPoprawne użycie komendy:");
        for(var s : schematic.all()) sendMessage(sender, "&f- &#00b3ff" + s);
    }
}
