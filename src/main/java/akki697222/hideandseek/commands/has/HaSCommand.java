package akki697222.hideandseek.commands.has;

import akki697222.hideandseek.commands.CommandBase;
import akki697222.hideandseek.commands.SubCommand;

import java.util.ArrayList;
import java.util.List;

public class HaSCommand extends CommandBase {
    public HaSCommand() {
        List<SubCommand> subCommands = new ArrayList<>();
        subCommands.add(new StartSC());
        subCommands.add(new LobbySC());
        subCommands.add(new VerSC());
        subCommands.add(new StopSC());
        setSubCommands(subCommands);
    }
}
