package eu.boxhunt.lobby.utils;

import eu.boxhunt.lobby.libs.content.SlotPos;
import lombok.val;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static eu.boxhunt.lobby.utils.TextUtil.sendTitle;


public class Util {

    public static SlotPos of(int slot) {
        val row = slot / 9;
        val column = slot - row * 9;
        return new SlotPos(row, column);
    }

    public static void processTitleAction(Player player, String actionValue) {
        val titleParts = actionValue.split(";", 2);
        val subtitle = titleParts.length > 1 ? titleParts[1] : "";
        sendTitle(player, titleParts[0], subtitle);
    }

    public static void processFireworkAction(Player player, String actionValue) {
        val fireworkParts = actionValue.split(";", 2);
        val color = parseColor(fireworkParts[0]);
        int power = Integer.parseInt(fireworkParts[1]);
        launchFirework(player, color, power);
    }

    public static Color parseColor(String colorName) {
        return switch (colorName.toUpperCase()) {
            case "RED" -> Color.RED;
            case "GREEN" -> Color.GREEN;
            case "BLUE" -> Color.BLUE;
            case "YELLOW" -> Color.YELLOW;
            default -> Color.WHITE; //default color, fuck bukkit - I don't know how to do this shit
        };
    }

    public static void processSoundAction(Player player, String actionValue) {
        Sound sound = Sound.valueOf(actionValue);
        player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
    }

    public static void launchFirework(Player player, Color color, int power) {
        val location = player.getLocation();
        val effect = FireworkEffect.builder()
                .withColor(color)
                .with(FireworkEffect.Type.BALL)
                .build();

        val firework = location.getWorld().spawn(location, Firework.class);
        val meta = firework.getFireworkMeta();
        meta.addEffect(effect);
        meta.setPower(power);
        firework.setFireworkMeta(meta);
    }


    public static List<Integer> getSlots(String slots) {
        val list = new ArrayList<Integer>();
        if(slots.contains("-")) {
            val split = slots.split("-");
            val start = Integer.parseInt(split[0]);
            val end = Integer.parseInt(split[1]);

            val range = end - start;
            if(range <= 0) return list;

            for (int i = 0; i < range; i++)
                list.add(start + i);

            return list;
        }
        if(slots.contains(";")) {
            val split = slots.split(";");
            for (String s : split)
                list.add(Integer.parseInt(s));

            return list;
        }

        return new ArrayList<>();
    }
}
