package akki697222.hideandseek.core;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import pl.mikigal.config.Config;
import pl.mikigal.config.annotation.Comment;
import pl.mikigal.config.annotation.ConfigName;

@ConfigName("settings.yml")
public interface Settings extends Config {
    @Comment("In seconds")
    default int getTimeLimit() {
        return 300;
    }

    /**
     * @param timeLimit time limit in tick
     */
    void setTimeLimit(int timeLimit);

    default int getEventCycle() {
        return 1200;
    }

    /**
     * @param eventCycle event cycle in tick
     */
    void setEventCycle(int eventCycle);

    default Location getLobbyLocation() {
        return new Location(Bukkit.getWorld("world"), 0, 0, 0);
    }

    void setLobbyLocation(Location lobbyLocation);

    default int getDefaultPlayerPoints() {
        return 3;
    }

    void setDefaultPlayerPoints(int points);
}
