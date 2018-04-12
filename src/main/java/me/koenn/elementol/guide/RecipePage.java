package me.koenn.elementol.guide;

import me.koenn.elementol.Elementol;
import me.koenn.elementol.gui.GuiGuide;
import me.koenn.elementol.helper.RecipeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RecipePage extends Page {

    private static final ResourceLocation CRAFTING_GRID = new ResourceLocation(Elementol.MOD_ID + ":textures/gui/crafting.png");
    private static final ResourceLocation SMELTING_GRID = new ResourceLocation(Elementol.MOD_ID + ":textures/gui/smelting.png");
    private static final ResourceLocation BINDING_GRID = new ResourceLocation(Elementol.MOD_ID + ":textures/gui/binding.png");

    private final String description;
    private final Item item;
    private final RecipeType recipeType;

    public RecipePage(Item item, String description, RecipeType recipeType) {
        super(item.getItemStackDisplayName(new ItemStack(item)));
        this.description = description;
        this.item = item;
        this.recipeType = recipeType;
    }

    @Override
    protected void draw(GuiGuide gui, FontRenderer fontRenderer, RenderItem itemRenderer, int xOffset, int yOffset) {
        switch (recipeType) {
            case CRAFTING:
                Minecraft.getMinecraft().getTextureManager().bindTexture(CRAFTING_GRID);
                break;
            case SMELTING:
                Minecraft.getMinecraft().getTextureManager().bindTexture(SMELTING_GRID);
                break;
            case BINDING:
                Minecraft.getMinecraft().getTextureManager().bindTexture(BINDING_GRID);
                break;
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        gui.drawTexturedModalRect(xOffset + 36, yOffset + 30, 0, 0, 119, 90);

        int x = xOffset + 38;
        int y = yOffset + 41;
        int offset = yOffset + 102;

        fontRenderer.drawSplitString(description, xOffset + 36, offset, 116, 0);

        GlStateManager.pushAttrib();
        GlStateManager.disableDepth();
        RenderHelper.enableGUIStandardItemLighting();

        switch (recipeType) {
            case CRAFTING:
                RecipeHelper.renderCraftingRecipe(itemRenderer, this.item, x, y);
                break;
            case SMELTING:
                RecipeHelper.renderSmeltingRecipe(itemRenderer, this.item, x, y);
                break;
            case BINDING:
                RecipeHelper.renderBindingRecipe(itemRenderer, this.item, x, y);
                break;
        }

        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableDepth();
        GlStateManager.popAttrib();
    }

    public enum RecipeType {
        CRAFTING, SMELTING, BINDING
    }
}
