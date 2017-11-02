package me.koenn.elementol;

import me.koenn.elementol.blocks.ModBlocks;
import me.koenn.elementol.client.ElementolTab;
import me.koenn.elementol.gui.GuiHandler;
import me.koenn.elementol.items.ModItems;
import me.koenn.elementol.network.PacketRequestUpdateBindingStone;
import me.koenn.elementol.network.PacketUpdateBindingStone;
import me.koenn.elementol.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(
        modid = Elementol.MOD_ID,
        name = Elementol.MOD_NAME,
        version = Elementol.VERSION
)
public class Elementol {

    public static final String MOD_ID = "elementol";
    public static final String MOD_NAME = "Elementol";
    public static final String VERSION = "1.0-SNAPSHOT";

    public static final ElementolTab creativeTab = new ElementolTab();
    @Mod.Instance(MOD_ID)
    public static Elementol instance;
    @SidedProxy(serverSide = "me.koenn.elementol.proxy.CommonProxy", clientSide = "me.koenn.elementol.proxy.ClientProxy", modId = MOD_ID)
    public static CommonProxy proxy;
    public static SimpleNetworkWrapper network;

    @SuppressWarnings("NewExpressionSideOnly")
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.registerRenderers();

        network = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);
        network.registerMessage(new PacketUpdateBindingStone.Handler(), PacketUpdateBindingStone.class, 0, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdateBindingStone.Handler(), PacketRequestUpdateBindingStone.class, 1, Side.SERVER);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ModItems.register(event.getRegistry());
            ModBlocks.registerItemBlocks(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            ModBlocks.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerItemModels(ModelRegistryEvent event) {
            ModItems.registerModels();
            ModBlocks.registerModels();
        }
    }
}
