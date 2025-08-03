package akki697222.hideandseek.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static akki697222.hideandseek.Hideandseek.logger;

public abstract class CommandBase implements CommandExecutor, TabCompleter {
    private List<SubCommand> subCommands;

    protected void setSubCommands(List<SubCommand> subCommands) {
        this.subCommands = subCommands;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length >= 1) {
            subCommands.forEach(subCommand -> {
                if (subCommand.getName().equals(args[0])) {
                    subCommand.onSubCommand(sender, Arrays.copyOfRange(args, 1, args.length));
                }
            });
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return subCommands != null ? subCommands.stream().map(SubCommand::getName).toList() : null;
        }

        if (args.length >= 2) {
            for (SubCommand subCommand : subCommands) {
                if (subCommand.getName().equals(args[0])) {
                    return subCommand.onTabComplete(commandSender, Arrays.copyOfRange(args, 1, args.length));
                }
            }
        }

        return null;
    }
}
