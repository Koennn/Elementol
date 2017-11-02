package me.koenn.elementol.tileentities;

import me.koenn.elementol.Elementol;
import me.koenn.elementol.network.PacketRequestUpdateBindingStone;
import me.koenn.elementol.network.PacketUpdateBindingStone;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntityBindingStone extends TileEntity implements ITickable {

    public long lastChangeTime;
    public ItemStackHandler inventory = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            if (!world.isRemote) {
                lastChangeTime = world.getTotalWorldTime();
                Elementol.network.sendToAllAround(new PacketUpdateBindingStone(TileEntityBindingStone.this), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
            }
        }
    };

    public static List<EntityItem> getCaptureItems(World worldIn, double x, double y, double z) {
        return worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(x - 0.5D, y, z - 0.5D, x + 0.5D, y + 1.5D, z + 0.5D), EntitySelectors.IS_ALIVE);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setLong("lastChangeTime", lastChangeTime);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        lastChangeTime = compound.getLong("lastChangeTime");
        super.readFromNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) inventory : super.getCapability(capability, facing);
    }

    @Override
    public void onLoad() {
        if (world.isRemote) {
            Elementol.network.sendToServer(new PacketRequestUpdateBindingStone(this));
        }
    }

    public void pullItems() {
        if (inventory.getStackInSlot(0).getItem().equals(Items.AIR)) {
            for (EntityItem item : getCaptureItems(world, pos.getX(), pos.getY(), pos.getZ())) {
                if (item != null) {
                    inventory.insertItem(0, item.getItem(), false);
                    world.removeEntity(item);
                    return;
                }
            }
        }
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            pullItems();
        }
    }
}
