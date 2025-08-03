package akki697222.hideandseek.commands.map;

import akki697222.hideandseek.core.GameMap;
import akki697222.hideandseek.game.Game;
import org.bukkit.command.CommandSender;

import java.util.List;

public class DescriptionESC implements EditorSubCommand {
    @Override
    public void onSubCommand(CommandSender sender, String[] args, String editorMapName) {
        if (args.length >= 1) {
            GameMap gameMap = EditorSC.getNormalAndTempMapsFromName(editorMapName);
            if (gameMap != null) {
                gameMap.setDescription(args[0]);
                sender.sendMessage("マップ '" + editorMapName + "' の説明を '" + args[0] + "' に設定しました。");
            } else {
                sender.sendMessage("§cマップ '" + editorMapName + "' は存在しません。");
            }
        }
    }

    @Override
    public String getName() {
        return "desc";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}
