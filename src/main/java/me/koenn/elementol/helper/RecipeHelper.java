package me.koenn.elementol.helper;

import me.koenn.elementol.binding.BindingRecipe;
import me.koenn.elementol.binding.BindingRecipeManager;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.*;

public class RecipeHelper {

    public static ItemStack getItemStackFromOreItem(Object oreItem) {
        ItemStack itemStack = null;
        if (oreItem instanceof String) {
            itemStack = OreDictionary.getOres((String) oreItem).get(0);
        } else if (oreItem instanceof ItemStack) {
            itemStack = (ItemStack) oreItem;
        } else if (oreItem instanceof Item) {
            itemStack = new ItemStack((Item) oreItem);
        } else if (oreItem instanceof Block) {
            itemStack = new ItemStack((Block) oreItem);
        } else if (oreItem instanceof Collection) {
            itemStack = (ItemStack) ((Collection) oreItem).toArray()[0];
        }
        return itemStack;
    }

    public static void renderBindingRecipe(RenderItem itemRenderer, Item item, int x, int y) {
        ItemStack baseItem = null;
        ItemStack recipeIdentifier = null;
        List<ItemStack> ingredients = new ArrayList<>();
        for (BindingRecipe bindingRecipe : BindingRecipeManager.getRecipes()) {
            if (bindingRecipe.getResult().getItem().equals(item)) {
                baseItem = bindingRecipe.getInput();
                recipeIdentifier = bindingRecipe.getIdentifier();
                ingredients.addAll(Arrays.asList(bindingRecipe.getIngredients()));
            }
        }
        if (baseItem == null) {
            return;
        }

        itemRenderer.renderItemIntoGUI(new ItemStack(baseItem.getItem()), x, y);
        itemRenderer.renderItemIntoGUI(new ItemStack(recipeIdentifier.getItem()), x + 36, y);

        int xOffset = 0;
        int yOffset = 24;

        for (ItemStack recipeItem : ingredients) {
            if (recipeItem == null) {
                continue;
            }
            itemRenderer.renderItemIntoGUI(new ItemStack(recipeItem.getItem()), x + xOffset, y + yOffset);
            xOffset += 18;
            if (xOffset == 53) {
                xOffset = 0;
                yOffset += 18;
            }
        }

        itemRenderer.renderItemIntoGUI(new ItemStack(item), x + 89, y + 18);
    }

    public static void renderSmeltingRecipe(RenderItem itemRenderer, Item item, int x, int y) {
        ItemStack ingredient = null;
        Map<ItemStack, ItemStack> recipes = FurnaceRecipes.instance().getSmeltingList();
        for (ItemStack recipeIngredient : recipes.keySet()) {
            if (recipes.get(recipeIngredient).getItem().equals(item)) {
                ingredient = recipeIngredient;
            }
        }
        if (ingredient == null) {
            return;
        }

        itemRenderer.renderItemIntoGUI(new ItemStack(ingredient.getItem()), x + 8, y);
        itemRenderer.renderItemIntoGUI(new ItemStack(Items.COAL), x + 8, y + 35);
        itemRenderer.renderItemIntoGUI(new ItemStack(item), x + 66, y + 18);
    }

    public static void renderCraftingRecipe(RenderItem itemRenderer, Item item, int x, int y) {
        IRecipe recipe = null;
        for (IRecipe iRecipe : CraftingManager.REGISTRY) {
            if (iRecipe != null && iRecipe.getRecipeOutput() != null && iRecipe.getRecipeOutput().getItem() != null && iRecipe.getRecipeOutput().getItem().equals(item)) {
                recipe = iRecipe;
            }
        }

        int xOffset = 0;
        int yOffset = 0;
        if (recipe instanceof ShapelessRecipes) {
            ShapelessRecipes shapelessRecipe = (ShapelessRecipes) recipe;

            for (Ingredient recipeItem : shapelessRecipe.recipeItems) {
                if (recipeItem == null) {
                    continue;
                }
                itemRenderer.renderItemIntoGUI(recipeItem.getMatchingStacks()[0], x + xOffset, y + yOffset);
                xOffset += 18;
                if (xOffset == 53) {
                    xOffset = 0;
                    yOffset += 18;
                }
            }
            itemRenderer.renderItemIntoGUI(shapelessRecipe.getRecipeOutput(), x + 89, y + 18);
        } else if (recipe instanceof ShapedRecipes) {
            ShapedRecipes shapedRecipe = (ShapedRecipes) recipe;

            for (Ingredient recipeItem : shapedRecipe.recipeItems) {
                if (recipeItem != null) {
                    itemRenderer.renderItemIntoGUI(recipeItem.getMatchingStacks()[0], x + xOffset, y + yOffset);
                }
                xOffset += 18;
                if (xOffset == 54) {
                    xOffset = 0;
                    yOffset += 18;
                }
            }
            itemRenderer.renderItemIntoGUI(shapedRecipe.getRecipeOutput(), x + 89, y + 18);
        } else if (recipe instanceof ShapedOreRecipe) {
            ShapedOreRecipe shapedRecipe = (ShapedOreRecipe) recipe;

            for (Object oreItem : shapedRecipe.getIngredients()) {
                ItemStack recipeItem = RecipeHelper.getItemStackFromOreItem(oreItem);

                if (recipeItem != null) {
                    itemRenderer.renderItemIntoGUI(new ItemStack(recipeItem.getItem()), x + xOffset, y + yOffset);
                }
                xOffset += 18;
                if (xOffset == 54) {
                    xOffset = 0;
                    yOffset += 18;
                }

                itemRenderer.renderItemIntoGUI(shapedRecipe.getRecipeOutput(), x + 89, y + 18);
            }
        } else if (recipe instanceof ShapelessOreRecipe) {
            ShapelessOreRecipe shapelessRecipe = (ShapelessOreRecipe) recipe;

            for (Object oreItem : shapelessRecipe.getIngredients()) {
                ItemStack recipeItem = RecipeHelper.getItemStackFromOreItem(oreItem);

                if (recipeItem == null) {
                    continue;
                }

                itemRenderer.renderItemIntoGUI(new ItemStack(recipeItem.getItem()), x + xOffset, y + yOffset);
                xOffset += 18;
                if (xOffset == 53) {
                    xOffset = 0;
                    yOffset += 18;
                }

                itemRenderer.renderItemIntoGUI(shapelessRecipe.getRecipeOutput(), x + 89, y + 18);
            }
        }
    }
}
