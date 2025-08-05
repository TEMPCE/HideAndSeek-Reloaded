package com.tempce.hideandseek.core.item.items;

import com.tempce.hideandseek.core.item.PotionItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Redbull extends PotionItem {
    public Redbull() {
        super("redbull", 300, 2);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text( getDisplayNameAsString());
    }

    @Override
    public @NotNull String getDisplayNameAsString() {
        return "レッドブル";
    }

    @Override
    public PotionEffectType getEffectType() {
        return PotionEffectType.SPEED;
    }

    @Override
    public List<Component> getLore() {
        return List.of(Component.text("翼を授ける"));
    }
}
