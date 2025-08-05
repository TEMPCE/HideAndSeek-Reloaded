package com.tempce.hideandseek.core.item.items;

import com.tempce.hideandseek.core.item.AbstractGameItem;
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

public class JumpBoost extends AbstractGameItem {
    public JumpBoost() {
        super("jump_boost");
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text(getDisplayNameAsString());
    }

    @Override
    public @NotNull String getDisplayNameAsString() {
        return "天使の翼";
    }

    @Override
    public @NotNull ItemStack getItem() {
        ItemStack item = new ItemStack(Material.SPLASH_POTION, 1);
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();

        if (potionMeta != null) {
            potionMeta.displayName(getDisplayName());
            potionMeta.lore(List.of(Component.text("ジャンプ！")));
            potionMeta.setColor(Color.WHITE);
            potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 20 * 10, 2), true);
            item.setItemMeta(potionMeta);
        }
        return item;
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void tick() {

    }
}
