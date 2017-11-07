package me.koenn.elementol.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelConnector extends ModelBase {

    private final ModelRenderer renderer;

    public ModelConnector() {
        this.renderer = new ModelRenderer(this, 2, 1);

        this.renderer.addBox(4, 11, 4, 1, 4, 1);
        this.renderer.addBox(11, 11, 11, 1, 4, 1);
        this.renderer.addBox(4, 11, 11, 1, 4, 1);
        this.renderer.addBox(11, 11, 4, 1, 4, 1);

        this.renderer.addBox(5, 15, 5, 1, 3, 1);
        this.renderer.addBox(10, 15, 10, 1, 3, 1);
        this.renderer.addBox(5, 15, 10, 1, 3, 1);
        this.renderer.addBox(10, 15, 5, 1, 3, 1);

        this.renderer.addBox(6, 18, 6, 1, 2, 1);
        this.renderer.addBox(9, 18, 9, 1, 2, 1);
        this.renderer.addBox(6, 18, 9, 1, 2, 1);
        this.renderer.addBox(9, 18, 6, 1, 2, 1);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.renderer.render(scale);
    }
}
