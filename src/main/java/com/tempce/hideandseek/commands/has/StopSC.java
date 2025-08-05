package com.tempce.hideandseek.commands.has;

import com.tempce.hideandseek.commands.SubCommand;
import com.tempce.hideandseek.core.game.GameMaster;
import org.bukkit.command.CommandSender;

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
