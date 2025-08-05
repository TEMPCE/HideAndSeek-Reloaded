package com.tempce.hideandseek.core.event;

import com.tempce.hideandseek.core.Mode;
import com.tempce.hideandseek.core.event.types.CustomEvent;
import com.tempce.hideandseek.core.event.types.Event;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.tempce.hideandseek.core.game.GameMaster.scoreboard;

public class GameEvent {
    private final String id;
    private final String name;
    private final String desc;
    private final Mode mode;
    private final List<String> forTeams;
    private final Event event;

    public GameEvent(String id, String name, String desc, Mode mode, List<String> forTeams, Event event) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.mode = mode;
        this.forTeams = forTeams;
        this.event = event;
    }

    public GameEvent(String id, String name, String desc, String mode, List<String> forTeams, Event event) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.mode = Mode.valueOf(mode);
        this.forTeams = forTeams;
        this.event = event;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Mode getMode() {
        return mode;
    }

    public List<String> getForTeams() {
        return forTeams;
    }

    public Event getEvent() {
        return event;
    }

    public void applyEvent() {
        if (event instanceof CustomEvent) {
            event.apply(null);
        } else {
            List<Player> players = new ArrayList<>();
            for (String teamName : forTeams) {
                Team team = Objects.requireNonNull(scoreboard.getTeam(teamName));
                players.addAll(
                        team.getEntries().stream().map(Bukkit::getPlayer).toList()
                );
            }
            for (Player player : players) {
                event.apply(player);
            }
        }
    }
}
