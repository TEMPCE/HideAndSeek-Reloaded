package com.tempce.hideandseek.commands.has;

import com.tempce.hideandseek.commands.SubCommand;
import com.tempce.hideandseek.core.map.GameMap;
import com.tempce.hideandseek.core.Mode;
import com.tempce.hideandseek.core.game.GameMaster;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tempce.hideandseek.Hideandseek.mapsConfig;

public class StartSC implements SubCommand {
    @Override
    public String getName() {
        return "start";
    }

    @Override
    public void onSubCommand(CommandSender sender, String[] args) {
        int value = args.length > 0 ? Integer.parseInt(args[0]) : 1;
        Mode mode = args.length > 1 ? Mode.valueOf(args[1]) : Mode.Normal;
        String map = args.length > 2 ? args[2] : null;

        GameMaster.init(value, mode, map);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 2) {
            return Arrays.stream(Mode.values()).map(Enum::name).toList();
        } else if (args.length == 3) {
            List<GameMap> mapList = new ArrayList<>(mapsConfig.getMaps());
            List<String> mapNames = new ArrayList<>();
            for (GameMap gameMap : mapList) {
                mapNames.add(gameMap.getName());
            }
            return mapNames;
        }

        return List.of();
    }
}
