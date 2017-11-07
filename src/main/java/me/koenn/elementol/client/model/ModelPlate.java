package me.koenn.elementol.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPlate extends ModelBase {

    private ModelRenderer renderer;

    public ModelPlate() {

    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.renderer = new ModelRenderer(this, 2, 2);

        this.renderer.addBox(18, 8, 3, 10, 2, 10);
        this.renderer.addBox(16, 4, 7, 3, 1, 2);
        this.renderer.addBox(18, 5, 7, 3, 1, 2);
        this.renderer.addBox(20, 6, 7, 3, 1, 2);
        this.renderer.addBox(22, 7, 7, 3, 1, 2);

        this.renderer.render(scale);
    }
}
