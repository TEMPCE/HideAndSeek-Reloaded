package akki697222.hideandseek.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {
    String getName();

    void onSubCommand(CommandSender sender, String[] args);

    List<String> onTabComplete(CommandSender sender, String[] args);
}
