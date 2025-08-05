package com.tempce.hideandseek.core.map;

import com.tempce.hideandseek.core.Mode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import pl.mikigal.config.Config;
import pl.mikigal.config.annotation.ConfigName;

import java.util.List;

@ConfigName("maps.yml")
public interface Maps extends Config {
    default List<GameMap> getMaps() {
        return List.of(new GameMap(
                "empty",
                "",
                "",
                "EMPTY",
                Mode.Normal,
                new Location(Bukkit.getWorld("world"), 0, 0, 0),
                new Location(Bukkit.getWorld("world"), 0, 0, 0),
                new Location(Bukkit.getWorld("world"), 0, 0, 0),
                List.of("")
        ));
    }

    void setMaps(List<GameMap> maps);
}
