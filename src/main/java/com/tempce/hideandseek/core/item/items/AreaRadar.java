package com.tempce.hideandseek.core.item.items;

import com.tempce.hideandseek.core.item.AbstractGameItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.tempce.hideandseek.Hideandseek.*;
import static com.tempce.hideandseek.core.game.GameMaster.scoreboard;

public class AreaRadar extends AbstractGameItem {
    public AreaRadar() {
        super("area_radar");
        if (!config.contains("range")) config.set("range", 30);
        if (!config.contains("batteryConsumes")) config.set("batteryConsumes", 1);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text(getDisplayNameAsString());
    }

    @Override
    public @NotNull String getDisplayNameAsString() {
        return "エリアレーダー";
    }

    @Override
    public @NotNull ItemStack getItem() {
        ItemStack compass = new ItemStack(Material.RECOVERY_COMPASS);
        ItemMeta meta = compass.getItemMeta();
        if (meta != null) {
            meta.displayName(getDisplayName());
            meta.lore(List.of(
                    Component.text("§7半径%range%B以内にいるプレイヤーを捜索することができるレーダーです。"
                            .replaceAll("%range%", String.valueOf(config.getInt("range"))))
            ));
            compass.setItemMeta(meta);
        }
        return compass;
    }

    @Override
    public void onUse(Player player) {
        String playerName = player.getName();
        if (playerBatteries.get(playerName) > 0) {
            int range = config.getInt("range");
            if (isPlayersInTeam(player, "hider")
                    && isTeamNearby(player, "seeker", range)) {
                player.sendMessage(messageConfig.getString("item.radar.foundSeeker").replaceAll("%range%", String.valueOf(range)));
            } else if (isPlayersInTeam(player, "seeker")
                    && isTeamNearby(player, "hider", range)) {
                player.sendMessage(messageConfig.getString("item.radar.foundHider").replaceAll("%range%", String.valueOf(range)));
            } else {
                player.sendMessage(messageConfig.getString("item.radar.nothing"));
            }
            playerBatteries.replace(playerName, playerBatteries.get(playerName) - config.getInt("batteryConsumes"));
        } else {
            player.sendMessage(messageConfig.getString("item.batteryDead"));
        }
    }

    @Override
    public void tick() {

    }

    private boolean isTeamNearby(Player player, String teamName, int radius) {
        Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(teamName);

        if (team == null) {
            return false;
        }

        for (Player nearbyPlayer : player.getWorld().getPlayers()) {
            if (nearbyPlayer != player && nearbyPlayer.getLocation().distance(player.getLocation()) <= radius) {
                if (team.hasEntry(nearbyPlayer.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isPlayersInTeam(Player player, String name) {
        Team playerTeam = scoreboard.getEntryTeam(player.getName());
        return playerTeam != null && playerTeam.getName().equalsIgnoreCase(name);
    }
}
