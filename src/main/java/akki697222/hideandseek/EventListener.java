package akki697222.hideandseek;

import akki697222.hideandseek.core.GameMap;
import akki697222.hideandseek.core.IGameItem;
import akki697222.hideandseek.core.Items;
import akki697222.hideandseek.game.GameMaster;
import akki697222.hideandseek.game.GameState;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

import static akki697222.hideandseek.Hideandseek.settings;
import static akki697222.hideandseek.game.GameMaster.*;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player joniedPlayer = event.getPlayer();

        initPlayer(joniedPlayer);

        if (gameState == GameState.STARTED) {
            spectator.addPlayer(joniedPlayer);
        } else if (gameState == GameState.PREPARE){
            if (!hider.hasPlayer(joniedPlayer) && !seeker.hasPlayer(joniedPlayer)) {
                spectator.addPlayer(joniedPlayer);
            }
        } else {
            lobby.addPlayer(joniedPlayer);
            joniedPlayer.clearActivePotionEffects();
            joniedPlayer.getInventory().clear();
            joniedPlayer.setGameMode(GameMode.ADVENTURE);
            joniedPlayer.setHealth(joniedPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
            joniedPlayer.teleport(settings.getLobbyLocation());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player movedPlayer = event.getPlayer();

        if (seeker.hasPlayer(movedPlayer) && gameState == GameState.PREPARE) {
            movedPlayer.setHealth(movedPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (gameState == GameState.PREPARE || gameState == GameState.STARTED) {
            game.onPlayerDeath(event);
        } else if (gameState == GameState.UNINITIALIZED) {
            event.getPlayer().teleport(settings.getLobbyLocation());
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item != null) {
            String itemName = "";
            if (item.getItemMeta() != null) {
                itemName = item.getItemMeta().getDisplayName();
            }
            for (Items items : Arrays.stream(Items.values()).toList()) {
                IGameItem item_ = items.getItem();

                if (item_.getDisplayName().equals(itemName)) {
                    item_.onUse(player);
                }
            }
        }
    }

}
