package eu.boxhunt.lobby.object.user;

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
    private boolean visiblePlayers = true;

    public User(UUID uuid) {
        this.uuid = uuid;
    }
}
