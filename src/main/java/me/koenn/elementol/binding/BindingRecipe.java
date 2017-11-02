package me.koenn.elementol.binding;

import net.minecraft.item.ItemStack;

/**
 * <p>
 * Copyright (C) Koenn - All Rights Reserved Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential Written by Koen Willemse, September 2017
 */
public class BindingRecipe {

    private final ItemStack input;
    private final ItemStack identifier;
    private final ItemStack[] ingredients;
    private final ItemStack result;
    private int id;

    public BindingRecipe(ItemStack input, ItemStack identifier, ItemStack result, ItemStack... ingredients) {
        this.input = input;
        this.identifier = identifier;
        this.result = result;
        this.ingredients = ingredients;
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getIdentifier() {
        return identifier;
    }

    public ItemStack[] getIngredients() {
        return ingredients;
    }

    public ItemStack getResult() {
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
