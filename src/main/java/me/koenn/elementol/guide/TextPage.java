package me.koenn.elementol.guide;

import me.koenn.elementol.gui.GuiGuide;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderItem;

public class TextPage extends Page {

    private final String text;

    public TextPage(String title, String text) {
        super(title);
        this.text = text;
    }

    @Override
    protected void draw(GuiGuide gui, FontRenderer fontRenderer, RenderItem itemRenderer, int xOffset, int yOffset) {
        fontRenderer.drawSplitString(this.text, xOffset + 36, yOffset + 30, 116, 0);
    }
}
