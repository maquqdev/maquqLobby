package eu.boxhunt.lobby.storage.config;

import eu.boxhunt.lobby.object.CosmeticType;
import eu.boxhunt.lobby.object.ItemAction;
import eu.boxhunt.lobby.utils.ItemUtil;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class PluginConfiguration extends OkaeriConfig {

    private Location lobby = new Location(Bukkit.getWorlds().get(0), 0, 65.5, 0);
    private ItemStack selectorItem = new ItemUtil(Material.BLAZE_ROD)
            .setName("&cRegion Selector")
            .build();


    @Comment("Available actions: serverSelector, pvp, hidePlayers, showPlayers")
    @Comment("key = slot")
    private Map<Integer, ItemAction> items = new ConcurrentHashMap<>() {{
        {
            put(
                    1,
                    new ItemAction(
                            new ItemUtil(Material.NETHER_STAR)
                                    .setName("&bServer selector")
                                    .build(),
                            "serverSelector"
                    )
            );

            put(
                    9,
                    new ItemAction(
                            new ItemUtil(Material.YELLOW_DYE)
                                    .setName("&aChange visibility")
                                    .build(),
                            "changeVisibility"
                    )
            );

            put(
                    5,
                    new ItemAction(
                            new ItemUtil(Material.DIAMOND_SWORD)
                                    .setName("&aEnable PVP")
                                    .build(),
                            "pvp"
                    )
            );

            put(6,
                    new ItemAction(
                            new ItemUtil(Material.COMPASS)
                                    .setName("&fChange custom item")
                                    .build(),
                            "changeCustomItem"
                    )

            );

            put(5,
                    new ItemAction(
                            new ItemUtil(Material.CAULDRON)
                                    .setName("&6Change cosmetic menu")
                                    .build(),
                            "cosmeticMenu"
                    )

            );
        }
    }};

    @Comment("Available actions: pushPlayer, hookPlayer")
    @Comment("First option is always default.")
    @Comment("key = slot")
    private Map<Integer, ItemAction> functionalItems = new ConcurrentHashMap<>() {{
        {
            put(
                    5,
                    new ItemAction(
                            new ItemUtil(Material.PRISMARINE_SHARD)
                                    .setName("&bMagic rod")
                                    .addLore("&fYou can change this item!")
                                    .setGlowing(true)
                                    .build(),
                            "pushPlayer"
                    )
            );

            put(
                    8,
                    new ItemAction(
                            new ItemUtil(Material.FISHING_ROD)
                                    .setName("&6Grappler")
                                    .addLore("&fYou can change this item!")
                                    .setGlowing(true)
                                    .setUnbreakable()
                                    .build(),
                            "hookPlayer"
                    )
            );
        }
    }};


    public Map<CosmeticType, ItemStack> cosmetics = new HashMap<>() {{
        {
            put(
                    CosmeticType.CIRCLE,
                    new ItemUtil(Material.LIGHT_GRAY_DYE)
                            .setName("&7Circle")
                            .build()
            );

            put(
                    CosmeticType.WINGS,
                    new ItemUtil(Material.PURPLE_DYE)
                            .setName("&5Wings")
                            .build()
            );

            put(
                    CosmeticType.FOUNTAIN,
                    new ItemUtil(Material.BLUE_DYE)
                            .setName("&bFountain")
                            .build()
            );

            put(
                    CosmeticType.HELIX,
                    new ItemUtil(Material.WHITE_DYE)
                            .setName("&fHelix")
                            .build()
            );

            put(
                    CosmeticType.SPIRAL,
                    new ItemUtil(Material.CAMPFIRE)
                            .setName("&cRed spiral")
                            .build()
            );
        }
    }};

    public String getAction(ItemStack itemStack) {
        String action = getActionFromItems(itemStack);
        if (action == null) {
            action = getActionFromFunctionalItems(itemStack);
        }
        return action;
    }

    private String getActionFromItems(ItemStack itemStack) {
        return items.values().stream()
                .filter(itemAction -> isSimilarEnough(itemAction.getItem(), itemStack))
                .map(ItemAction::getAction)
                .findFirst()
                .orElse(null);
    }

    private String getActionFromFunctionalItems(ItemStack itemStack) {
        return functionalItems.values().stream()
                .filter(itemAction -> isSimilarEnough(itemAction.getItem(), itemStack))
                .map(ItemAction::getAction)
                .findFirst()
                .orElse(null);
    }

    public Integer getFunctionalItemSlot(ItemStack itemStack) {
        return functionalItems.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getItem().isSimilar(itemStack))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    public boolean isFunctionalItem(ItemStack itemStack) {
        return isInItems(itemStack) || isInFunctionalItems(itemStack);
    }

    private boolean isInItems(ItemStack itemStack) {
        return items.values().stream()
                .anyMatch(itemAction -> isSimilarEnough(itemAction.getItem(), itemStack));
    }

    private boolean isInFunctionalItems(ItemStack itemStack) {
        return functionalItems.values().stream()
                .anyMatch(itemAction -> isSimilarEnough(itemAction.getItem(), itemStack));
    }

    private boolean isSimilarEnough(ItemStack item1, ItemStack item2) {
        return item1.getType() == item2.getType() &&
                item1.getItemMeta().getDisplayName().equals(item2.getItemMeta().getDisplayName());
    }
}
