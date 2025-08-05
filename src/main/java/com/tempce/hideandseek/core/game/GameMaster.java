package com.tempce.hideandseek.core.game;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.tempce.hideandseek.core.Mode;
import com.tempce.hideandseek.core.map.GameMap;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.tempce.hideandseek.Hideandseek.*;

public class GameMaster {
    // Others
    private static boolean isInitialized = false;
    public static BossBar bossBar;
    public static Game game;
    public static GameMap gameMap;
    public static Mode mode;

    // Teams
    public static Team hider;
    public static Team seeker;
    public static Team spectator;
    public static Team lobby;
    public static Team dead;
    public static Scoreboard scoreboard;
    public static Tick tick = new Tick();
    public static GameState gameState = GameState.UNINITIALIZED;

    /**
     * first init
     */
    public static void postInit() {
        if (isInitialized) {
            logger.warn("Plugin already initialized.");
            return;
        }
        isInitialized = true;

        //ワールドがロードされたら
        Bukkit.getScheduler().runTask(plugin, () -> {
            World world = Bukkit.getWorld("world");

            if (world != null) {
                world.setGameRule(GameRule.FALL_DAMAGE, false);
                world.setGameRule(GameRule.NATURAL_REGENERATION, false);
                world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
                world.setGameRule(GameRule.SPAWN_RADIUS, 0);
                world.setGameRule(GameRule.KEEP_INVENTORY, true);
                world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
                world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
                world.setGameRule(GameRule.DO_MOB_LOOT, false);
                world.setPVP(true);
            }

            scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
            //team
            hider = teamSetUp("hider", NamedTextColor.GREEN);
            seeker = teamSetUp("seeker", NamedTextColor.RED);
            spectator = teamSetUp("spectator", NamedTextColor.DARK_GRAY);
            lobby = teamSetUp("lobby", NamedTextColor.YELLOW);
            dead = teamSetUp("dead", NamedTextColor.DARK_RED);
            //bossbar
            bossBar = Bukkit.createBossBar(messageConfig.getString("bossbars.defaultTitle"), BarColor.GREEN, BarStyle.SOLID);
            //others
            tick.runTaskTimer(plugin, 0L, 1L);
        });
    }

    public static Team teamSetUp(String name, NamedTextColor teamColor) {
        Team team;
        if (scoreboard.getTeam(name) != null) {
            team = scoreboard.getTeam(name);
        } else {
            team = scoreboard.registerNewTeam(name);
            team.prefix(Component.text(messageConfig.getString("teamPrefixes." + name, "null")));
            team.color(teamColor);
            team.setAllowFriendlyFire(false);
            team.setCanSeeFriendlyInvisibles(true);
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
        }
        return team;
    }

    public static void init(int seekers) {
        Mode[] modes = Mode.values();
        init(seekers, modes[new Random().nextInt(modes.length)]);
    }

    public static void init(int seekers, Mode mode) {
        init(seekers, mode, null);
    }

