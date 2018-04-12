package me.koenn.elementol.guide;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Chapter {

    private final String name;
    private final String id;
    private final ItemStack icon;

    public Chapter(String name, String id, ItemStack icon) {
        this.name = name;
        this.id = id;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int drawChapterButton(FontRenderer fontRenderer, RenderItem itemRenderer, int x, int y) {
        int xOffset = (146 / 4);
        int yOffset = 16;
        int width = fontRenderer.getStringWidth(this.name);

        itemRenderer.renderItemIntoGUI(this.icon, x + xOffset - 8, y);
        fontRenderer.drawString(this.name, x + xOffset - (width / 2), y + yOffset, 0);

        return (xOffset * 2) - 8 + 5;
    }

    @SideOnly(Side.CLIENT)
    public static class ChapterButton extends GuiButton {

        private final String name;

        public ChapterButton(int parButtonId, int parPosX, int parPosY, int width, int height, String name) {
            super(parButtonId, parPosX, parPosY, width, height, "");
            this.name = name;
        }

        @Override
        public void drawButton(Minecraft mc, int parX, int parY, float partialTicks) {

        }

        public String getName() {
            return name;
        }
    }
}
