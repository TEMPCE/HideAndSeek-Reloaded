package akki697222.hideandseek;

import akki697222.hideandseek.commands.ShopCommand;
import akki697222.hideandseek.commands.has.HaSCommand;
import akki697222.hideandseek.commands.map.MapCommand;
import akki697222.hideandseek.core.Maps;
import akki697222.hideandseek.core.Settings;
import akki697222.hideandseek.core.event.*;
import akki697222.hideandseek.game.GameMaster;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.mikigal.config.ConfigAPI;
import pl.mikigal.config.style.CommentStyle;
import pl.mikigal.config.style.NameStyle;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Hideandseek extends JavaPlugin {
    public static Hideandseek plugin;
    public static Logger logger = LoggerFactory.getLogger("hide-and-seek");

    public static YamlConfiguration messageConfig;
    public static FileConfiguration config;
    public static Maps mapsConfig;
    public static Settings settings;

    public static Map<String, Integer> playerPoints = new HashMap<>();
    public static Map<String, Integer> playerBatteries = new HashMap<>();

    public static final List<GameEvent> events = new ArrayList<>();

    @Override
    public void onEnable() {
        plugin = this;

        File messagesConfigFile = new File(getDataFolder(), "message.yml");
        saveResource("message.yml", false);
        saveDefaultConfig();
        messageConfig = YamlConfiguration.loadConfiguration(messagesConfigFile);
        config = getConfig();
        Bukkit.getScheduler().runTask(this, () -> {
            mapsConfig = ConfigAPI.init(Maps.class, NameStyle.UNDERSCORE, CommentStyle.INLINE, true, this);
            settings = ConfigAPI.init(Settings.class, NameStyle.UNDERSCORE, CommentStyle.INLINE, true, this);
        });

        Bukkit.getPluginManager().registerEvents(new EventListener(), this);

        //register commands
        getCommand("hideandseek").setExecutor(new HaSCommand());
        getCommand("map").setExecutor(new MapCommand());
        getCommand("shop").setExecutor(new ShopCommand());

        //regsiter events
        registerGameEvent(new SpeedUpGameEvent());
        registerGameEvent(new GlowingGameEvent());
        registerGameEvent(new JumpBoostGameEvent());
        registerGameEvent(new InvisibleSeekerGameEvent());
        registerGameEvent(new InvisibleHiderGameEvent());
        registerGameEvent(new ShuffleGameEvent());
        registerGameEvent(new SlownessGameEvent());;

        // final init
        GameMaster.postInit();
    }

    public void registerGameEvent(GameEvent event) {
        events.add(event);
    }

    @Override
    public void onDisable() {

    }
}
