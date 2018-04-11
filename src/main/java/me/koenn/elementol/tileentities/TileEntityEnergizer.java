package me.koenn.elementol.tileentities;

import me.koenn.elementol.Elementol;
import me.koenn.elementol.blocks.ModBlocks;
import me.koenn.elementol.items.ItemElementalCrystal;
import me.koenn.elementol.items.ItemElementalGem;
import me.koenn.elementol.network.PacketUpdateEnergizer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class TileEntityEnergizer extends TileEntityInventory implements ITickable {

    public static final int REQUIRED_CRYSTAL = 500;
    public static final int REQUIRED_PROGRESS = 400;

    public int progress;

    public TileEntityEnergizer() {
        super(1);
    }

    public boolean hasPylon() {
        return this.world.getBlockState(new BlockPos(this.getPos()).add(0, 1, 0)).getBlock().equals(ModBlocks.PYLON);
    }

    public boolean isEnergizing() {
        return this.progress > 0;
    }

    @Override
    public void update() {
        if (this.world.isRemote) {
            return;
        }

        if (!this.hasPylon()) {
            return;
        }

        ItemStack item = this.inventory.getStackInSlot(0).copy();
        if (item == null || !(item.getItem() instanceof ItemElementalGem) || ItemElementalGem.isEnergized(item)) {
            return;
        }

        if (this.isEnergizing()) {
            this.progress++;
            Elementol.networkWrapper.sendToAllAround(
                    new PacketUpdateEnergizer(this),
                    new NetworkRegistry.TargetPoint(this.world.provider.getDimension(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), 64)
            );

            if (this.progress >= REQUIRED_PROGRESS) {
                this.progress = 0;
                ItemElementalGem.makeEnergized(item);
                this.inventory.setStackInSlot(0, item);

                TileEntityPylon pylon = (TileEntityPylon) this.world.getTileEntity(new BlockPos(this.getPos()).add(0, 1, 0));
                pylon.inventory.extractItem(0, 1, false);

                Elementol.networkWrapper.sendToAllAround(
                        new PacketUpdateEnergizer(this),
                        new NetworkRegistry.TargetPoint(this.world.provider.getDimension(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), 64)
                );
            }
            return;
        }

        TileEntityPylon pylon = (TileEntityPylon) this.world.getTileEntity(new BlockPos(this.getPos()).add(0, 1, 0));
        ItemStack pylonItem = pylon.inventory.getStackInSlot(0);
        if (pylonItem == null || !(pylonItem.getItem() instanceof ItemElementalCrystal)) {
            return;
        }

        if (ItemElementalCrystal.getCrystalAmount(pylonItem) < REQUIRED_CRYSTAL) {
            return;
        }

        this.progress++;
    }
}
