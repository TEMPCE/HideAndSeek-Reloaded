package com.tempce.hideandseek.commands.map;

import com.tempce.hideandseek.core.map.GameMap;
import org.bukkit.command.CommandSender;

import java.util.List;

import static com.tempce.hideandseek.commands.map.EditorSC.tempMaps;

public class AddESC implements EditorSubCommand {
    @Override
    public String getName() {
        return "add";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }

    @Override
    public void onSubCommand(CommandSender sender, String[] args, String editorMapName) {
        GameMap gameMap = new GameMap();
        gameMap.setName(editorMapName);
        tempMaps.add(gameMap);
        sender.sendMessage("マップ '" + editorMapName + "' を作成中のマップとして追加しました。");
    }
}
