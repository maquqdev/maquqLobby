package eu.boxhunt.lobby.command.handler;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.permission.MissingPermissions;
import dev.rollczi.litecommands.permission.MissingPermissionsHandler;
import org.bukkit.command.CommandSender;

import static eu.boxhunt.lobby.utils.TextUtil.sendMessage;

public class PermissionHandler implements MissingPermissionsHandler<CommandSender> {

    @Override
    public void handle(
            Invocation<CommandSender> invocation,
            MissingPermissions missingPermissions,
            ResultHandlerChain<CommandSender> resultHandlerChain
    ) {
        CommandSender sender = invocation.sender();
        sendMessage(sender, "&cNie posiadasz permisji (&4" + String.join(", ", missingPermissions.getPermissions()) + "&c)");
    }
}
