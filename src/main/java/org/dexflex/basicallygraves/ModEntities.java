package org.dexflex.basicallygraves;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.dexflex.basicallygraves.entity.GraveEntity;

public class ModEntities {
    public static EntityType<GraveEntity> GRAVE_ENTITY;

    public static void register() {
        GRAVE_ENTITY = Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of("basicallygraves", "grave"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, GraveEntity::new)
                        .dimensions(EntityDimensions.fixed(0.6F, 0.9F))
                        .build()
        );

        FabricDefaultAttributeRegistry.register(GRAVE_ENTITY, GraveEntity.createGraveAttributes());
    }
}
