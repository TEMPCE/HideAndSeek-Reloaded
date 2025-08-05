package com.tempce.hideandseek.commands.map;

import com.tempce.hideandseek.core.map.GameMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HiderSpawnESC implements EditorSubCommand {
    @Override
    public void onSubCommand(CommandSender sender, String[] args, String editorMapName) {
        GameMap gameMap = EditorSC.getNormalAndTempMapsFromName(editorMapName);
        if (gameMap != null) {
            if (sender instanceof Player player) {
                gameMap.setHiderSpawn(player.getLocation());
                sender.sendMessage("マップ '" + editorMapName + "' の逃げのスポーンを '" + player.getLocation() + "' に設定しました。");
            } else {
                sender.sendMessage("§cこの設定はコンソールから編集できません。");
            }
        } else {
            sender.sendMessage("§cマップ '" + editorMapName + "' は存在しません。");
        }
    }

    @Override
    public String getName() {
        return "hiderSpawn";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}
