package eu.boxhunt.lobby.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class ItemAction implements Serializable {

    private ItemStack item;
    private String action;
}
