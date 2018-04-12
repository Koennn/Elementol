package me.koenn.elementol.tileentities;

import me.koenn.elementol.items.ItemElementalCrystal;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public class TileEntityPylon extends TileEntityInventory implements ITickable {

    private static final int COOLDOWN = 20;
    private int currentCooldown;

    public TileEntityPylon() {
        super(1);
        this.setInventoryFace(EnumFacing.UP, EnumFacing.DOWN);
    }

    @Override
    public void update() {
        if (this.world.isRemote) {
            return;
        }

        if (this.currentCooldown > 0) {
            this.currentCooldown--;
            return;
        }

        ItemStack crystal = this.inventory.getStackInSlot(0);
        if (crystal == null || !(crystal.getItem() instanceof ItemElementalCrystal)) {
            return;
        }
        int currentAmount = ItemElementalCrystal.getCrystalAmount(crystal);
        if (currentAmount == ItemElementalCrystal.MAX_CRYSTAL_AMOUNT) {
            return;
        }

        switch (crystal.getItem().getRegistryName().toString()) {
            case "elementol:fire_crystal":
                if (this.world.getBiome(this.pos).equals(Biome.getBiome(8))) {
                    addCrystal(crystal, currentAmount, 2);
                }
                break;
            case "elementol:water_crystal":
                BlockPos waterPos = new BlockPos(this.pos).add(0, -1, 0);
                IBlockState state = this.world.getBlockState(waterPos);
                if (state.getBlock().equals(Blocks.WATER) && state.getBlock() instanceof BlockStaticLiquid && state.getValue(BlockLiquid.LEVEL) == 0) {
                    this.world.setBlockToAir(waterPos);
                    addCrystal(crystal, currentAmount, 1);
                }
                break;
            case "elementol:air_crystal":
                if (this.pos.getY() >= 120) {
                    addCrystal(crystal, currentAmount, 1);
                }
                break;
            case "elementol:earth_crystal":
                if (this.world.getBlockState(new BlockPos(this.pos).add(0, -1, 0)).getBlock().equals(Blocks.BEDROCK)) {
                    addCrystal(crystal, currentAmount, 1);
                }
                break;
        }
    }

    private void addCrystal(ItemStack crystal, int currentAmount, int add) {
        ItemElementalCrystal.setCrystalAmount(crystal, currentAmount + add);
        this.currentCooldown = COOLDOWN;
    }
}
