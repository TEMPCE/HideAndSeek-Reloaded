package akki697222.hideandseek.commands.has;

import akki697222.hideandseek.commands.SubCommand;
import akki697222.hideandseek.core.Mode;
import akki697222.hideandseek.game.GameMaster;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class StopSC implements SubCommand {
    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public void onSubCommand(CommandSender sender, String[] args) {
        GameMaster.end(true, false);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}
