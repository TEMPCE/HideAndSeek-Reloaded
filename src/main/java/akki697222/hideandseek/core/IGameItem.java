package akki697222.hideandseek.core;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public interface IGameItem {
    @Nonnull String getDisplayName();
    @Nonnull
    ItemStack getItem();
    void onUse(Player player);
    void tick();
}
