package com.tempce.hideandseek.commands;

import static com.tempce.hideandseek.Hideandseek.*;
import com.tempce.hideandseek.core.event.GameEvent;
import com.tempce.hideandseek.core.game.GameMaster;
import com.tempce.hideandseek.core.game.GameState;
import com.tempce.hideandseek.core.map.GameMap;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class EventCommand extends CommandBase {
    public EventCommand() {
        List<SubCommand> subCommands = new ArrayList<>();
        subCommands.add(new Fire());
        setSubCommands(subCommands);
    }

    public static final class Fire implements SubCommand {

        @Override
        public String getName() {
            return "fire";
        }

        @Override
        public void onSubCommand(CommandSender sender, String[] args) {
            if (args.length == 0) {
                sender.sendMessage("イベントを指定してください！");
                return;
            }
            if (GameMaster.gameState != GameState.STARTED) {
                sender.sendMessage("イベントはゲーム中にのみ発生させることができます。");
                return;
            }
            GameEvent ev = null;
            for (GameEvent event : events) {
                if (event.getId().equals(args[0])) {
                    ev = event;
                    break;
                }
            }
            if (ev == null) {
                sender.sendMessage("イベント '" + args[0] + "'は存在しません。");
                return;
            }
            ev.applyEvent();
        }

        @Override
        public List<String> onTabComplete(CommandSender sender, String[] args) {
            return events.stream().map(GameEvent::getId).toList();
        }
    }
}
