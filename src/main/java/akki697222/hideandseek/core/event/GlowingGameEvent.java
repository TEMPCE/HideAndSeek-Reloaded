package akki697222.hideandseek.core.event;

import akki697222.hideandseek.core.Mode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import static akki697222.hideandseek.game.GameMaster.hider;
import static akki697222.hideandseek.game.GameMaster.seeker;

public class GlowingGameEvent implements GameEvent {
    @Override
    public void onEvent() {
        hider.getEntries().forEach(playerName -> {
            Player player = Bukkit.getPlayer(playerName);
            if (player != null) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 10 * 20, 255));
            }
        });
        seeker.getEntries().forEach(playerName -> {
            Player player = Bukkit.getPlayer(playerName);
            if (player != null) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 10 * 20, 255));
            }
        });
    }

    @Override
    public @NotNull Mode getMode() {
        return Mode.Hard;
    }

    @Override
    public String getEventName() {
        return "ピカピカ";
    }

    @Override
    public String getEventDesc() {
        return "10秒間すべてのプレイヤーが発光します。";
    }
}
