package com.tempce.hideandseek.core.item.items;

import com.tempce.hideandseek.core.item.AbstractGameItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.jetbrains.annotations.NotNull;

public class KnockbackStick extends AbstractGameItem {
    public KnockbackStick() {
        super("knockback_stick");
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text(getDisplayNameAsString());
    }

    @Override
    public @NotNull String getDisplayNameAsString() {
        return "鬼の金棒";
    }

    @Override
    public @NotNull ItemStack getItem() {
        ItemStack item = new ItemStack(Material.WOODEN_SWORD, 1);
        Damageable meta = (Damageable) item.getItemMeta();
        if (meta != null) {
            meta.displayName(getDisplayName());
            meta.setDamage(60);
            item.setItemMeta(meta);
        }
        item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 8);
        return item;
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void tick() {

    }
}
