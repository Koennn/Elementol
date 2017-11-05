package me.koenn.elementol.blocks;

import me.koenn.elementol.Elementol;
import me.koenn.elementol.tileentities.TileEntityPylon;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
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
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntityPylon tile = getTileEntity(worldIn, pos);
            IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
            if (!playerIn.isSneaking()) {
                if (playerIn.getHeldItem(EnumHand.MAIN_HAND).isEmpty()) {
                    playerIn.setHeldItem(EnumHand.MAIN_HAND, itemHandler.extractItem(0, 64, false));
                } else {
                    playerIn.setHeldItem(EnumHand.MAIN_HAND, itemHandler.insertItem(0, playerIn.getHeldItem(EnumHand.MAIN_HAND), false));
                }
                tile.markDirty();
            } else {
                return false;
            }
        }
        return true;
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
