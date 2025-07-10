package org.dexflex.basicallygraves;


import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.text.Text;
import net.minecraft.world.GameRules;
import org.dexflex.basicallygraves.entity.GraveEntity;

public class DeathEventHandler {
    public static void register() {
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
        });

        ServerPlayerEvents.ALLOW_DEATH.register((player, damageSource, damageAmount) -> {
            if (!player.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)&& !player.getInventory().isEmpty()) {
                GraveEntity grave = new GraveEntity(ModEntities.GRAVE_ENTITY, player.world);
                grave.setPosition(player.getX(), player.getY(), player.getZ());

                grave.setCustomName(Text.literal(player.getName().getString()));
                grave.setCustomNameVisible(true);

                grave.fillInventoryFromPlayer(player);
                player.world.spawnEntity(grave);

                player.getInventory().clear();
            }
            return true;
        });
    }
}
