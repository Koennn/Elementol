package me.koenn.elementol.items;

import me.koenn.elementol.Elementol;
import me.koenn.elementol.gui.GuiHandler;
import me.koenn.elementol.helper.ItemNBTHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemElementalGem extends ItemBase {

    private static final String ENERGIZED_TAG = "energized";

    public ItemElementalGem(String name) {
        super(name);
        this.setHasSubtypes(true);
    }

    public static void makeEnergized(ItemStack stack) {
        ItemNBTHelper.getNBT(stack).setBoolean(ENERGIZED_TAG, true);
    }

    public static boolean isEnergized(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, ENERGIZED_TAG, false);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return isEnergized(stack) ? String.format("%s.energized", super.getUnlocalizedName(stack)) : super.getUnlocalizedName(stack);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return isEnergized(stack);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            items.add(new ItemStack(this));

            ItemStack energized = new ItemStack(this);
            makeEnergized(energized);
            items.add(energized);
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.isRemote) {
            playerIn.openGui(Elementol.instance, GuiHandler.GUIDE_ID, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return isEnergized(stack) ? 16 : super.getItemStackLimit(stack);
    }
}
