package me.koenn.elementol.tileentities;

import me.koenn.elementol.binding.BindingRecipe;
import me.koenn.elementol.binding.BindingRecipeManager;
import me.koenn.elementol.items.ModItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class TileEntityBindingStone extends TileEntityInventory implements ITickable {

    public int stage;
    public BindingRecipe currentRecipe;

    public TileEntityBindingStone() {
        super(2);
    }

    public static List<EntityItem> getCaptureItems(World worldIn, double x, double y, double z) {
        return worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(x - 0.5D, y, z - 0.5D, x + 0.5D, y + 1.5D, z + 0.5D), EntitySelectors.IS_ALIVE);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("stage", stage);
        if (currentRecipe != null) {
            compound.setInteger("currentRecipe", currentRecipe.getId());
        }
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        stage = compound.getInteger("stage");
        if (compound.hasKey("currentRecipe")) {
            currentRecipe = BindingRecipeManager.getById(compound.getInteger("currentRecipe"));
        }
        super.readFromNBT(compound);
    }

    public EntityItem pullItem() {
        for (EntityItem item : getCaptureItems(world, pos.getX(), pos.getY(), pos.getZ())) {
            if (item != null) {
                return item;
            }
        }
        return null;
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            EntityItem item = pullItem();
            if (item == null) {
                return;
            }

            ItemStack input = item.getItem();
            ItemStack current = inventory.getStackInSlot(0);

            //Check if it doesn't contain an item and if the item is a valid input -> insert primary input.
            if ((current == null || current.getItem().equals(Items.AIR)) && BindingRecipeManager.isInput(input)) {
                this.world.removeEntity(item);
                this.inventory.setStackInSlot(1, new ItemStack(ModItems.BLANK_GEM));
                this.inventory.setStackInSlot(0, input);
                //Check if there is no recipe selected and if the input is a valid identifier -> insert identifier.
            } else if (this.currentRecipe == null && BindingRecipeManager.isIdentifier(current, input)) {
                this.world.removeEntity(item);
                this.currentRecipe = BindingRecipeManager.getRecipe(current, input);
                this.stage = 0;
                this.inventory.setStackInSlot(1, this.currentRecipe.getIngredients()[this.stage]);
                //Check if there is a recipe selected -> attempt insert ingredient.
            } else if (this.currentRecipe != null && this.stage < this.currentRecipe.getIngredients().length) {
                if (this.currentRecipe.getIngredients()[this.stage].isItemEqual(input)) {
                    this.world.removeEntity(item);
                    this.stage++;
                    //Check if the recipe is completed -> drop the output item.
                    if (this.stage >= this.currentRecipe.getIngredients().length) {
                        this.stage = 0;
                        this.inventory.extractItem(0, 64, false);
                        this.inventory.extractItem(1, 64, false);
                        world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, this.currentRecipe.getResult()));
                        this.currentRecipe = null;
                    } else {
                        this.inventory.setStackInSlot(1, this.currentRecipe.getIngredients()[this.stage]);
                    }
                }
            }
        }
    }
}
