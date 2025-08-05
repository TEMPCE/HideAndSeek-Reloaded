package com.tempce.hideandseek.commands.map;

import com.tempce.hideandseek.core.map.GameMap;
import org.bukkit.command.CommandSender;

import java.util.List;

import static com.tempce.hideandseek.Hideandseek.mapsConfig;

public class SaveESC implements EditorSubCommand {
    @Override
    public void onSubCommand(CommandSender sender, String[] args, String editorMapName) {
        GameMap gameMap = EditorSC.getNormalAndTempMapsFromName(editorMapName);
        if (gameMap != null) {
            StringBuilder sb = new StringBuilder();
            if (gameMap.getTitle() == null) sb.append("タイトル,");
            if (gameMap.getHiderSpawn() == null) sb.append("逃げのスポーン,");
            if (gameMap.getSeekerSpawn() == null) sb.append("鬼のスポーン,");
            if (gameMap.getSpectatorSpawn() == null) sb.append("観戦者のスポーン,");
            if (gameMap.getCredits() == null) gameMap.setCredits("");
            if (gameMap.getExecutableCommands() == null) gameMap.setExecutableCommands(List.of(""));
            if (gameMap.getDescription() == null) gameMap.setDescription("");
            if (sb.isEmpty()) {
                sender.sendMessage("マップ '" + editorMapName + "' をセーブしました！");
                List<GameMap> maps = mapsConfig.getMaps();
                GameMap dup = null;
                for (GameMap map : maps) {
                    if (map.getName().equals(gameMap.getName())) {
                        dup = map;
                    }
                }
                maps.remove(dup);
                maps.add(gameMap);
                mapsConfig.setMaps(maps);
                EditorSC.removeTempMap(editorMapName);
            } else {
                sender.sendMessage("§cマップ '" + editorMapName + "' をセーブするのに必要な設定が行われていません: " + sb);
            }
        } else {
            sender.sendMessage("§cマップ '" + editorMapName + "' は存在しません。");
        }
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}
