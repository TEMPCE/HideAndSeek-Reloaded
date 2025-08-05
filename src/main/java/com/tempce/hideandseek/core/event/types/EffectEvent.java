package com.tempce.hideandseek.core.event.types;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectEvent extends Event {
    private final PotionEffectType effectType;
    private final int duration;
    private final int amplifier;
    private final boolean ambient;
    private final boolean particles;

    public EffectEvent(String effectType, int duration, int amplifier) {
        super("effect");
        this.effectType = PotionEffectType.getByName(effectType);
        this.duration = duration;
        this.amplifier = amplifier;
        this.ambient = false;
        this.particles = true;
    }

    public EffectEvent(String effectType, int duration, int amplifier, boolean ambient) {
        super("effect");
        this.effectType = PotionEffectType.getByName(effectType);
        this.duration = duration;
        this.amplifier = amplifier;
        this.ambient = ambient;
        this.particles = true;
    }

    public EffectEvent(String effectType, int duration, int amplifier, boolean ambient, boolean particles) {
        super("effect");
        this.effectType = PotionEffectType.getByName(effectType);
        this.duration = duration;
        this.amplifier = amplifier;
        this.ambient = ambient;
        this.particles = particles;
    }

    public EffectEvent(PotionEffectType effectType, int duration, int amplifier, boolean ambient, boolean particles) {
        super("effect");
        this.effectType = effectType;
        this.duration = duration;
        this.amplifier = amplifier;
        this.ambient = ambient;
        this.particles = particles;
    }

    public Boolean getParticles() {
        return particles;
    }

    public Boolean getAmbient() {
        return ambient;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public int getDuration() {
        return duration;
    }

    public PotionEffectType getEffectType() {
        return effectType;
    }

    @Override
    public void apply(Player player) {
        player.addPotionEffect(new PotionEffect(
                effectType,
                duration,
                amplifier,
                ambient,
                particles
        ));
    }
}
