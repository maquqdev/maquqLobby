package eu.boxhunt.lobby.storage.config;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;

import java.util.List;

@Getter
public class MessageConfiguration extends OkaeriConfig {

    private String noPermission = "&cYou do not have permission to do this.";

    private List<String> welcomeActions = List.of(
            "[TITLE]:&bBOXHUNT.PL;&fWelcome on server!",
            "[FIREROCKET]:RED;10",
            "[SOUND]:ENTITY_PLAYER_LEVELUP"
    );

    private String chosenItem = "&aYou have chosen your custom item.";
    private String messageFormat = "&cADMIN &f[PLAYER] &8-> &f[MESSAGE]";

    private Gamemode gamemode = new Gamemode();
    private Lobby lobby = new Lobby();
    private Configure configure = new Configure();
    private Misc misc = new Misc();

    @Getter
    public static class Gamemode extends OkaeriConfig {
        private String invalidGamemode = "&cYou have entered an invalid game mode.";
        private String changedGamemode = "&fYou have changed your game mode to &b[GAMEMODE].";
        private String changedGamemodeOther = "&fYou changed the game mode of the player &b[PLAYER] &fto &b[GAMEMODE].";
    }

    @Getter
    public static class Lobby extends OkaeriConfig {
        private String teleported = "&aSuccessfully teleported to lobby.";
        private String teleportedOther = "&aTeleported [PLAYER] to lobby.";
    }

    @Getter
    public static class Configure extends OkaeriConfig {
        private String setLobbyLocation = "&aSuccessfully set the lobby location.";
        private String reloadedItems = "&aReloaded all player items.";
        private String setPvpItems = "&aSuccessfully set the pvp items.";
        private String missingPos = "&cYou didn't mark both items.";
        private String setPvpRegion = "&aSuccessfully set the pvp region.";
        private String setPvpSpawn = "&aSuccessfully set the pvp spawn.";
    }

    @Getter
    public static class Misc extends OkaeriConfig {
        private String enabledVisibility = "&aShowed players!";
        private String enabledInvisibility = "&aHide players!";

        private String notInArena = "&cYou're not in arena!";
        private String leftFromArena = "&cLeft froma pvp pit!";

        private String setCosmetic = "&aYou have set your new cosmetic correctly!";
        private String clearCosmetic = "&cCleared cosmetics!";
        private String dontHaveCosmetic = "&cYou don't have any cosmetic chosen!";
    }
}
