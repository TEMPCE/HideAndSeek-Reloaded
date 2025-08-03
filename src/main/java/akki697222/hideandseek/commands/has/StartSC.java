package akki697222.hideandseek.commands.has;

import akki697222.hideandseek.commands.SubCommand;
import akki697222.hideandseek.core.Mode;
import akki697222.hideandseek.game.GameMaster;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class StartSC implements SubCommand {
    @Override
    public String getName() {
        return "start";
    }

    @Override
    public void onSubCommand(CommandSender sender, String[] args) {
        int value = args.length > 0 ? Integer.parseInt(args[0]) : 1;
        Mode mode = args.length > 1 ? Mode.valueOf(args[1]) : Mode.Normal;

        GameMaster.init(value, mode);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            return Arrays.stream(Mode.values()).map(Enum::name).toList();
        }

        return List.of();
    }
}
