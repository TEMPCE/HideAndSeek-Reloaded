package com.tempce.hideandseek.core.game;

import com.tempce.hideandseek.core.Mode;
import com.tempce.hideandseek.core.event.GameEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.tempce.hideandseek.Hideandseek.*;
import static com.tempce.hideandseek.core.game.GameMaster.*;

public class Game extends BukkitRunnable {
    private int tick;
    private boolean lastPlayer = false;

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendActionBar(Component.text(messageConfig.getString("game.batteries").replaceAll("%battery%", String.valueOf(playerBatteries.get(player.getName())))));
        }

        if (hider.getEntries().isEmpty() && gameState == GameState.STARTED) {
            end(false, true);
        }

        if (seeker.getEntries().isEmpty() && gameState == GameState.STARTED) {
            end(false, false);
        }

        if (hider.getEntries().size() == 1 && !lastPlayer) {
            lastPlayer = true;
            Bukkit.broadcast(Component.text(prefixes(messageConfig.getString("game.lastPlayer").replaceAll("%player%", hider.getEntries().iterator().next()))));
        }

        if (tick % settings.getEventCycle() == 0 && gameState == GameState.STARTED && tick != 0) {
            List<GameEvent> eventList_ = new ArrayList<>(events);
            Collections.shuffle(eventList_);
            for (GameEvent event : eventList_) {
                if (shouldApplyEvent(event.getMode())) {
                    event.applyEvent();
                    Bukkit.broadcast(Component.text(prefixes("イベント: " + event.getName() + "\n" + event.getDesc())));
                    break;
                }
            }
        }

        if (tick % ((settings.getTimeLimit() - 15) * 20) == 0 && gameState == GameState.STARTED && tick != 0) {
            Bukkit.broadcast(Component.text(prefixes(messageConfig.getString("game.glow"))));
            hider.getEntries().forEach(playerName -> {
                Player player = Bukkit.getPlayer(playerName);
                if (player != null) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 255, false, false));
                }
            });
        }

        if (tick % 1200 == 0 && gameState == GameState.STARTED && tick != 0) {
            hider.getEntries().forEach(playerName -> {
                Player player = Bukkit.getPlayer(playerName);
                if (player != null && playerPoints.containsKey(playerName)) {
                    playerPoints.replace(playerName, playerPoints.get(playerName) + 1);
                    player.sendMessage(prefixes(messageConfig.getString("shop.getPoint")));
                }
            });
        }

        tick++;
    }

    private boolean shouldApplyEvent(@Nonnull Mode eventMode) {
        switch (mode) {
            case Insane -> {
                return eventMode == Mode.Insane || eventMode == Mode.Hard || eventMode == Mode.Normal;
            }
            case Hard -> {
                return eventMode == Mode.Hard || eventMode == Mode.Normal;
            }
            case Normal -> {
                return eventMode == Mode.Normal;
            }
            default -> {
                return false;
            }
        }
    }

    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();

        if (hider.hasPlayer(player)) {
            dead.addPlayer(player);
            player.setGameMode(GameMode.SPECTATOR);
            player.getInventory().clear();
            player.clearActivePotionEffects();
            if (player.getKiller() != null) {
                event.deathMessage(Component.text(prefixes(messageConfig.getString("game.deathHider", "null").replaceAll("%player%", player.getName()).replaceAll("%killer%", player.getKiller().getName()))));
            } else {
                event.deathMessage(Component.text(prefixes(messageConfig.getString("game.deathHiderSingle", "null").replaceAll("%player%", player.getName()))));
            }
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.teleport(gameMap.getSpectatorSpawn());
            }, 2L);
        } else if (seeker.hasPlayer(player)) {
            if (player.getKiller() != null) {
                event.deathMessage(Component.text(prefixes(messageConfig.getString("game.deathSeeker", "null").replaceAll("%player%", player.getName()).replaceAll("%killer%", player.getKiller().getName()))));
            } else {
                event.deathMessage(Component.text(prefixes(messageConfig.getString("game.deathSeekerSingle", "null").replaceAll("%player%", player.getName()))));
            }
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.teleport(gameMap.getSeekerSpawn());
                player.setGameMode(GameMode.ADVENTURE);
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
            }, 2L);
        }
    }
}
