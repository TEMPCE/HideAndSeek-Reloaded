package com.tempce.hideandseek.commands.has;

import com.tempce.hideandseek.Hideandseek;
import com.tempce.hideandseek.commands.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

import static com.tempce.hideandseek.Hideandseek.*;

public class ReloadSC implements SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public void onSubCommand(CommandSender sender, String[] args) {
        sender.sendMessage("Reloading Configs...");
        plugin.reloadConfig();
        sender.sendMessage("Reloading Events...");
        events.clear();
        loadEvents();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}
