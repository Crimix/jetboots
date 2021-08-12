package com.black_dog20.jetboots.common.network.packets;

import com.black_dog20.jetboots.common.network.PacketHandler;
import com.black_dog20.jetboots.common.util.ModUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import java.util.function.Supplier;

public class PacketSyncRocketFlight {

    private final boolean isFlying;

    public PacketSyncRocketFlight(boolean isFlying) {
        this.isFlying = isFlying;
    }

    public static void encode(PacketSyncRocketFlight msg, FriendlyByteBuf buffer) {
        buffer.writeBoolean(msg.isFlying);
    }

    public static PacketSyncRocketFlight decode(FriendlyByteBuf buffer) {
        return new PacketSyncRocketFlight(buffer.readBoolean());
    }

    public static class Handler {
        public static void handle(PacketSyncRocketFlight msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player == null) return;

                if (!ModUtils.hasRocketBoots(player)){
                    PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketSyncStopSound(player.getUUID()));
                    return;
                }

                if (msg.isFlying) {
                    player.fallDistance = 0.0F;
                    if (player.connection != null)
                        player.connection.aboveGroundTickCount = 0;
                    PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketSyncPartical(player.getUUID(), true));
                    if (!player.isInWater()) {
                        PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketSyncSound(player.getUUID()));
                    } else {
                        PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketSyncStopSound(player.getUUID()));
                    }
                } else {
                    PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketSyncStopSound(player.getUUID()));
                }
            });

            ctx.get().setPacketHandled(true);
        }
    }
}
