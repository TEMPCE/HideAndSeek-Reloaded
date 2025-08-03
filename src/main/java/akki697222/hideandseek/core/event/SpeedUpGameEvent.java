package akki697222.hideandseek.core.event;

import akki697222.hideandseek.core.Mode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import static akki697222.hideandseek.game.GameMaster.hider;

public class SpeedUpGameEvent implements GameEvent {
    @Override
    public void onEvent() {
        hider.getEntries().forEach(playerName -> {
            Player player = Bukkit.getPlayer(playerName);
            if (player != null) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 15 * 20, 2));
            }
        });
    }

    @Override
    public @NotNull Mode getMode() {
        return Mode.Normal;
    }

    @Override
    public String getEventName() {
        return "スピードアップ";
    }

    @Override
    public String getEventDesc() {
        return "逃げの足が15秒間早くなります。";
    }
}
