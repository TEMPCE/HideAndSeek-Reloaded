package com.tempce.hideandseek.commands.has;

import com.tempce.hideandseek.commands.CommandBase;
import com.tempce.hideandseek.commands.SubCommand;

import java.util.ArrayList;
import java.util.List;

public class HaSCommand extends CommandBase {
    public HaSCommand() {
        List<SubCommand> subCommands = new ArrayList<>();
        subCommands.add(new StartSC());
        subCommands.add(new LobbySC());
        subCommands.add(new VerSC());
        subCommands.add(new StopSC());
        subCommands.add(new ReloadSC());
        setSubCommands(subCommands);
    }
}
