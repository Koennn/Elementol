package me.koenn.elementol.client.tesr;

import me.koenn.elementol.client.model.ModelConnector;
import me.koenn.elementol.tileentities.TileEntityEnergizer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;

public class TESREnergizer extends TileEntitySpecialRenderer<TileEntityEnergizer> {

    private static final ResourceLocation GOLD_BLOCK = new ResourceLocation("textures/blocks/gold_block.png");
    private static final ModelConnector CONNECTOR = new ModelConnector();
    private static final float SCALE = 0.0625F;
    private static final float COLOR = 0.35F;

    @Override
    public void render(TileEntityEnergizer te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (te.hasPylon()) {
            GlStateManager.pushMatrix();

            GlStateManager.translate((float) x, (float) y, (float) z);
            GlStateManager.scale(SCALE, SCALE, SCALE);
            GlStateManager.color(COLOR, COLOR, COLOR);
            this.bindTexture(GOLD_BLOCK);

            CONNECTOR.render(null, 0, 0, 0, 0, 0, 1.0F);

            GlStateManager.popMatrix();
        }

        ItemStack stack = te.inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
            GlStateManager.enableBlend();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5, y + 0.7, z + 0.5);
            GlStateManager.scale(0.8, 0.8, 0.8);
            GlStateManager.rotate((te.getWorld().getTotalWorldTime() + partialTicks) * Math.max(te.progress / 4 > 4 ? 50 : 4, te.progress / 4), 0, 1, 0);

            IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, te.getWorld(), null);
            model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);

            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);

            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
    }
}
