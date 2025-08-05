package com.tempce.hideandseek.core.map;

import com.tempce.hideandseek.core.Mode;
import org.bukkit.Location;

import java.io.Serializable;
import java.util.List;

public class GameMap implements Serializable {
    private String name;
    private String description;
    private String credits;
    private String title;
    private Location hiderSpawn;
    private Location seekerSpawn;
    private Location spectatorSpawn;
    private List<String> executableCommands;

    public GameMap() {}

    public GameMap(String name, String description, String credits, String title,
                   Mode mode, Location hiderSpawn, Location seekerSpawn,
                   Location spectatorSpawn, List<String> executableCommands) {
        this.name = name;
        this.description = description;
        this.credits = credits;
        this.title = title;
        this.hiderSpawn = hiderSpawn;
        this.seekerSpawn = seekerSpawn;
        this.spectatorSpawn = spectatorSpawn;
        this.executableCommands = executableCommands;
    }

    public List<String> getExecutableCommands() {
        return executableCommands;
    }

    public void setExecutableCommands(List<String> executableCommands) {
        this.executableCommands = executableCommands;
    }

    public Location getSpectatorSpawn() {
        return spectatorSpawn;
    }

    public void setSpectatorSpawn(Location spectatorSpawn) {
        this.spectatorSpawn = spectatorSpawn;
    }

    public Location getSeekerSpawn() {
        return seekerSpawn;
    }

    public void setSeekerSpawn(Location seekerSpawn) {
        this.seekerSpawn = seekerSpawn;
    }

    public Location getHiderSpawn() {
        return hiderSpawn;
    }

    public void setHiderSpawn(Location hiderSpawn) {
        this.hiderSpawn = hiderSpawn;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
