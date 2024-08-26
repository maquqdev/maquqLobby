package eu.boxhunt.lobby.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

    public static String fixColor(String message) {
        Pattern pattern = Pattern.compile("&(#[a-fA-F0-9]{6})");
        for (Matcher matcher = pattern.matcher(message); matcher.find(); matcher = pattern.matcher(message)) {
            String color = message.substring(matcher.start() + 1, matcher.end());
            message = message.replace("&" + color, String.valueOf(ChatColor.of(color)));
        }
        return ChatColor.translateAlternateColorCodes('&', message)
                .replace(">>", "»")
                .replace("<<", "«")
                .replace("->", "→")
                .replace("<-", "←")
                .replace("**", "•");
    }

    public static void sendTitle(Player player, String title, String subtitle) {
        player.sendTitle(fixColor(title), fixColor(subtitle), 10, 40, 10);
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage(fixColor(message));
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(fixColor(message));
    }
}
