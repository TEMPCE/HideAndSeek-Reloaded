package com.tempce.hideandseek.core.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class PotionItem extends AbstractGameItem {
    public PotionItem(String configFileName, int defaultDuration, int defaultAmplifier) {
        super(configFileName);
        if (!config.contains("duration")) config.set("duration", defaultDuration);
        if (!config.contains("amplifier")) config.set("amplifier", defaultAmplifier);
    }

    @Override
    public @NotNull ItemStack getItem() {
        ItemStack item = new ItemStack(Material.SPLASH_POTION, 1);
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();

        if (potionMeta != null) {
            potionMeta.displayName(getDisplayName());
            potionMeta.lore(getLore());
            potionMeta.setColor(Color.WHITE);
            potionMeta.addCustomEffect(new PotionEffect(getEffectType(), config.getInt("duration"), config.getInt("amplifier")), true);
            item.setItemMeta(potionMeta);
        }
        return item;
    }

    public abstract PotionEffectType getEffectType();
    public abstract List<Component> getLore();

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void tick() {

    }
}
