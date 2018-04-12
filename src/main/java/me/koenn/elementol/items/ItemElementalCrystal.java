package me.koenn.elementol.items;

import me.koenn.elementol.helper.ItemNBTHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemElementalCrystal extends ItemBase {

    public static final int MAX_CRYSTAL_AMOUNT = 500;
    private static final String TAG_AMOUNT = "crystal_amount";

    public ItemElementalCrystal(String name) {
        super(name);
    }

    public static void setCrystalAmount(ItemStack crystal, int amount) {
        if (getCrystalAmount(crystal) >= MAX_CRYSTAL_AMOUNT) {
            ItemNBTHelper.getNBT(crystal).setInteger(TAG_AMOUNT, MAX_CRYSTAL_AMOUNT);
        } else {
            ItemNBTHelper.getNBT(crystal).setInteger(TAG_AMOUNT, Math.max(amount, 0));
        }
    }

    public static int getCrystalAmount(ItemStack crystal) {
        return ItemNBTHelper.getInt(crystal, TAG_AMOUNT, 0);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return getCrystalAmount(stack) == MAX_CRYSTAL_AMOUNT ? 16 : 1;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Size: " + getCrystalAmount(stack));
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            ItemStack full = new ItemStack(this);
            setCrystalAmount(full, MAX_CRYSTAL_AMOUNT);
            items.add(full);

            items.add(new ItemStack(this));
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return getCrystalAmount(stack) < MAX_CRYSTAL_AMOUNT;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1.0F - ((float) getCrystalAmount(stack) / (float) MAX_CRYSTAL_AMOUNT);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return MathHelper.hsvToRGB((float) getCrystalAmount(stack) / (float) MAX_CRYSTAL_AMOUNT / 3.0F, 1.0F, 1.0F);
    }
}
