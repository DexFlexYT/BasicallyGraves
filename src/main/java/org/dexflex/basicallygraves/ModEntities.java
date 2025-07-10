package org.dexflex.basicallygraves;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.dexflex.basicallygraves.entity.GraveEntity;

public class ModEntities {
    public static EntityType<GraveEntity> GRAVE_ENTITY;

    public static void register() {
        GRAVE_ENTITY = Registry.register(
                Registry.ENTITY_TYPE,
                new Identifier("basicallygraves", "grave"),
                EntityType.Builder.create(GraveEntity::new, SpawnGroup.MISC)
                        .setDimensions(0.6F, 0.9F)
                        .build("grave")
        );
        FabricDefaultAttributeRegistry.register(GRAVE_ENTITY, GraveEntity.createGraveAttributes());
    }
}
