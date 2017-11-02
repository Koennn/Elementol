package me.koenn.elementol.network;

import io.netty.buffer.ByteBuf;
import me.koenn.elementol.tileentities.TileEntityBindingStone;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestUpdateBindingStone implements IMessage {

    private BlockPos pos;
    private int dimension;

    public PacketRequestUpdateBindingStone(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public PacketRequestUpdateBindingStone(TileEntityBindingStone te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    @SuppressWarnings("unused")
    public PacketRequestUpdateBindingStone() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(dimension);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        dimension = buf.readInt();
    }

    public static class Handler implements IMessageHandler<PacketRequestUpdateBindingStone, PacketUpdateBindingStone> {

        @Override
        public PacketUpdateBindingStone onMessage(PacketRequestUpdateBindingStone message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension);
            TileEntityBindingStone te = (TileEntityBindingStone) world.getTileEntity(message.pos);
            if (te != null) {
                return new PacketUpdateBindingStone(te);
            } else {
                return null;
            }
        }

    }

}
