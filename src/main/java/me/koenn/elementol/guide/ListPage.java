package me.koenn.elementol.guide;

import me.koenn.elementol.gui.GuiGuide;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;

import java.util.List;

public class ListPage extends Page {

    private final Chapter[] chapters;

    protected ListPage(String title, Chapter... chapters) {
        super(title);
        this.chapters = chapters;
    }

    @Override
    public void init(List<GuiButton> buttonList, int currentId, int xOffset, int yOffset) {
        int x = xOffset + 21;
        int y = yOffset + 35;
        int width = 146 / 2;
        int height = 16 + 9;

        for (Chapter chapter : this.chapters) {
            buttonList.add(new Chapter.ChapterButton(currentId, x, y, width, height, chapter.getId()));
            currentId++;
            x += width;
            if (x > xOffset + 146) {
                x = xOffset + 21;
                y += 20 + 9;
            }
        }
    }

    public String getDestination(Chapter.ChapterButton button) {
        for (Chapter chapter : this.chapters) {
            if (chapter.getId().equalsIgnoreCase(button.getName())) {
                return chapter.getId();
            }
        }
        throw new IllegalArgumentException("Unknown button \'" + button.getName().toLowerCase() + "\'");
    }

    @Override
    protected void draw(GuiGuide gui, FontRenderer fontRenderer, RenderItem itemRenderer, int xOffset, int yOffset) {
        GlStateManager.pushAttrib();
        GlStateManager.disableDepth();
        RenderHelper.enableGUIStandardItemLighting();

        int x = xOffset + 21;
        int y = yOffset + 35;

        for (Chapter chapter : this.chapters) {
            x += chapter.drawChapterButton(fontRenderer, itemRenderer, x, y);
            if (x > xOffset + 146) {
                x = xOffset + 21;
                y += 20 + fontRenderer.FONT_HEIGHT;
            }
        }

        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableDepth();
        GlStateManager.popAttrib();
    }
}
