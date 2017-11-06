package me.koenn.elementol.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class ItemNBTHelper {

    public static NBTTagCompound getNBT(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        return stack.getTagCompound();
    }

    public static boolean hasTag(ItemStack stack, String tag) {
        return !stack.isEmpty() && getNBT(stack).hasKey(tag);
    }

    public static int getInt(ItemStack stack, String tag, int defaultExpected) {
        return hasTag(stack, tag) ? getNBT(stack).getInteger(tag) : defaultExpected;
    }
}
