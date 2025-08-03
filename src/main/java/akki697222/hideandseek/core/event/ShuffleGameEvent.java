package akki697222.hideandseek.core.event;

import akki697222.hideandseek.core.Mode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static akki697222.hideandseek.game.GameMaster.hider;

public class ShuffleGameEvent implements GameEvent {
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

    @Override
    public @NotNull Mode getMode() {
        return Mode.Normal;
    }

    @Override
    public String getEventName() {
        return "シャッフル";
    }

    @Override
    public String getEventDesc() {
        return "逃げの位置がすべて入れ替わります。";
    }
}
