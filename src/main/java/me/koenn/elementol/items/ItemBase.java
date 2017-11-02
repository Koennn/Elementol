package me.koenn.elementol.items;

import me.koenn.elementol.Elementol;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    protected String name;

    public ItemBase(String name) {
        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
        this.setCreativeTab(Elementol.creativeTab);
    }

    public void registerItemModel() {
        Elementol.proxy.registerItemRenderer(this, 0, name);
    }
}
