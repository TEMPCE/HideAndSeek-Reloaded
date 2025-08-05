package com.tempce.hideandseek.commands.has;

import com.tempce.hideandseek.commands.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

import static com.tempce.hideandseek.Hideandseek.plugin;

public class ReloadSC implements SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public void onSubCommand(CommandSender sender, String[] args) {
        sender.sendMessage("Reloading...");
        plugin.reloadConfig();
        sender.sendMessage("Reloaded");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}
