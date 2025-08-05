package com.tempce.hideandseek.core.item.items;

import com.tempce.hideandseek.core.item.AbstractGameItem;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.tempce.hideandseek.Hideandseek.plugin;

public class ProtectionShield extends AbstractGameItem {
    public static final List<Player> shieldedPlayers = new ArrayList<>();

    public ProtectionShield() {
        super("protection_shield");
        if (!config.contains("duration")) config.set("duration", 30);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text(getDisplayNameAsString());
    }

    @Override
    public @NotNull String getDisplayNameAsString() {
        return "プロテクションシールド";
    }

    @Override
    public @NotNull ItemStack getItem() {
        ItemStack item = new ItemStack(Material.HEART_OF_THE_SEA, 1);
        Damageable meta = (Damageable) item.getItemMeta();
        if (meta != null) {
            meta.displayName(getDisplayName());
            meta.lore(List.of(
                    Component.text("使用すると" + config.getInt("duration") + "秒間鬼に殴られても死なずに逃げの誰かにテレポートするシールドを付与します。"),
                    Component.text("ただしテレポート後は3秒間発光します。")
            ));
            item.setItemMeta(meta);
        }
        return item;
    }

    @Override
    public void onUse(Player player) {
        player.getInventory().remove(player.getInventory().getItemInMainHand());
        shieldedPlayers.add(player);
        player.playSound(
                Sound.sound(
                        org.bukkit.Sound.BLOCK_BEACON_ACTIVATE,
                        Sound.Source.MASTER,
                        1.0f,
                        1.0f
                )
        );
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (shieldedPlayers.contains(player)) {
                player.playSound(
                        Sound.sound(
                                org.bukkit.Sound.BLOCK_BEACON_DEACTIVATE,
                                Sound.Source.MASTER,
                                1.0f,
                                1.0f
                        )
                );
                shieldedPlayers.remove(player);
            }
        }, config.getInt("duration") * 20L);
    }

    @Override
    public void tick() {

    }
}
