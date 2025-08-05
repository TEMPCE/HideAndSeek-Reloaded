package com.tempce.hideandseek.commands.map;

import com.tempce.hideandseek.commands.CommandBase;
import com.tempce.hideandseek.commands.SubCommand;

import java.util.ArrayList;
import java.util.List;

public class MapCommand extends CommandBase {
    public MapCommand() {
        List<SubCommand> subCommands = new ArrayList<>();

        subCommands.add(new EditorSC());

        setSubCommands(subCommands);
    }
}
