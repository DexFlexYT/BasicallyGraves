package org.dexflex.basicallygraves.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import org.dexflex.basicallygraves.client.ModModelLayers;

public class GraveEntityRenderer extends MobEntityRenderer<GraveEntity, GraveEntityModel<GraveEntity>> {
    public GraveEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GraveEntityModel<>(context.getPart(ModModelLayers.GRAVE)), 0.25f);
    }

    @Override
    public Identifier getTexture(GraveEntity entity) {
        return Identifier.of("basicallygraves", "textures/entity/grave.png");
    }
}
