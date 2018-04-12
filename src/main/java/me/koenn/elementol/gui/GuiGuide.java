package me.koenn.elementol.gui;

import me.koenn.elementol.Elementol;
import me.koenn.elementol.guide.Chapter;
import me.koenn.elementol.guide.GuideLoader;
import me.koenn.elementol.guide.ListPage;
import me.koenn.elementol.guide.Page;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Stack;

public class GuiGuide extends GuiScreen {

    public static final int BOOK_WIDTH = 192;
    public static final int BOOK_HEIGHT = 192;
    private static final ResourceLocation BOOK_TEXTURE = new ResourceLocation(Elementol.MOD_ID + ":textures/gui/elemental_guide.png");
    public final Stack<Page[]> history;
    public int currentPage;
    private Page[] openPages;
    private NextPageButton buttonNextPage;
    private NextPageButton buttonPreviousPage;
    private BackButton buttonBack;

    public GuiGuide() {
        this.openPages = new Page[16];
        this.history = new Stack<>();
    }

    private void reloadButtons() {
        int xOffset = (width - BOOK_WIDTH) / 2;
        int yOffset = (this.height / 2) - (BOOK_HEIGHT / 2) - 20;

        this.buttonNextPage = new NextPageButton(1, xOffset + 120, yOffset + 156, true);
        this.buttonPreviousPage = new NextPageButton(2, xOffset + 38, yOffset + 156, false);
        this.buttonBack = new BackButton(3, xOffset + 80, yOffset + 158);

        this.buttonList.clear();
        this.buttonList.add(this.buttonNextPage);
        this.buttonList.add(this.buttonPreviousPage);
        this.buttonList.add(this.buttonBack);

        this.openPages[this.currentPage].init(this.buttonList, 4, xOffset, yOffset);
    }

    private int getPageCount() {
        int count = 0;
        for (Page page : this.openPages) {
            if (page != null) {
                count++;
            } else {
                return count;
            }
        }
        return count;
    }

    @Override
    public void initGui() {
        this.openPages[0] = GuideLoader.INSTANCE.getMainPage();

        this.reloadButtons();
    }

    @Override
    public void updateScreen() {
        this.buttonNextPage.visible = (this.currentPage < this.getPageCount() - 1);
        this.buttonPreviousPage.visible = this.currentPage > 0;
        this.buttonBack.visible = !this.history.isEmpty();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(BOOK_TEXTURE);

        int xOffset = (width - BOOK_WIDTH) / 2;
        int yOffset = (this.height / 2) - (BOOK_HEIGHT / 2) - 20;
        drawTexturedModalRect(xOffset, yOffset, 0, 0, BOOK_WIDTH, BOOK_HEIGHT);
        this.openPages[this.currentPage].drawPage(this, fontRenderer, itemRender, xOffset, yOffset);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void drawBackground(int tint) {
        super.drawBackground(tint);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        GuideLoader loader = GuideLoader.INSTANCE;
        if (button instanceof Chapter.ChapterButton) {
            this.history.push(this.openPages);
            this.currentPage = 0;
            String destination = ((ListPage) this.openPages[this.currentPage]).getDestination(((Chapter.ChapterButton) button));
            this.openPages = loader.parsePages(loader.parseFile("assets/elementol/guide/" + destination + ".json"));
            this.reloadButtons();
        } else if (button == this.buttonNextPage) {
            if (this.currentPage < this.getPageCount() - 1) {
                ++this.currentPage;
                this.reloadButtons();
            }
        } else if (button == this.buttonPreviousPage) {
            if (this.currentPage > 0) {
                --this.currentPage;
                this.reloadButtons();
            }
        } else if (button == buttonBack) {
            if (!this.history.isEmpty()) {
                this.openPages = this.history.pop();
                this.currentPage = 0;
                this.reloadButtons();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    static class NextPageButton extends GuiButton {

        private final boolean isNextButton;

        public NextPageButton(int parButtonId, int parPosX, int parPosY, boolean parIsNextButton) {
            super(parButtonId, parPosX, parPosY, 23, 13, "");
            isNextButton = parIsNextButton;
        }

        @Override
        public void drawButton(Minecraft mc, int parX, int parY, float partialTicks) {
            if (visible) {
                boolean isButtonPressed = parX >= this.x && parY >= this.y && parX < this.x + width && parY < this.y + height;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(BOOK_TEXTURE);
                int textureX = 0;
                int textureY = 192;

                if (isButtonPressed) {
                    textureX += 23;
                }

                if (!isNextButton) {
                    textureY += 13;
                }

                drawTexturedModalRect(this.x, this.y, textureX, textureY, 23, 13);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    static class BackButton extends GuiButton {

        public BackButton(int parButtonId, int parPosX, int parPosY) {
            super(parButtonId, parPosX, parPosY, 25, 13, "");
        }

        @Override
        public void drawButton(Minecraft mc, int parX, int parY, float partialTicks) {
            if (visible) {
                boolean isButtonPressed = parX >= this.x && parY >= this.y && parX < this.x + this.width && parY < this.y + this.height;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(BOOK_TEXTURE);
                int textureX = 49;
                int textureY = 193;

                if (isButtonPressed) {
                    textureY += 14;
                }

                drawTexturedModalRect(this.x, this.y, textureX, textureY, 23, 13);
            }
        }
    }
}
