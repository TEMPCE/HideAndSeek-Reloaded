package com.tempce.hideandseek.core.event.types;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class EffectEvent extends Event {
    private final List<PotionEffect> effects;

    public EffectEvent(List<PotionEffect> effects) {
        super("effect");
        this.effects = effects;
    }

    @Override
    public void apply(Player player) {
        effects.forEach(player::addPotionEffect);
    }
}
