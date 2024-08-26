package eu.boxhunt.lobby.storage.config.other;

import eu.boxhunt.lobby.object.CommandItem;
import eu.boxhunt.lobby.utils.ItemUtil;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@Getter
public class GuiConfiguration extends OkaeriConfig {

    @Comment("Server selector")
    private String title = "&bSelect server";
    private int rows = 5;

    @Comment("Availble: 45-53, and 43;44;45 etc.")
    private Map<String, ItemStack> filters = new HashMap<>() {{{
        put(
                "0-8",
                new ItemUtil(Material.GRAY_STAINED_GLASS_PANE)
                        .build()
        );

        put(
                "36-44",
                new ItemUtil(Material.GRAY_STAINED_GLASS_PANE)
                        .build()
        );

        put(
                "9;18;27;17;26;35",
                new ItemUtil(Material.GRAY_STAINED_GLASS_PANE)
                        .build()
        );
    }}};

    private Map<Integer, CommandItem> servers = new HashMap<>() {{{
        put(
                19,
                new CommandItem(
                        new ItemUtil(Material.ELYTRA)
                                .setName("&b&lElytra Box")
                                .addLore(" ",
                                        "&8● &7Wersja&8: &f1.16.4 - 1.21 &7(Zalecana 1.19.4)",
                                        "&8● &fOnline&8: &f%math_0_{bungee_Elytra1}+{bungee_Elytra2}+{bungee_Elytra3}+{bungee_ElytraPlots1}%"
                                )
                                .build(),
                        "move [PLAYER] Elytra"
                )
        );
        put(
                21,
                new CommandItem(
                        new ItemUtil(Material.CROSSBOW)
                                .setName("&6&lSkyPvP Practice")
                                .addLore(" ",
                                        "&8● &7Wersja&8: &f1.16.4 - 1.21 &7(Zalecana 1.19.4)",
                                        "&8● &fOnline&8: &f%bungee_SkyPrac%"
                                )
                                .build(),
                        "move [PLAYER] SkyPrac"
                )
        );
        put(
                23,
                new CommandItem(
                        new ItemUtil(Material.GOLDEN_SWORD)
                                .setName("&e&lDuels")
                                .addLore(" ",
                                        "&8● &7Wersja&8: &f1.16.4 - 1.21 &7(Zalecana 1.19.4)",
                                        "&8● &fOnline&8: &f%bungee_Duels%"
                                )
                                .build(),
                        "move [PLAYER] Duels"
                )
        );
        put(
                25,
                new CommandItem(
                        new ItemUtil(Material.NETHERITE_SWORD)
                                .setName("&c&lAnarchia Practice")
                                .addLore(" ",
                                        "&8● &7Wersja&8: &f1.16.4 - 1.21 &7(Zalecana 1.19.4)",
                                        "&8● &fOnline&8: &f%bungee_AnarchiaPrac%"
                                )
                                .build(),
                        "move [PLAYER] AnarchiaPrac"
                )
        );
    }}};


    @Comment("Custom item change")
    private String customItemTitle = "&bChange custom item";
    private int customItemRows = 1;

    @Comment("Availble: 45-53, and 43;44;45 etc.")
    private Map<String, ItemStack> customItemFilters = new HashMap<>() {{{
        put(
                "8",
                new ItemUtil(Material.OAK_SIGN)
                        .setName("&cWhat is this?")
                        .addLore(" ", "&fIn this gui you can change your custom item.", " ")
                        .build()
        );
    }}};
 }
