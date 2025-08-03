package akki697222.hideandseek.core.event;

import akki697222.hideandseek.core.Mode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import static akki697222.hideandseek.game.GameMaster.hider;
import static akki697222.hideandseek.game.GameMaster.seeker;

public class JumpBoostGameEvent implements GameEvent {
    @Override
    public void onEvent() {
        hider.getEntries().forEach(playerName -> {
            Player player = Bukkit.getPlayer(playerName);
            if (player != null) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 15 * 20, 2));
            }
        });
        seeker.getEntries().forEach(playerName -> {
            Player player = Bukkit.getPlayer(playerName);
            if (player != null) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 15 * 20, 2));
            }
        });
    }

    @Override
    public @NotNull Mode getMode() {
        return Mode.Normal;
    }

    @Override
    public String getEventName() {
        return "跳躍力";
    }

    @Override
    public String getEventDesc() {
        return "15秒間すべてのプレイヤーの跳躍力が上昇します。";
    }
}
