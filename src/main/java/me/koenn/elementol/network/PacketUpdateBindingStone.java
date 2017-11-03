package me.koenn.elementol.network;

import io.netty.buffer.ByteBuf;
import me.koenn.elementol.tileentities.TileEntityBindingStone;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketUpdateBindingStone implements IMessage {

    private BlockPos pos;
    private ItemStack stack;
    private long lastChangeTime;

    public PacketUpdateBindingStone(BlockPos pos, ItemStack stack, long lastChangeTime) {
        this.pos = pos;
        this.stack = stack;
        this.lastChangeTime = lastChangeTime;
    }

    public PacketUpdateBindingStone(TileEntityBindingStone te) {
        this(te.getPos(), te.inventory.getStackInSlot(1), te.lastChangeTime);
    }

    @SuppressWarnings("unused")
    public PacketUpdateBindingStone() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeItemStack(buf, stack);
        buf.writeLong(lastChangeTime);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        stack = ByteBufUtils.readItemStack(buf);
        lastChangeTime = buf.readLong();
    }

    @SideOnly(Side.CLIENT)
    public static class Handler implements IMessageHandler<PacketUpdateBindingStone, IMessage> {

        @Override
        public IMessage onMessage(PacketUpdateBindingStone message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntityBindingStone te = (TileEntityBindingStone) Minecraft.getMinecraft().world.getTileEntity(message.pos);
                te.inventory.setStackInSlot(1, message.stack);
                te.lastChangeTime = message.lastChangeTime;
            });
            return null;
        }

    }
}
