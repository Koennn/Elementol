package me.koenn.elementol;

import net.minecraft.util.text.TextFormatting;

public enum Element {

    FIRE(TextFormatting.RED, 16730112, 0, "#FF4800"), WATER(TextFormatting.BLUE, 28159, 1, "#006DFF"), AIR(TextFormatting.YELLOW, 15588471, 2, "#EDDC77"), EARTH(TextFormatting.DARK_GREEN, 1478170, 3, "#168E1A");

    private final TextFormatting color;
    private final int colorCode;
    private final int index;
    private final String hex;

    Element(TextFormatting color, int colorCode, int index, String hex) {
        this.color = color;
        this.colorCode = colorCode;
        this.index = index;
        this.hex = hex;
    }

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }

    public TextFormatting getColor() {
        return color;
    }

    public int getColorCode() {
        return colorCode;
    }

    public int getIndex() {
        return index;
    }
}
