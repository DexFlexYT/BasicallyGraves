package org.dexflex.basicallygraves;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.dexflex.basicallygraves.client.ModModelLayers;
import org.dexflex.basicallygraves.entity.GraveEntityModel;
import org.dexflex.basicallygraves.entity.GraveEntityRenderer;

public class BasicallyGravesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.GRAVE, GraveEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.GRAVE_ENTITY, GraveEntityRenderer::new);
    }
}