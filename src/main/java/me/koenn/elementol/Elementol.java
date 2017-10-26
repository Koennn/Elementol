package me.koenn.elementol;

import me.koenn.elementol.blocks.TestBlock;
import me.koenn.elementol.items.ItemElementGem;
import me.koenn.elementol.items.TestItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(
        modid = Elementol.MOD_ID,
        name = Elementol.MOD_NAME,
        version = Elementol.VERSION
)
public class Elementol {

    public static final String MOD_ID = "elementol";
    public static final String MOD_NAME = "Elementol";
    public static final String VERSION = "1.0-SNAPSHOT";

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static Elementol INSTANCE;

    /**
     * This is the first initialization event. Register tile entities here. The registry events below will have fired
     * prior to entry to this method.
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    /**
     * Forge will automatically look up and bind blocks to the fields in this class based on their registry name.
     */
    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Blocks {

        public static final TestBlock test_block = null;
    }

    /**
     * Forge will automatically look up and bind items to the fields in this class based on their registry name.
     */
    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Items {

        public static final TestItem test_item = null;
        public static final ItemElementGem fire_gem = null;
        public static final ItemElementGem water_gem = null;
        public static final ItemElementGem air_gem = null;
        public static final ItemElementGem earth_gem = null;
    }

    /**
     * This is a special class that listens to registry events, to allow creation of mod blocks and items at the proper
     * time.
     */
    @Mod.EventBusSubscriber()
    public static class ObjectRegistryHandler {

        /**
         * Listen for the register event for creating custom items
         */
        @SubscribeEvent
        @SuppressWarnings("ConstantConditions")
        public static void addItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(new ItemBlock(Blocks.test_block).setRegistryName(MOD_ID, "test_block"));
            event.getRegistry().register(new TestItem().setRegistryName(MOD_ID, "test_item"));
        }

        /**
         * Listen for the register event for creating custom blocks
         */
        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(new TestBlock().setRegistryName(MOD_ID, "test_block"));
        }
    }
}