    /**
     * init for next games
     */
    public static void init(int seekers, Mode mode, @Nullable String map) {
        logger.info("Initializing Game...");
        gameState = GameState.INITIALIZED;
        game = new Game();
        GameMaster.mode = mode;

        List<GameMap> mapList = new ArrayList<>(mapsConfig.getMaps());
        Collections.shuffle(mapList);

        for (String entry : hider.getEntries()) {
            hider.removeEntry(entry);
        }

        for (String entry : seeker.getEntries()) {
            seeker.removeEntry(entry);
        }

        for (String entry : dead.getEntries()) {
            dead.removeEntry(entry);
        }

        gameMap = mapList.get(0);
        if (map != null) {
            for (GameMap m : mapList) {
                if (m.getName().equals(map)) {
                    gameMap = m;
                    break;
                }
            }
        }

        if (mapList.size() == 1 && gameMap.getName().equals("empty")) {
            Bukkit.broadcast(Component.text("§cゲームを開始できませんでした。マップを最低でも1つ以上作成する必要があります。"));
            end(true, false);
            return;
        }

        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        players.forEach(player -> {
            hider.addPlayer(player);
        });

        Collections.shuffle(players);

        for (int i = 0; i < seekers; i++) {
            seeker.addPlayer(players.remove(0));
        }

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.setHealth(player.getAttribute(Attribute.MAX_HEALTH).getBaseValue());
            playerBatteries.put(player.getName(), 3);
        });

        prepare();
    }

    public static void prepare() {
        logger.info("Preparing Game...");
        gameState = GameState.PREPARE;

        hider.getEntries().forEach(playerName -> {
            Player player = Bukkit.getPlayer(playerName);
            if (player != null) {
                player.teleport(gameMap.getHiderSpawn());
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 255, false, false));
                player.sendMessage(prefixes(messageConfig.getString("team.joinedHider", "null")));
                player.setGameMode(GameMode.ADVENTURE);
            }
        });

        seeker.getEntries().forEach(playerName -> {
            Player player = Bukkit.getPlayer(playerName);
            if (player != null) {
                player.teleport(gameMap.getSeekerSpawn());
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 255, false, false));
                player.sendMessage(prefixes(messageConfig.getString("team.joinedSeeker", "null")));
                player.setGameMode(GameMode.ADVENTURE);
            }
        });

        spectator.getEntries().forEach(playerName -> {
            Player player = Bukkit.getPlayer(playerName);
            if (player != null) {
                player.teleport(gameMap.getSpectatorSpawn());
                player.setGameMode(GameMode.SPECTATOR);
            }
        });

        String sb = prefixes("今回のマップです。") +
                "- " + gameMap.getTitle() + " -\n" +
                gameMap.getDescription() + "\n" +
                "製作者: " + gameMap.getCredits();
        Bukkit.broadcast(Component.text(sb));

        new Timer(15, bossBar, GameMaster::start, "逃げろ！ 残り%time%秒！")
                .runTaskTimer(plugin, 0L, 1L);
    }

    private static Timer gameTimer;

    public static void start() {
        logger.info("Starting Game...");
        gameState = GameState.STARTED;
        game.runTaskTimer(plugin, 0L, 1L);

        hider.getEntries().forEach(playerName -> {
            Player player = Bukkit.getPlayer(playerName);
            if (player != null) {
                playerPoints.put(playerName, settings.getDefaultPlayerPoints());
                player.clearActivePotionEffects();
            }
        });

        seeker.getEntries().forEach(playerName -> {
            Player player = Bukkit.getPlayer(playerName);
            if (player != null) {
                playerPoints.put(playerName, settings.getDefaultPlayerPoints());
                player.clearActivePotionEffects();
                switch (mode) {
                    case Normal -> {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false, false));
                    }
                    case Hard -> {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3, false, false));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 255, false, false));
                    }
                    case Insane -> {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 5, false, false));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 255, false, false));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, Integer.MAX_VALUE, 2, false, false));
                    }
                }
                PlayerInventory inventory = player.getInventory();
                ItemStack seekerAxe = new ItemStack(Material.NETHERITE_AXE);
                ItemMeta meta = seekerAxe.getItemMeta();
                if (meta != null) {
                    AttributeModifier damageModifier = new AttributeModifier(
                            Attribute.ATTACK_DAMAGE.getKey(), Double.MAX_VALUE, AttributeModifier.Operation.ADD_NUMBER
                    );

                    // Multimap に追加
                    Multimap<Attribute, AttributeModifier> multimap = ImmutableMultimap.of(
                            Attribute.ATTACK_DAMAGE, damageModifier
                    );

                    meta.setAttributeModifiers(multimap);
                    seekerAxe.setItemMeta(meta);
                }
                inventory.addItem(seekerAxe);
            }
        });

        if (!gameMap.getExecutableCommands().isEmpty()) {
            for (String cmd : gameMap.getExecutableCommands()) {
                if (cmd == null || cmd.isEmpty()) continue;
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
            }
        }

        gameTimer = new Timer(settings.getTimeLimit(), bossBar, () -> {
            end(false, false);
        }, "ゲーム終了まであと%time%秒");
        gameTimer.runTaskTimer(plugin, 0L, 1L);

    }

    public static void end(boolean isDraw, boolean isSeekerWin) {
        gameState = GameState.UNINITIALIZED;
        if (gameTimer != null) {
            gameTimer.setCanceled(true);
        }
        gameTimer = null;
        playerPoints.clear();
        playerBatteries.clear();

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.setHealth(player.getAttribute(Attribute.MAX_HEALTH).getBaseValue());
            player.setGameMode(GameMode.ADVENTURE);
            player.teleport(settings.getLobbyLocation());
            player.clearActivePotionEffects();
            player.getInventory().clear();
            lobby.addPlayer(player);
            if (isDraw) {
                player.showTitle(Title.title(Component.text(messageConfig.getString("game.draw", "null")), Component.empty()));
            } else {
                if (isSeekerWin) {
                    player.showTitle(Title.title(Component.text(messageConfig.getString("game.seekerWin", "null")), Component.empty()));
                    player.playSound(Sound.sound(org.bukkit.Sound.ENTITY_WITHER_DEATH, Sound.Source.MASTER, 0.5f, 1.0f));
                } else {
                    player.showTitle(Title.title(Component.text(messageConfig.getString("game.hiderWin", "null")), Component.empty()));
                    player.playSound(Sound.sound(org.bukkit.Sound.UI_TOAST_CHALLENGE_COMPLETE, Sound.Source.MASTER, 0.5f, 1.0f));
                }
            }
        });

        if (game != null) {
            game.cancel();
        }
        game = null;
        gameMap = null;

        bossBar.setTitle(messageConfig.getString("bossbars.defaultTitle"));
    }

    public static void initPlayer(Player player) {
        bossBar.addPlayer(player);
    }

    public static String prefixes(String message) {
        return messageConfig.getString("prefix", "null") + message;
    }
}
