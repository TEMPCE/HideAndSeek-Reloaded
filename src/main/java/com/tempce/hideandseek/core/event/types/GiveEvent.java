package com.tempce.hideandseek.core.event.types;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GiveEvent extends Event {
    private final List<ItemStack> items;

    public GiveEvent(List<ItemStack> items) {
        super("give");
        this.items = items;
    }

    @Override
    public void apply(Player player) {
        for (ItemStack item : items) {
            player.getInventory().addItem(item);
        }
    }

    public List<ItemStack> getItems() {
        return items;
    }
}
