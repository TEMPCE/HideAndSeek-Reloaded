package com.tempce.hideandseek;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tempce.hideandseek.commands.EventCommand;
import com.tempce.hideandseek.commands.ShopCommand;
import com.tempce.hideandseek.commands.has.HaSCommand;
import com.tempce.hideandseek.commands.map.MapCommand;
import com.tempce.hideandseek.core.Settings;
import com.tempce.hideandseek.core.event.EventParser;
import com.tempce.hideandseek.core.event.GameEvent;
import com.tempce.hideandseek.core.game.GameMaster;
import com.tempce.hideandseek.core.map.Maps;
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
import java.io.FileReader;
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
        getCommand("event").setExecutor(new EventCommand());

        //regsiter events
        if (!new File(getDataFolder(), "events").exists()) new File(getDataFolder(), "events").mkdir();
        saveResource("events/blindness.json", false);
        saveResource("events/glow.json", false);
        saveResource("events/invisible_hider.json", false);
        saveResource("events/invisible_seeker.json", false);
        saveResource("events/jumpboost.json", false);
        saveResource("events/shuffle.json", false);
        saveResource("events/slowness.json", false);
        saveResource("events/speed.json", false);
        saveResource("events/readme.md", false);

        loadEvents();

        if (!new File(getDataFolder(), "items").exists()) new File(getDataFolder(), "items").mkdir();

        // final init
        GameMaster.postInit();
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        saveDefaultConfig();
        config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();
        File messageFile = new File(getDataFolder(), "message.yml");
        if (!messageFile.exists()) {
            saveResource("message.yml", false);
        }
        messageConfig = YamlConfiguration.loadConfiguration(messageFile);
        loadEvents();
    }

    public static void loadEvents() {
        events.clear();
        File eventsFolder = new File(plugin.getDataFolder(), "events");
        File[] files = eventsFolder.listFiles((dir, name) -> name.endsWith(".json"));

        if (files == null || files.length == 0) {
            logger.warn("No Events found");
            return;
        }

        for (File file : files) {
            try (FileReader reader = new FileReader(file)) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                GameEvent event = EventParser.parseGameEvent(json);
                events.add(event);
                logger.info("Registered Event '{}' ({})", event.getId(), file.getName());
            } catch (Exception e) {
                logger.error("Failed to register event: {}", file.getName(), e);
            }
        }
    }

    @Override
    public void onDisable() {

    }
}
