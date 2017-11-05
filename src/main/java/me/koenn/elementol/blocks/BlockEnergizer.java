package me.koenn.elementol.blocks;

import me.koenn.elementol.Elementol;
import me.koenn.elementol.tileentities.TileEntityEnergizer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockEnergizer extends BlockTileEntity<TileEntityEnergizer> {

    private static final AxisAlignedBB AXIS_ALIGNED_BB = new AxisAlignedBB(0, 0, 0, 1, 0.6, 1);

    public BlockEnergizer(String name) {
        super(Material.ROCK, name, AXIS_ALIGNED_BB, 4.0F, 20.0F, SoundType.STONE);
        this.setFullCube(false);
        this.setCreativeTab(Elementol.ELEMENTOL_TAB);
    }

    @Override
    public TileEntityEnergizer createTileEntity(World world, IBlockState state) {
        return new TileEntityEnergizer();
    }
}
