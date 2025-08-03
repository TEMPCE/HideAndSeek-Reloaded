package akki697222.hideandseek.items;

import akki697222.hideandseek.core.IGameItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.jetbrains.annotations.NotNull;

public class KnockbackStick implements IGameItem {
    @Override
    public @NotNull String getDisplayName() {
        return "鬼の金棒";
    }

    @Override
    public @NotNull ItemStack getItem() {
        ItemStack item = new ItemStack(Material.WOODEN_SWORD, 1);
        Damageable meta = (Damageable) item.getItemMeta();
        if (meta != null) {
            meta.displayName(Component.text("鬼の金棒"));
            meta.setDamage(60);
            item.setItemMeta(meta);
        }
        item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 8);
        return item;
    }

    @Override
    public void onUse(Player player) {

    }

    @Override
    public void tick() {

    }
}
