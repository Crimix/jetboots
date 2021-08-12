package com.black_dog20.jetboots.common.network.packets;

import com.black_dog20.jetboots.client.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class PacketSyncSound {
    private final UUID uuid;

    public PacketSyncSound(UUID uuid) {
        this.uuid = uuid;
    }

    public static void encode(PacketSyncSound msg, FriendlyByteBuf buffer) {
        buffer.writeUtf(msg.uuid.toString());
    }

    public static PacketSyncSound decode(FriendlyByteBuf buffer) {
        return new PacketSyncSound(UUID.fromString(buffer.readUtf()));
    }

    public static class Handler {
        public static void handle(PacketSyncSound msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
                Player player = Minecraft.getInstance().level.getPlayerByUUID(msg.uuid);
                ClientHelper.play(player);
            }));

            ctx.get().setPacketHandled(true);
        }
    }
}
