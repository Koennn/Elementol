package me.koenn.elementol.items;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public final class ModItems {

    public static final ItemBase BLANK_GEM = new ItemBase("blank_gem");
    public static final ItemBase FIRE_GEM = new ItemBase("fire_gem");
    public static final ItemBase WATER_GEM = new ItemBase("water_gem");
    public static final ItemBase AIR_GEM = new ItemBase("air_gem");
    public static final ItemBase EARTH_GEM = new ItemBase("earth_gem");

    public static final ItemBase[] ALL_ITEMS = new ItemBase[]{BLANK_GEM, FIRE_GEM, WATER_GEM, AIR_GEM, EARTH_GEM};

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(ALL_ITEMS);
    }

    public static void registerModels() {
        for (ItemBase item : ALL_ITEMS) {
            item.registerItemModel();
        }
    }
}
