package me.koenn.elementol.blocks;

import me.koenn.elementol.tileentities.TileEntityBindingStone;
import me.koenn.elementol.tileentities.TileEntityPylon;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public final class ModBlocks {

    public static final BlockBindingStone BINDING_STONE = new BlockBindingStone("binding_stone");
    public static final BlockPylon PYLON = new BlockPylon("pylon");
    public static final BlockEnergizer ENERGIZER = new BlockEnergizer("energizer");

    public static final BlockBase[] ALL_BLOCKS = new BlockBase[]{BINDING_STONE, PYLON, ENERGIZER};

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        registry.registerAll(ALL_BLOCKS);
    }

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityBindingStone.class, BINDING_STONE.getRegistryName().toString());
        GameRegistry.registerTileEntity(TileEntityPylon.class, PYLON.getRegistryName().toString());
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        for (BlockBase block : ALL_BLOCKS) {
            registry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
    }

    public static void registerModels() {
        for (BlockBase block : ALL_BLOCKS) {
            block.registerItemModel(Item.getItemFromBlock(block));
        }
    }
}
