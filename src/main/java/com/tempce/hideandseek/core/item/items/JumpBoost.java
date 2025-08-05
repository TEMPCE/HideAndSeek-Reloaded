package com.tempce.hideandseek.core.item.items;

import com.tempce.hideandseek.core.item.PotionItem;
import net.kyori.adventure.text.Component;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JumpBoost extends PotionItem {
    public JumpBoost() {
        super("jump_boost", 200, 2);
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
    public PotionEffectType getEffectType() {
        return PotionEffectType.JUMP_BOOST;
    }

    @Override
    public List<Component> getLore() {
        return List.of(Component.text("ジャンプ！"));
    }
}
