package com.tempce.hideandseek.core.event;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tempce.hideandseek.core.event.types.*;
import de.tr7zw.nbtapi.NBT;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                JsonArray effectArray = eventObject.getAsJsonArray("effect");
                List<PotionEffect> effects = new ArrayList<>();
                for (JsonElement element : effectArray) {
                    if (element.isJsonObject()) {
                        JsonObject effect = element.getAsJsonObject();
                        String effectType = effect.get("type").getAsString();
                        int duration = effect.get("duration").getAsInt();
                        int amplifier = effect.get("amplifier").getAsInt();

                        boolean ambient = effect.has("ambient") && effect.get("ambient").getAsBoolean();
                        boolean particles = effect.has("particles") && effect.get("particles").getAsBoolean();

                        effects.add(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(effectType.toLowerCase())), duration, amplifier, ambient, particles));
                    }
                }

                return new EffectEvent(effects);
            }
            case "entity_player" -> {
                JsonObject entityPlayer = eventObject.getAsJsonObject("entity_player");

                String entity = entityPlayer.get("entity").getAsString();
                int amount = entityPlayer.get("amount").getAsInt();
                String nbt = entityPlayer.get("nbt").getAsJsonObject().toString();
                JsonArray targetArray = entityPlayer.getAsJsonArray("target_player");
                List<String> targetPlayers = new ArrayList<>();
                for (JsonElement el : targetArray) {
                    targetPlayers.add(el.getAsString());
                }

                return new EntityEvent.PlayersEntityEvent(
                        entity,
                        nbt,
                        amount,
                        targetPlayers
                );
            }
            case "give" -> {
                JsonArray give = eventObject.getAsJsonArray("give");
                List<ItemStack> items = new ArrayList<>();
                for (JsonElement element : give) {
                    if (element.isJsonObject()) {
                        JsonObject itemObject = element.getAsJsonObject();
                        String materialName = itemObject.get("material").getAsString();
                        String nbtString = itemObject.get("nbt").getAsJsonObject().toString();
                        int amount = itemObject.get("amount").getAsInt();
                        Material material = Material.getMaterial(materialName);
                        if (material == null) {
                            throw new IllegalStateException("Invalid Material '" + materialName + "'");
                        }
                        ItemStack stack = new ItemStack(material, amount);
                        NBT.modify(stack, nbt -> {
                            nbt.mergeCompound(NBT.parseNBT(nbtString));
                        });
                        items.add(stack);
                    }
                }

                return new GiveEvent(items);
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
