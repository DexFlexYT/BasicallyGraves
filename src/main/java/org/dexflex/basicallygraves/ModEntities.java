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

    public static final EntityType<GraveEntity> GRAVE_ENTITY =
            Registry.register(Registries.ENTITY_TYPE, new Identifier("basicallygraves", "grave"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, GraveEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f, 0.9f))
                    .build()
    );

    public static void register() {
        FabricDefaultAttributeRegistry.register(
                GRAVE_ENTITY,
                GraveEntity.createGraveAttributes()
        );
    }
}