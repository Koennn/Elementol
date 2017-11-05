package me.koenn.elementol.network;

import io.netty.buffer.ByteBuf;
import me.koenn.elementol.tileentities.TileEntityInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestUpdateInventory implements IMessage {

    private BlockPos pos;
    private int dimension;

    public PacketRequestUpdateInventory(TileEntityInventory te) {
        this.pos = te.getPos();
        this.dimension = te.getWorld().provider.getDimension();
    }

    @SuppressWarnings("unused")
    public PacketRequestUpdateInventory() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        //Write the position as a long.
        buf.writeLong(pos.toLong());

        //Write the dimension id.
        buf.writeInt(dimension);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        //Read the position (convert from long).
        pos = BlockPos.fromLong(buf.readLong());

        //Read the dimension id.
        dimension = buf.readInt();
    }

    public static class Handler implements IMessageHandler<PacketRequestUpdateInventory, PacketUpdateInventory> {

        @Override
        public PacketUpdateInventory onMessage(PacketRequestUpdateInventory message, MessageContext ctx) {
            //Get the World instance (with the right dimension id).
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension);

            //Get the TileEntity instance.
            TileEntityInventory te = (TileEntityInventory) world.getTileEntity(message.pos);

            //Check whether the TileEntity still exists.
            if (te != null) {

                //Send a update packet back to the client.
                return new PacketUpdateInventory(te);
            } else {
                return null;
            }
        }

    }

}
