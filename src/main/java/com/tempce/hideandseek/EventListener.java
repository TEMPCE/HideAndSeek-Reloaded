package com.tempce.hideandseek;

import com.tempce.hideandseek.core.item.AbstractGameItem;
import com.tempce.hideandseek.core.item.Items;
import com.tempce.hideandseek.core.game.GameState;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

import static com.tempce.hideandseek.Hideandseek.*;
import static com.tempce.hideandseek.core.game.GameMaster.*;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player joniedPlayer = event.getPlayer();

        initPlayer(joniedPlayer);

        if (gameState == GameState.STARTED) {
            spectator.addPlayer(joniedPlayer);
            joniedPlayer.teleport(gameMap.getSpectatorSpawn());
        } else if (gameState == GameState.PREPARE){
            if (!hider.hasPlayer(joniedPlayer) && !seeker.hasPlayer(joniedPlayer)) {
                spectator.addPlayer(joniedPlayer);
                joniedPlayer.teleport(gameMap.getSpectatorSpawn());
            }
        } else {
            lobby.addPlayer(joniedPlayer);
            joniedPlayer.clearActivePotionEffects();
            joniedPlayer.getInventory().clear();
            joniedPlayer.setGameMode(GameMode.ADVENTURE);
            joniedPlayer.setHealth(joniedPlayer.getAttribute(Attribute.MAX_HEALTH).getBaseValue());
            joniedPlayer.teleport(settings.getLobbyLocation());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player movedPlayer = event.getPlayer();

        if (seeker.hasPlayer(movedPlayer) && gameState == GameState.PREPARE) {
            movedPlayer.setHealth(movedPlayer.getAttribute(Attribute.MAX_HEALTH).getBaseValue());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (gameState == GameState.STARTED) {
            game.onPlayerDeath(event);
        } else if (gameState == GameState.PREPARE) {
            if (seeker.hasPlayer(event.getPlayer())) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    event.getPlayer().teleport(gameMap.getSeekerSpawn());
                }, 2L);
            } else {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    event.getPlayer().teleport(gameMap.getHiderSpawn());
                }, 2L);
            }
        } else if (gameState == GameState.UNINITIALIZED) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                event.getPlayer().teleport(settings.getLobbyLocation());
            }, 2L);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item != null) {
            Component itemName = Component.empty();
            if (item.getItemMeta() != null) {
                itemName = item.getItemMeta().displayName();
            }
            for (Items items : Arrays.stream(Items.values()).toList()) {
                AbstractGameItem item_ = items.getItem();

                if (item_.getDisplayName().equals(itemName)) {
                    item_.onUse(player);
                }
            }
        }
    }

}
