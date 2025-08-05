package com.tempce.hideandseek.core.item;

import com.tempce.hideandseek.utils.JsonConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.io.File;

import static com.tempce.hideandseek.Hideandseek.plugin;

public abstract class AbstractGameItem {
    protected final JsonConfig config;

    public AbstractGameItem(String configFileName) {
        try {
            this.config = new JsonConfig(
                    new File(plugin.getDataFolder(), "items/" + configFileName + ".json").toPath()
            );
        } catch (Exception e) {
            throw new RuntimeException("Unable to create item config", e);
        }
    }

    @Nonnull
    public abstract Component getDisplayName();
    @Nonnull
    public abstract String getDisplayNameAsString();
    @Nonnull
    public abstract ItemStack getItem();
    public abstract void onUse(Player player);
    public abstract void tick();
}
