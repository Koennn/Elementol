package me.koenn.elementol.client;

import me.koenn.elementol.Elementol;
import me.koenn.elementol.items.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ElementolTab extends CreativeTabs {

    public ElementolTab() {
        super(Elementol.MOD_ID);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.FIRE_GEM);
    }
}
