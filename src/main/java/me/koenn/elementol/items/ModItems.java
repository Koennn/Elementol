package me.koenn.elementol.items;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public final class ModItems {

    public static final ItemBase blank_gem = new ItemBase("blank_gem");
    public static final ItemBase fire_gem = new ItemBase("fire_gem");
    public static final ItemBase water_gem = new ItemBase("water_gem");
    public static final ItemBase air_gem = new ItemBase("air_gem");
    public static final ItemBase earth_gem = new ItemBase("earth_gem");

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                blank_gem, fire_gem, water_gem, air_gem, earth_gem
        );
    }

    public static void registerModels() {
        blank_gem.registerItemModel();
        fire_gem.registerItemModel();
        water_gem.registerItemModel();
        air_gem.registerItemModel();
        earth_gem.registerItemModel();
    }
}
