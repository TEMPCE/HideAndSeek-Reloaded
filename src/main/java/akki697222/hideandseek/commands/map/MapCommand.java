package akki697222.hideandseek.commands.map;

import akki697222.hideandseek.commands.CommandBase;
import akki697222.hideandseek.commands.SubCommand;

import java.util.ArrayList;
import java.util.List;

public class MapCommand extends CommandBase {
    public MapCommand() {
        List<SubCommand> subCommands = new ArrayList<>();

        subCommands.add(new EditorSC());

        setSubCommands(subCommands);
    }
}
