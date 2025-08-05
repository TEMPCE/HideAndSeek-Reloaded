package com.tempce.hideandseek.core.event.types;

import org.bukkit.entity.Player;

public abstract class Event {
    private final String type;

    public Event(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public abstract void apply(Player player);
}
