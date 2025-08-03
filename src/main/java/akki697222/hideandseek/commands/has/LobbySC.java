package akki697222.hideandseek.commands.has;

import akki697222.hideandseek.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static akki697222.hideandseek.Hideandseek.settings;

public class LobbySC implements SubCommand {
    @Override
    public String getName() {
        return "lobby";
    }

    @Override
    public void onSubCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            settings.setLobbyLocation(player.getLocation());
        } else {
            sender.sendMessage("§cこの設定はコンソールから変更できません。");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}
