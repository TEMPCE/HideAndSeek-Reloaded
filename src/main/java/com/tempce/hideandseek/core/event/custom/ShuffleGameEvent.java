package com.tempce.hideandseek.core.event.custom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

import static com.tempce.hideandseek.core.game.GameMaster.hider;

public class ShuffleGameEvent implements CustomGameEvent {
    @Override
    public void onEvent() {
        List<Player> hiders = new java.util.ArrayList<>(hider.getEntries().stream().map(Bukkit::getPlayer).toList());
        Collections.shuffle(hiders);
        for (int i = 0; i < hiders.size() - 1; i += 2) {
            Player one = hiders.get(i);
            Player two = hiders.get(i + 1);
            if (one != null && two != null) {
                Location oneL = one.getLocation();
                Location twoL = two.getLocation();
                one.teleport(twoL);
                two.teleport(oneL);
            }
        }
    }
}
