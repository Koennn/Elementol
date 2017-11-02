package me.koenn.elementol.blocks;

import me.koenn.elementol.Elementol;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockBase extends Block {

    protected final String name;
    protected final AxisAlignedBB aabb;
    protected boolean fullCube;

    public BlockBase(Material material, String name, float hardness, float resistance, SoundType soundType) {
        this(material, name, Block.FULL_BLOCK_AABB, hardness, resistance, soundType);
    }

    public BlockBase(Material material, String name, AxisAlignedBB aabb, float hardness, float resistance, SoundType soundType) {
        super(material);

        this.name = name;
        this.aabb = aabb;
        this.fullCube = true;

        this.setUnlocalizedName(name);
        this.setRegistryName(name);

        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setSoundType(soundType);
    }

    public void registerItemModel(Item item) {
        Elementol.proxy.registerItemRenderer(item, 0, name);
    }

    @Override
    public BlockBase setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }

    public void setFullCube(boolean fullCube) {
        this.fullCube = fullCube;
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return this.aabb;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return this.fullCube;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return this.fullCube;
    }
}
