package eu.boxhunt.lobby;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.annotations.LiteCommandsAnnotations;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import eu.boxhunt.lobby.command.ConfigureCommand;
import eu.boxhunt.lobby.command.GameModeCommand;
import eu.boxhunt.lobby.command.LeaveArenaCommand;
import eu.boxhunt.lobby.command.LobbyCommand;
import eu.boxhunt.lobby.command.argument.GameModeArgument;
import eu.boxhunt.lobby.command.handler.CorrectUsageHandler;
import eu.boxhunt.lobby.command.handler.PermissionHandler;
import eu.boxhunt.lobby.libs.InventoryManager;
import eu.boxhunt.lobby.listener.*;
import eu.boxhunt.lobby.manager.ArenaManager;
import eu.boxhunt.lobby.manager.ItemManager;
import eu.boxhunt.lobby.manager.SelectorManager;
import eu.boxhunt.lobby.manager.user.UserManager;
import eu.boxhunt.lobby.storage.config.other.GuiConfiguration;
import eu.boxhunt.lobby.storage.config.MessageConfiguration;
import eu.boxhunt.lobby.storage.config.PluginConfiguration;
import eu.boxhunt.lobby.storage.config.other.ArenaConfiguration;
import eu.boxhunt.lobby.storage.config.other.serializer.KitSerializer;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import lombok.Getter;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class LobbyPlugin extends JavaPlugin {

    /*
       TODO:
        - More custom items,
        - More custom kits (premium, other kits),
        - Custom kit layout,
        - Multiply arenas,
        - Cosmetics,
        - More commands,
        - Implement efficiently particles on pvp arena borders,
        - KillStreak leaderboard on Pvp arena,
        - Double jump (set available to fly, set flying false - if not on gamemode),
        - LaunchPad.
     */

    @Getter
    public static LobbyPlugin instance;

    private UserManager userManager;
    private ItemManager itemManager;
    private InventoryManager inventoryManager;
    private ArenaManager arenaManager;
    private SelectorManager selectorManager;

    private LiteCommands<CommandSender> liteCommands;

    private PluginConfiguration pluginConfiguration;
    private MessageConfiguration messageConfiguration;
    private GuiConfiguration guiConfiguration;
    private ArenaConfiguration arenaConfiguration;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        registerConfigs();
        registerManagers();
        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        liteCommands.unregister();
    }

    private void registerConfigs() {
        pluginConfiguration = ConfigManager.create(PluginConfiguration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(new File(getDataFolder(), "configuration.yml"));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });

        messageConfiguration = ConfigManager.create(MessageConfiguration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer());
            it.withBindFile(new File(getDataFolder(), "messages.yml"));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });

        guiConfiguration = ConfigManager.create(GuiConfiguration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(new File(getDataFolder(), "other/gui.yml"));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });

        arenaConfiguration = ConfigManager.create(ArenaConfiguration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(new File(getDataFolder(), "other/arena.yml"));
            it.withSerdesPack(serdes -> serdes.register(new KitSerializer()));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
    }

    private void registerManagers() {
        userManager = new UserManager();
        arenaManager = new ArenaManager(this);
        itemManager = new ItemManager(this);
        selectorManager = new SelectorManager();

        inventoryManager = new InventoryManager(this);
        inventoryManager.init();
    }

    private void registerListeners() {
        val pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new PlayerAsynChatListener(this), this);
        pluginManager.registerEvents(new PlayerInteractListener(this), this);
        pluginManager.registerEvents(new PlayerDeathListener(this), this);
        pluginManager.registerEvents(new PlayerJoinListener(this), this);
        pluginManager.registerEvents(new PlayerMoveListener(this), this);
        pluginManager.registerEvents(new EntityDamageListener(this), this);
        pluginManager.registerEvents(new PlayerFishListener(), this);
        pluginManager.registerEvents(new PlayerBlockBreakListener(), this);
        pluginManager.registerEvents(new FoodLevelChangeListener(), this);
        pluginManager.registerEvents(new PlayerBlockPlaceListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new PlayerDropItemListener(), this);
        pluginManager.registerEvents(new InventoryListener(), this);
    }

    private void registerCommands() {
        liteCommands = LiteBukkitFactory.builder()
                .settings(settings -> settings.fallbackPrefix("maquq-lobby").nativePermissions(true))
                .commands(LiteCommandsAnnotations.of(
                                new ConfigureCommand(this),
                                new GameModeCommand(this),
                                new LobbyCommand(this),
                                new LeaveArenaCommand(this)
                        )
                )
                .argument(GameMode.class, new GameModeArgument(this))
                .message(LiteBukkitMessages.PLAYER_ONLY, "&cThis command is available only for players.")
                .message(LiteBukkitMessages.PLAYER_NOT_FOUND, "&cCan't find player with this name.")
                .invalidUsage(new CorrectUsageHandler())
                .missingPermission(new PermissionHandler())
                .build();
    }
}
