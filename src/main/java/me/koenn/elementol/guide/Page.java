package me.koenn.elementol.guide;

import me.koenn.elementol.gui.GuiGuide;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderItem;
import net.minecraftforge.fml.common.FMLLog;

import java.util.List;

public abstract class Page {

    private final String title;

    protected Page(String title) {
        this.title = title;
    }

    public void drawPage(GuiGuide gui, FontRenderer fontRenderer, RenderItem itemRenderer, int xOffset, int yOffset) {
        if (this.title != null) {
            fontRenderer.drawStringWithShadow(this.title, xOffset + 36, 16 + yOffset, 15054119);
        }

        try {
            this.draw(gui, fontRenderer, itemRenderer, xOffset, yOffset);
        } catch (Exception ex) {
            FMLLog.log.error("Error while drawing page!");
            ex.printStackTrace();
        }
    }

    public void init(List<GuiButton> buttonList, int currentId, int xOffset, int yOffset) {

    }

    protected abstract void draw(GuiGuide gui, FontRenderer fontRenderer, RenderItem itemRenderer, int xOffset, int yOffset);
}
