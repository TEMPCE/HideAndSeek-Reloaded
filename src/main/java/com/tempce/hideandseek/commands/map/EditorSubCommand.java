package com.tempce.hideandseek.commands.map;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface EditorSubCommand {
    void onSubCommand(CommandSender sender, String[] args, String editorMapName);
    String getName();
    List<String> onTabComplete(CommandSender sender, String[] args);
}
