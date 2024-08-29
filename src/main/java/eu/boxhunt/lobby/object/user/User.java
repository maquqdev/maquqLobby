package eu.boxhunt.lobby.object.user;

import eu.boxhunt.lobby.object.Cosmetic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class User {

    private UUID uuid;
    private ItemStack chosenItem;

    private Player player;

    private Cosmetic cosmetic = null;
    private boolean visiblePlayers = true;

    public User(UUID uuid) {
        this.uuid = uuid;
    }
}
