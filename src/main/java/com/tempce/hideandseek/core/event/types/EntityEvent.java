package com.tempce.hideandseek.core.event.types;

import de.tr7zw.nbtapi.NBT;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;

import static com.tempce.hideandseek.Hideandseek.logger;

public abstract class EntityEvent extends Event {
    protected final String entityId;
    protected final String entityNbt;
    protected final int amount;

    public EntityEvent(String type, String entityId, String entityNbt, int amount) {
        super(type);
        this.entityId = entityId;
        this.entityNbt = entityNbt;
        this.amount = amount;
    }

    @Override
    public abstract void apply(Player player);

    public String getEntityId() {
        return entityId;
    }

    public String getEntityNbt() {
        return entityNbt;
    }

    public int getAmount() {
        return amount;
    }

    public static final class PlayersEntityEvent extends EntityEvent {
        private final List<String> targetPlayerNames;

        public PlayersEntityEvent(String entityId, String entityNbt, int amount, List<String> targetPlayerNames) {
            super("entity_player", entityId, entityNbt, amount);
            this.targetPlayerNames = targetPlayerNames;
        }

        @Override
        public void apply(Player ignored) {
            for (String targetPlayerName : targetPlayerNames) {
                Player player = Bukkit.getPlayer(targetPlayerName);
                if (player != null) {
                    World world = player.getWorld();
                    EntityType entityType = EntityType.fromName(entityId);
                    if (entityType == null) {
                        logger.error("Failed to spawn entity (id: {})", entityId);
                        return;
                    }
                    for (int i = 0; i < amount; i++) {
                        Entity entity = world.spawnEntity(player.getLocation(), entityType);
                        NBT.modify(entity, nbt -> {
                            nbt.mergeCompound(NBT.parseNBT(entityNbt));
                        });
                    }
                }
            }
        }
    }
}
