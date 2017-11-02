package me.koenn.elementol.blocks;

import me.koenn.elementol.tileentities.TileEntityBindingStone;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public final class ModBlocks {

    public static final BlockBase binding_stone = new BlockBindingStone();

    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(
                binding_stone
        );

        GameRegistry.registerTileEntity(TileEntityBindingStone.class, binding_stone.getRegistryName().toString());
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(
                new ItemBlock(binding_stone).setRegistryName(binding_stone.getRegistryName())
        );
    }

    public static void registerModels() {
        binding_stone.registerItemModel(Item.getItemFromBlock(binding_stone));
    }
}
