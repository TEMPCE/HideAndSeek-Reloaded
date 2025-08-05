package com.tempce.hideandseek.commands.map;

import com.tempce.hideandseek.commands.SubCommand;
import com.tempce.hideandseek.core.map.GameMap;
import org.bukkit.command.CommandSender;

import java.util.*;

import static com.tempce.hideandseek.Hideandseek.mapsConfig;

public class EditorSC implements SubCommand {
    private final List<EditorSubCommand> subCommands = new ArrayList<>();
    public static List<GameMap> tempMaps = new ArrayList<>();

    public EditorSC() {
        subCommands.add(new AddESC());
        subCommands.add(new DescriptionESC());
        subCommands.add(new CreditsESC());
        subCommands.add(new TitleESC());
        subCommands.add(new HiderSpawnESC());
        subCommands.add(new SeekerSpawnESC());
        subCommands.add(new SpectatorSpawnESC());
        subCommands.add(new SaveESC());
    }

    public static GameMap getNormalAndTempMapsFromName(String name) {
        for (GameMap map : tempMaps) {
            if (map.getName().equals(name)) {
                return map;
            }
        }
        for (GameMap map : mapsConfig.getMaps()) {
            if (map.getName().equals(name)) {
                return map;
            }
        }
        return null;
    }

    public static void removeTempMap(String name) {
        GameMap map_ = null;
        for (GameMap map : tempMaps) {
            if (map.getName().equals(name)) {
                map_ = map;
            }
        }
        if (map_ != null) {
            tempMaps.remove(map_);
        }
    }

    @Override
    public String getName() {
        return "editor";
    }

    @Override
    public void onSubCommand(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            subCommands.forEach(subCommand -> {
                if (subCommand.getName().equals(args[1])) {
                    subCommand.onSubCommand(sender, Arrays.copyOfRange(args, 2, args.length), args[0]);
                }
            });
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return mapsConfig.getMaps().stream().map(GameMap::getName).toList();
        } else if (args.length == 2) {
            return subCommands.stream().map(EditorSubCommand::getName).toList();
        } else if (args.length > 2) {
            for (EditorSubCommand subCommand : subCommands) {
                if (subCommand.getName().equals(args[1])) {
                    return subCommand.onTabComplete(sender, Arrays.copyOfRange(args, 2, args.length));
                }
            }
        }

        return List.of();
    }
}
