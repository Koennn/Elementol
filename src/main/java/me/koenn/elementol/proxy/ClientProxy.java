package me.koenn.elementol.proxy;

import me.koenn.elementol.Elementol;
import me.koenn.elementol.client.tesr.TESRBindingStone;
import me.koenn.elementol.client.tesr.TESREnergizer;
import me.koenn.elementol.client.tesr.TESRPylon;
import me.koenn.elementol.tileentities.TileEntityBindingStone;
import me.koenn.elementol.tileentities.TileEntityEnergizer;
import me.koenn.elementol.tileentities.TileEntityPylon;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Elementol.MOD_ID + ":" + id, "inventory"));
    }

    @Override
    public String localize(String unlocalized, Object... args) {
        return I18n.format(unlocalized, args);
    }

    @Override
    public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBindingStone.class, new TESRBindingStone());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPylon.class, new TESRPylon());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnergizer.class, new TESREnergizer());
    }
}
