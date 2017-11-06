package me.koenn.elementol.blocks;

import me.koenn.elementol.Elementol;
import me.koenn.elementol.items.ItemElementalCrystal;
import me.koenn.elementol.tileentities.TileEntityEnergizer;
import me.koenn.elementol.tileentities.TileEntityPylon;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockPylon extends BlockTileEntity<TileEntityPylon> {

    public BlockPylon(String name) {
        super(Material.IRON, name, 2.0F, 10.0F, SoundType.METAL);
        this.fullCube = false;
        this.translucent = true;
        this.setLightOpacity(2);
        this.setCreativeTab(Elementol.ELEMENTOL_TAB);
        this.setTickRandomly(true);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.34, 0, 0.34, 0.66, 1.3, 0.66);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntityPylon tile = getTileEntity(worldIn, pos);
            IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
            if (!playerIn.isSneaking()) {
                if (playerIn.getHeldItem(hand).isEmpty()) {
                    if (!this.isEnergizing(worldIn, pos)) {
                        playerIn.setHeldItem(hand, itemHandler.extractItem(0, 64, false));
                    }
                } else if (playerIn.getHeldItem(hand).getItem() instanceof ItemElementalCrystal) {
                    playerIn.setHeldItem(hand, itemHandler.insertItem(0, playerIn.getHeldItem(hand), false));
                } else {
                    return false;
                }
                tile.markDirty();
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean isEnergizing(World worldIn, BlockPos pos) {
        BlockPos energizerPos = new BlockPos(pos).add(0, -1, 0);
        return worldIn.getBlockState(energizerPos).getBlock().equals(ModBlocks.ENERGIZER) && ((TileEntityEnergizer) worldIn.getTileEntity(energizerPos)).isEnergizing();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        IItemHandler itemHandler = getTileEntity(worldIn, pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        ItemStack crystal = itemHandler.getStackInSlot(0);
        if (crystal == null || !(crystal.getItem() instanceof ItemElementalCrystal)) {
            return 0;
        }

        return (int) Math.round((14.0 / ItemElementalCrystal.MAX_CRYSTAL_AMOUNT) * ItemElementalCrystal.getCrystalAmount(crystal)) + 1;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public TileEntityPylon createTileEntity(World world, IBlockState state) {
        return new TileEntityPylon();
    }
}
