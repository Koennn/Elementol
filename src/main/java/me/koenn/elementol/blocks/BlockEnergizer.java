package me.koenn.elementol.blocks;

import me.koenn.elementol.Elementol;
import me.koenn.elementol.tileentities.TileEntityEnergizer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockEnergizer extends BlockTileEntity<TileEntityEnergizer> {

    private static final AxisAlignedBB AXIS_ALIGNED_BB = new AxisAlignedBB(0, 0, 0, 1, 0.6, 1);

    public BlockEnergizer(String name) {
        super(Material.ROCK, name, AXIS_ALIGNED_BB, 4.0F, 20.0F, SoundType.STONE);
        this.setFullCube(false);
        this.setCreativeTab(Elementol.ELEMENTOL_TAB);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntityEnergizer tile = getTileEntity(worldIn, pos);
            if (tile.isEnergizing()) {
                return false;
            }

            IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
            if (!playerIn.isSneaking()) {
                if (playerIn.getHeldItem(hand).isEmpty()) {
                    playerIn.setHeldItem(hand, itemHandler.extractItem(0, 64, false));
                } else {
                    playerIn.setHeldItem(hand, itemHandler.insertItem(0, playerIn.getHeldItem(hand), false));
                }
                tile.markDirty();
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public TileEntityEnergizer createTileEntity(World world, IBlockState state) {
        return new TileEntityEnergizer();
    }
}
