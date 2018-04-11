package me.koenn.elementol.network;

import io.netty.buffer.ByteBuf;
import me.koenn.elementol.tileentities.TileEntityEnergizer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketUpdateEnergizer implements IMessage {

    private BlockPos pos;
    private int progress;

    public PacketUpdateEnergizer(TileEntityEnergizer te) {
        this.pos = te.getPos();
        this.progress = te.progress;
    }

    @SuppressWarnings("unused")
    public PacketUpdateEnergizer() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.pos.toLong());
        buf.writeInt(this.progress);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = BlockPos.fromLong(buf.readLong());
        this.progress = buf.readInt();
    }

    @SideOnly(Side.CLIENT)
    public static class Handler implements IMessageHandler<PacketUpdateEnergizer, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateEnergizer message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> ((TileEntityEnergizer) Minecraft.getMinecraft().world.getTileEntity(message.pos)).progress = message.progress);
            return null;
        }
    }
}
