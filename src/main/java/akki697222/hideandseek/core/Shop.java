package akki697222.hideandseek.core;

import de.themoep.inventorygui.DynamicGuiElement;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;

import static akki697222.hideandseek.Hideandseek.*;
import static akki697222.hideandseek.game.GameMaster.prefixes;

public class Shop {
    public static String[] layout = {
            "123456789",
            "         ",
            "        0"
    };

    public static void show(Player player, boolean freeItems) {
        InventoryGui gui = new InventoryGui(plugin, null, "ショップ", layout);
        gui.setFiller(new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
        //エリアレーダー
        gui.addElement(new StaticGuiElement('1', Items.AreaRadar.getItem().getItem(), 1, click -> {
            buy(player, Items.AreaRadar, freeItems ? 0 : 2);
            click.getGui().draw();
            return true;
        }, Items.AreaRadar.getItem().getDisplayName(), "§7購入に2ポイントが必要です。"));
        //レッドブル
        gui.addElement(new StaticGuiElement('2', Items.Redbull.getItem().getItem(), 1, click -> {
            buy(player, Items.Redbull, freeItems ? 0 : 1);
            click.getGui().draw();
            return true;
        }, Items.Redbull.getItem().getDisplayName(), "§7購入に1ポイントが必要です。"));
        //鬼の金棒
        gui.addElement(new StaticGuiElement('3', Items.KnockbackStick.getItem().getItem(), 1, click -> {
            buy(player, Items.KnockbackStick, freeItems ? 0 : 2);
            click.getGui().draw();
            return true;
        }, Items.KnockbackStick.getItem().getDisplayName(), "§7購入に2ポイントが必要です。"));
        //翼を授ける
        gui.addElement(new StaticGuiElement('4', Items.JumpBoost.getItem().getItem(), 1, click -> {
            buy(player, Items.JumpBoost, freeItems ? 0 : 1);
            click.getGui().draw();
            return true;
        }, Items.JumpBoost.getItem().getDisplayName(), "§7購入に1ポイントが必要です。"));
        //バッテリーパック
        gui.addElement(new StaticGuiElement('5', Items.BatteryPack.getItem().getItem(), 1, click -> {
            buy(player, Items.BatteryPack, freeItems ? 0 : 2);
            click.getGui().draw();
            return true;
        }, Items.BatteryPack.getItem().getDisplayName(), "§7購入に2ポイントが必要です。"));
        //ポイント数確認用
        gui.addElement(new DynamicGuiElement('0', (viewer) -> new StaticGuiElement('0', new ItemStack (Material.DIAMOND),
                click -> {
                    click.getGui().draw();
                    return true;
                },
                "ポイント: " + playerPoints.getOrDefault(player.getName(), 0))));
        gui.show(player);
    }

    private static void buy(Player player, Items items, int pointRequires) {
        if (playerPoints.containsKey(player.getName())) {
            if (playerPoints.get(player.getName()) >= pointRequires) {
                player.getInventory().addItem(items.getItem().getItem());
                playerPoints.replace(player.getName(), playerPoints.get(player.getName()) - pointRequires);
                player.sendMessage(prefixes(messageConfig.getString("shop.bought", "null").replaceAll("%item%", items.getItem().getDisplayName())));
            } else {
                player.sendMessage(prefixes(messageConfig.getString("shop.failed", "null").replaceAll("%item%", items.getItem().getDisplayName()).replaceAll("%point%", String.valueOf(pointRequires))));
            }
        }
    }
}
