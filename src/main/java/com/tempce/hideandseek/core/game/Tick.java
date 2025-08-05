package com.tempce.hideandseek.core.game;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tick extends BukkitRunnable {
    private int tick;

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 255, false, false));
        });

        if (tick % 2400 == 0) {
            List<String> tips = new ArrayList<>(List.of(
                    "'/shop'または、'/s'でゲーム中にショップを開くことができます。",
                    "ショップのアイテムをうまく利用して鬼から逃げ切りましょう。",
                    "スピードのポーションを使用すれば鬼と同じ速度で移動できます。"
            ));
            Collections.shuffle(tips);
            Bukkit.broadcast(Component.text("§7[§eTips§7]§f " + tips.get(0)));
        }

        tick++;
    }
}
