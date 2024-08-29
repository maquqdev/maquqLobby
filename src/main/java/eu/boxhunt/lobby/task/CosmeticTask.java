package eu.boxhunt.lobby.task;

import eu.boxhunt.lobby.manager.user.UserManager;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
public class CosmeticTask implements Runnable {

    private final UserManager userManager;

    @Override
    public void run() {
        userManager.getUserMap().values()
                .stream()
                .filter(user -> user.getCosmetic() != null)
                .forEach(user -> {
                    val player = user.getPlayer();
                    if(player.isOnline()) user.getCosmetic().display(user.getPlayer());
                });
    }
}
