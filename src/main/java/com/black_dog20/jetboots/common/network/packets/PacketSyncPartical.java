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

public class PacketSyncPartical {
    private final UUID uuid;
    private final boolean flying;

    public PacketSyncPartical(UUID uuid, boolean flying) {
        this.uuid = uuid;
        this.flying = flying;
    }

    public static void encode(PacketSyncPartical msg, FriendlyByteBuf buffer) {
        buffer.writeUtf(msg.uuid.toString());
        buffer.writeBoolean(msg.flying);
    }

    public static PacketSyncPartical decode(FriendlyByteBuf buffer) {
        return new PacketSyncPartical(UUID.fromString(buffer.readUtf()), buffer.readBoolean());
    }

    public static class Handler {
        public static void handle(PacketSyncPartical msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
                Player player = Minecraft.getInstance().level.getPlayerByUUID(msg.uuid);
                ClientHelper.spawnJetParticals(Minecraft.getInstance(), player, msg.flying);
            }));

            ctx.get().setPacketHandled(true);
        }
    }
}
