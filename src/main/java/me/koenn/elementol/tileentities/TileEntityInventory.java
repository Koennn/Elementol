package me.koenn.elementol.tileentities;

import me.koenn.elementol.Elementol;
import me.koenn.elementol.network.PacketRequestUpdateInventory;
import me.koenn.elementol.network.PacketUpdateInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileEntityInventory extends TileEntity {

    public long lastChangeTime;
    public ItemStackHandler inventory;
    public List<EnumFacing> inventoryFaces = new ArrayList<>();

    public TileEntityInventory(int size) {
        //Create the inventory with the given size.
        this.inventory = new ItemStackHandler(size) {
            @Override
            protected void onContentsChanged(int slot) {
                //Check whether we're running on the server.
                if (!world.isRemote) {

                    //Set the lastChangeTime to the world time (now).
                    lastChangeTime = world.getTotalWorldTime();

                    //Send the update packet to everyone within 64 blocks.
                    Elementol.networkWrapper.sendToAllAround(
                            new PacketUpdateInventory(TileEntityInventory.this),
                            new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64)
                    );
                }
            }
        };
    }

    public void setInventoryFace(EnumFacing... faces) {
        Collections.addAll(this.inventoryFaces, faces);
    }

    @Override
    public void onLoad() {
        //Check whether we're running on the client.
        if (this.world.isRemote) {

            //Send the update request packet to the server.
            Elementol.networkWrapper.sendToServer(new PacketRequestUpdateInventory(this));
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && this.inventoryFaces.contains(facing)) || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (this.inventoryFaces.contains(facing) ? (T) this.inventory : null) : super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        //Read the inventory.
        this.inventory.deserializeNBT(compound.getCompoundTag("inventory"));

        //Read the lastChangeTime.
        this.lastChangeTime = compound.getLong("lastChangeTime");

        //Allow the superclass to read data.
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        //Write the inventory.
        compound.setTag("inventory", this.inventory.serializeNBT());

        //Write the lastChangeTime.
        compound.setLong("lastChangeTime", this.lastChangeTime);

        //Allow the superclass to write data and return the tag.
        return super.writeToNBT(compound);
    }
}
