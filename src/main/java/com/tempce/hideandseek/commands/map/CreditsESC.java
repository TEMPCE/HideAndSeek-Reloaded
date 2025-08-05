package com.tempce.hideandseek.commands.map;

import com.tempce.hideandseek.core.map.GameMap;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CreditsESC implements EditorSubCommand {
    @Override
    public void onSubCommand(CommandSender sender, String[] args, String editorMapName) {
        if (args.length >= 1) {
            GameMap gameMap = EditorSC.getNormalAndTempMapsFromName(editorMapName);
            if (gameMap != null) {
                gameMap.setCredits(args[0]);
                sender.sendMessage("マップ '" + editorMapName + "' のクレジットを '" + args[0] + "' に設定しました。");
            } else {
                sender.sendMessage("§cマップ '" + editorMapName + "' は存在しません。");
            }
        }
    }

    @Override
    public String getName() {
        return "credits";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}
