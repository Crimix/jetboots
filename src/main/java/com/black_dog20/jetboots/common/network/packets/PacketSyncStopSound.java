package com.black_dog20.jetboots.common.network.packets;

import com.black_dog20.jetboots.client.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class PacketSyncStopSound {
    private final UUID uuid;

    public PacketSyncStopSound(UUID uuid) {
        this.uuid = uuid;
    }

    public static void encode(PacketSyncStopSound msg, PacketBuffer buffer) {
        buffer.writeString(msg.uuid.toString());
    }

    public static PacketSyncStopSound decode(PacketBuffer buffer) {
        return new PacketSyncStopSound(UUID.fromString(buffer.readString()));
    }

    public static class Handler {
        public static void handle(PacketSyncStopSound msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
                PlayerEntity player = Minecraft.getInstance().world.getPlayerByUuid(msg.uuid);
                ClientHelper.stop(player);
            }));

            ctx.get().setPacketHandled(true);
        }
    }
}
