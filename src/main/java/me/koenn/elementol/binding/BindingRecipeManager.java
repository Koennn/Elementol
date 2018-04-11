package me.koenn.elementol.binding;

import me.koenn.elementol.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * <p>
 * Copyright (C) Koenn - All Rights Reserved Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential Written by Koen Willemse, September 2017
 */
public final class BindingRecipeManager {

    private static final ArrayList<BindingRecipe> recipes = new ArrayList<>();
    private static int id = 0;

    public static void registerRecipe(BindingRecipe recipe) {
        recipe.setId(id++);
        recipes.add(recipe);
    }

    public static BindingRecipe getRecipe(ItemStack input, ItemStack identifier) {
        for (BindingRecipe recipe : recipes) {
            if (recipe.getInput().isItemEqual(input) && recipe.getIdentifier().isItemEqual(identifier)) {
                return recipe;
            }
        }
        return null;
    }

    public static boolean isIdentifier(ItemStack input, ItemStack identifier) {
        return getRecipe(input, identifier) != null;
    }

    public static boolean isInput(ItemStack input) {
        for (BindingRecipe recipe : recipes) {
            if (recipe.getInput().isItemEqual(input)) {
                return true;
            }
        }
        return false;
    }

    public static BindingRecipe getById(int id) {
        for (BindingRecipe recipe : recipes) {
            if (recipe.getId() == id) {
                return recipe;
            }
        }
        return null;
    }

    public static void registerRecipes() {
        registerRecipe(new BindingRecipe(
                new ItemStack(ModItems.BLANK_GEM),
                new ItemStack(Items.BLAZE_POWDER),
                new ItemStack(ModItems.FIRE_GEM),
                new ItemStack(Items.GUNPOWDER),
                new ItemStack(Items.NETHERBRICK),
                new ItemStack(Item.getItemFromBlock(Blocks.TORCH))
        ));
    }
}
