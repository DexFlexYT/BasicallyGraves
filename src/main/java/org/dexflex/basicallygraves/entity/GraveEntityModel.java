package org.dexflex.basicallygraves.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class GraveEntityModel<T extends Entity> extends EntityModel<T> {
    private final ModelPart root;

    public GraveEntityModel(ModelPart root) {
        this.root = root.getChild("grave");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        root.addChild("grave", ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-4.0F, -5.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 21.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float amplitude = 3.0f;
        float frequency = 0.075f;
        root.pivotY = 18.0f + (float) Math.sin(ageInTicks * frequency) * amplitude;
        root.yaw = ageInTicks * 0.05f;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        root.render(matrices, vertices, light, overlay);

    }
}
