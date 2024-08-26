package eu.boxhunt.lobby.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class Kit implements Serializable {

    private ItemStack[] contents;
    private ItemStack offHand;
}
