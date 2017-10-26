package me.koenn.elementol.items;

import me.koenn.elementol.Element;
import net.minecraft.item.Item;

public class ItemElementGem extends Item {

    private final Element element;

    public ItemElementGem(Element element) {
        this.setUnlocalizedName("elemental_gem." + element.name().toLowerCase());
        this.element = element;
    }
}
