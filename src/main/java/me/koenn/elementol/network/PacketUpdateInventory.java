package me.koenn.elementol.network;

import io.netty.buffer.ByteBuf;
import me.koenn.elementol.tileentities.TileEntityInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketUpdateInventory implements IMessage {

    private BlockPos pos;
    private ItemStack[] items;
    private long lastChangeTime;

    public PacketUpdateInventory(TileEntityInventory te) {
        //Create an array to mach the inventory.
        ItemStack[] items = new ItemStack[te.inventory.getSlots()];

        //Copy all the items from the inventory to the array.
        for (int i = 0; i < items.length; i++) {
            items[i] = te.inventory.getStackInSlot(i);
        }

        //Set the variables.
        this.pos = te.getPos();
        this.items = items;
        this.lastChangeTime = te.lastChangeTime;
    }

    @SuppressWarnings("unused")
    public PacketUpdateInventory() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        //Write the position as a long.
        buf.writeLong(pos.toLong());

        //Write the length of the item array.
        buf.writeInt(items.length);

        //Write the item array.
        for (ItemStack stack : items) {
            ByteBufUtils.writeItemStack(buf, stack);
        }

        //Write the lastChangeTime.
        buf.writeLong(lastChangeTime);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        //Read the position (convert from long).
        pos = BlockPos.fromLong(buf.readLong());

        //Read the item array length.
        int length = buf.readInt();

        //Create the item array with the length.
        items = new ItemStack[length];

        //Read all the items.
        for (int i = 0; i < length; i++) {
            items[i] = ByteBufUtils.readItemStack(buf);
        }

        //Read the lastChangeTime.
        lastChangeTime = buf.readLong();
    }

    @SideOnly(Side.CLIENT)
    public static class Handler implements IMessageHandler<PacketUpdateInventory, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateInventory message, MessageContext ctx) {
            //Schedule a task on the client main thread.
            Minecraft.getMinecraft().addScheduledTask(() -> {

                //Get the TileEntity instance.
                TileEntityInventory te = (TileEntityInventory) Minecraft.getMinecraft().world.getTileEntity(message.pos);

                //Set all the items in the right slots.
                for (int i = 0; i < message.items.length; i++) {
                    te.inventory.setStackInSlot(i, message.items[i]);
                }

                //Set the lastChangeTime.
                te.lastChangeTime = message.lastChangeTime;
            });

            //Don't send a return packet.
            return null;
        }

    }
}
