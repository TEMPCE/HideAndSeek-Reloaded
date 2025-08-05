package com.tempce.hideandseek.commands.has;

import com.tempce.hideandseek.commands.SubCommand;
import io.papermc.paper.plugin.configuration.PluginMeta;
import org.bukkit.command.CommandSender;

import java.util.List;

import static com.tempce.hideandseek.Hideandseek.plugin;

public class VerSC implements SubCommand {
    @Override
    public String getName() {
        return "ver";
    }

    /**
     * このプラグインを今後編集する方へ
     * <p>
     * PluginMetaはPaperの試験的なAPIであり、今後のバージョンで削除される可能性があります。
     * <p>
     * 以下のPluginMetaの部分で{@link NoClassDefFoundError}が発生してしまった場合はこのメソッドの中身を消してあげてください。
     */
    @Override
    public void onSubCommand(CommandSender sender, String[] args) {
        PluginMeta meta = plugin.getPluginMeta();
        sender.sendMessage("§aHide§8and§cSeek§f §l§6Reloaded§f\nVersion: " + meta.getVersion() + "\nAuthor: " + String.join(", ", meta.getAuthors()));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}
