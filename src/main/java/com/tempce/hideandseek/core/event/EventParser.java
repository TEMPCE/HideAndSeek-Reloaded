package com.tempce.hideandseek.core.event;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tempce.hideandseek.core.event.types.CustomEvent;
import com.tempce.hideandseek.core.event.types.EffectEvent;
import com.tempce.hideandseek.core.event.types.Event;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class EventParser {
    public static GameEvent parseGameEvent(JsonObject jsonObject) {
        String id = jsonObject.get("id").getAsString();
        String name = jsonObject.get("name").getAsString();
        String desc = jsonObject.get("desc").getAsString();
        String mode = jsonObject.get("mode").getAsString();

        List<String> forTeams = new ArrayList<>();
        JsonArray forArray = jsonObject.getAsJsonArray("for");
        for (JsonElement element : forArray) {
            forTeams.add(element.getAsString());
        }

        JsonObject eventObject = jsonObject.getAsJsonObject("event");
        String type = eventObject.get("type").getAsString();

        Event event = parseEvent(type, eventObject);

        return new GameEvent(id, name, desc, mode, forTeams, event);
    }

    public static Event parseEvent(@Nonnull String type, JsonObject eventObject) {
        switch (type) {
            case "effect" -> {
                JsonObject effect = eventObject.getAsJsonObject("effect");
                String effectType = effect.get("type").getAsString();
                int duration = effect.get("duration").getAsInt();
                int amplifier = effect.get("amplifier").getAsInt();

                // オプションのパラメータ（省略可）
                boolean ambient = effect.has("ambient") && effect.get("ambient").getAsBoolean();
                boolean particles = effect.has("particles") && effect.get("particles").getAsBoolean();

                return new EffectEvent(effectType.toUpperCase(), duration, amplifier, ambient, particles);
            }
            case "custom" -> {
                JsonObject custom = eventObject.getAsJsonObject("custom");
                String customType = custom.get("type").getAsString();
                if (customType.equals("class")) {
                    return new CustomEvent.JavaClassEvent(custom.get("class").getAsString());
                } else {
                    throw new IllegalArgumentException("Unsupported custom event type: " + type);
                }
            }
            default -> {
                throw new IllegalArgumentException("Unsupported event type: " + type);
            }
        }
    }
}
