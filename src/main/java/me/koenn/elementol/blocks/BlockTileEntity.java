package me.koenn.elementol.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockTileEntity<TE extends TileEntity> extends BlockBase {

    public BlockTileEntity(Material material, String name, AxisAlignedBB aabb, float hardness, float resistance, SoundType soundType) {
        super(material, name, aabb, hardness, resistance, soundType);
    }

    public BlockTileEntity(Material material, String name, float hardness, float resistance, SoundType soundType) {
        super(material, name, hardness, resistance, soundType);
    }

    public TE getTileEntity(IBlockAccess world, BlockPos pos) {
        return (TE) world.getTileEntity(pos);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public abstract TE createTileEntity(World world, IBlockState state);
}
