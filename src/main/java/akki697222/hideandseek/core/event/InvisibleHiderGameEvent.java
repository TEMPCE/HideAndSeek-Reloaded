package akki697222.hideandseek.core.event;

import akki697222.hideandseek.core.Mode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import static akki697222.hideandseek.game.GameMaster.hider;
import static akki697222.hideandseek.game.GameMaster.seeker;

public class InvisibleHiderGameEvent implements GameEvent {
    @Override
    public void onEvent() {
        hider.getEntries().forEach(playerName -> {
            Player player = Bukkit.getPlayer(playerName);
            if (player != null) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 15 * 20, 255, false, false));
            }
        });
    }

    @Override
    public @NotNull Mode getMode() {
        return Mode.Hard;
    }

    @Override
    public String getEventName() {
        return "息を潜めて...";
    }

    @Override
    public String getEventDesc() {
        return "逃げが15秒間透明化します。";
    }
}
