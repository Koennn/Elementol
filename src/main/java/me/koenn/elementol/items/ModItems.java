package me.koenn.elementol.items;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public final class ModItems {

    public static final ItemBase BLANK_GEM = new ItemBase("blank_gem");
    public static final ItemBase FIRE_GEM = new ItemElementalGem("fire_gem");
    public static final ItemBase WATER_GEM = new ItemElementalGem("water_gem");
    public static final ItemBase AIR_GEM = new ItemElementalGem("air_gem");
    public static final ItemBase EARTH_GEM = new ItemElementalGem("earth_gem");
    public static final ItemBase FIRE_CRYSTAL = new ItemElementalCrystal("fire_crystal");
    public static final ItemBase WATER_CRYSTAL = new ItemElementalCrystal("water_crystal");
    public static final ItemBase AIR_CRYSTAL = new ItemElementalCrystal("air_crystal");
    public static final ItemBase EARTH_CRYSTAL = new ItemElementalCrystal("earth_crystal");
    public static final ItemBase UNBOUND_CRYSTAL = new ItemBase("unbound_crystal");

    public static final ItemBase[] ALL_ITEMS = new ItemBase[]{
            BLANK_GEM, FIRE_GEM, WATER_GEM, AIR_GEM, EARTH_GEM,
            FIRE_CRYSTAL, WATER_CRYSTAL, AIR_CRYSTAL, EARTH_CRYSTAL,
            UNBOUND_CRYSTAL
    };

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(ALL_ITEMS);
    }

    public static void registerModels() {
        for (ItemBase item : ALL_ITEMS) {
            item.registerItemModel();
        }
    }
}
