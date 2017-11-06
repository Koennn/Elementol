package me.koenn.elementol.network;

import io.netty.buffer.ByteBuf;
import me.koenn.elementol.tileentities.TileEntityEnergizer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketEnergizerParticle implements IMessage {

    private BlockPos pos;

    public PacketEnergizerParticle(TileEntityEnergizer te) {
        this.pos = te.getPos();
    }

    @SuppressWarnings("unused")
    public PacketEnergizerParticle() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
    }

    @SideOnly(Side.CLIENT)
    public static class Handler implements IMessageHandler<PacketEnergizerParticle, IMessage> {

        @Override
        public IMessage onMessage(PacketEnergizerParticle message, MessageContext ctx) {
            Minecraft mc = Minecraft.getMinecraft();
            mc.addScheduledTask(() -> mc.world.spawnParticle(
                    EnumParticleTypes.SPELL_WITCH,
                    message.pos.getX() + 0.5,
                    message.pos.getY() + 0.7,
                    message.pos.getZ() + 0.5,
                    1, 1, 1
            ));
            return null;
        }
    }
}
