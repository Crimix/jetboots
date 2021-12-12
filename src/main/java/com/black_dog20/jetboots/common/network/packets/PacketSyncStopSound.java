package com.black_dog20.jetboots.common.network.packets;

import com.black_dog20.jetboots.client.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class PacketSyncStopSound {
    private final UUID uuid;

    public PacketSyncStopSound(UUID uuid) {
        this.uuid = uuid;
    }

    public static void encode(PacketSyncStopSound msg, FriendlyByteBuf buffer) {
        buffer.writeUtf(msg.uuid.toString());
    }

    public static PacketSyncStopSound decode(FriendlyByteBuf buffer) {
        return new PacketSyncStopSound(UUID.fromString(buffer.readUtf()));
    }

    public static class Handler {
        public static void handle(PacketSyncStopSound msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
                Player player = Minecraft.getInstance().level.getPlayerByUUID(msg.uuid);
                ClientHelper.stop(player);
            }));

            ctx.get().setPacketHandled(true);
        }
    }
}
