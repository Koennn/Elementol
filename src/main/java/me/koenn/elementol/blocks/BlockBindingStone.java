package me.koenn.elementol.blocks;

import me.koenn.elementol.Elementol;
import me.koenn.elementol.tileentities.TileEntityBindingStone;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockBindingStone extends BlockTileEntity<TileEntityBindingStone> {

    private static final AxisAlignedBB AABB = new AxisAlignedBB(-0.06D, 0.0D, -0.06D, 1.063D, 0.60D, 1.06D);

    public BlockBindingStone() {
        super(Material.ROCK, "binding_stone", AABB, 2F, 10F, SoundType.STONE);
        this.setCreativeTab(Elementol.creativeTab);
        this.setFullCube(false);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntityBindingStone tile = getTileEntity(world, pos);
            IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
            if (!player.isSneaking()) {
                if (player.getHeldItem(hand).isEmpty()) {
                    player.setHeldItem(hand, itemHandler.extractItem(0, 64, false));
                } else {
                    player.setHeldItem(hand, itemHandler.insertItem(0, player.getHeldItem(hand), false));
                }
                tile.markDirty();
            }
        } else {
            if (player.isSneaking()) {
                TileEntityBindingStone tile = getTileEntity(world, pos);
                IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
                ItemStack stack = itemHandler.getStackInSlot(0);
                if (!stack.isEmpty()) {
                    String localized = Elementol.proxy.localize(stack.getUnlocalizedName() + ".name");
                    player.sendMessage(new TextComponentString(stack.getCount() + "x " + localized));
                } else {
                    player.sendMessage(new TextComponentString("Empty"));
                }
            }
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntityBindingStone tile = getTileEntity(world, pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            world.spawnEntity(item);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public Class<TileEntityBindingStone> getTileEntityClass() {
        return TileEntityBindingStone.class;
    }

    @Override
    public TileEntityBindingStone createTileEntity(World world, IBlockState state) {
        return new TileEntityBindingStone();
    }
}
