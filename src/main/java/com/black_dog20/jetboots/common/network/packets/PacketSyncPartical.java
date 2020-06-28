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

public class PacketSyncPartical {
    private final UUID uuid;
    private final boolean flying;

    public PacketSyncPartical(UUID uuid, boolean flying) {
        this.uuid = uuid;
        this.flying = flying;
    }

    public static void encode(PacketSyncPartical msg, PacketBuffer buffer) {
        buffer.writeString(msg.uuid.toString());
        buffer.writeBoolean(msg.flying);
    }

    public static PacketSyncPartical decode(PacketBuffer buffer) {
        return new PacketSyncPartical(UUID.fromString(buffer.readString()), buffer.readBoolean());
    }

    public static class Handler {
        public static void handle(PacketSyncPartical msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
                PlayerEntity player = Minecraft.getInstance().world.getPlayerByUuid(msg.uuid);
                ClientHelper.spawnJetParticals(Minecraft.getInstance(), player, msg.flying);
            }));

            ctx.get().setPacketHandled(true);
        }
    }
}
