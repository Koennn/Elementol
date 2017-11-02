package me.koenn.elementol.blocks;

import me.koenn.elementol.Elementol;
import me.koenn.elementol.tileentities.TileEntityBindingStone;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockBindingStone extends BlockTileEntity<TileEntityBindingStone> {

    private static final AxisAlignedBB AABB = new AxisAlignedBB(-0.06D, 0.0D, -0.06D, 1.063D, 0.60D, 1.06D);

    public BlockBindingStone() {
        super(Material.ROCK, "binding_stone", AABB, 2F, 10F, SoundType.STONE);
        this.setCreativeTab(Elementol.creativeTab);
        this.setFullCube(false);
    }

    @Override
    public TileEntityBindingStone createTileEntity(World world, IBlockState state) {
        return new TileEntityBindingStone();
    }
}
