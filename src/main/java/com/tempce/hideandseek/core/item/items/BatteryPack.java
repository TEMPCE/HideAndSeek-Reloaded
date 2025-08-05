package com.tempce.hideandseek.core.item.items;

import com.tempce.hideandseek.core.item.AbstractGameItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.tempce.hideandseek.Hideandseek.playerBatteries;

public class BatteryPack extends AbstractGameItem {
    public BatteryPack() {
        super("battery_pack");
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text(getDisplayNameAsString());
    }

    @Override
    public @NotNull String getDisplayNameAsString() {
        return "バッテリーパック";
    }

    @Override
    public @NotNull ItemStack getItem() {
        ItemStack compass = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta meta = compass.getItemMeta();
        if (meta != null) {
            meta.displayName(getDisplayName());
            meta.lore(List.of(
                    Component.text("§7使用するとバッテリーが3回復します")
            ));
            compass.setItemMeta(meta);
        }
        return compass;
    }

    @Override
    public void onUse(Player player) {
        player.getInventory().remove(player.getInventory().getItemInMainHand());
        playerBatteries.replace(player.getName(), playerBatteries.get(player.getName()) + 3);
    }

    @Override
    public void tick() {

    }
}
