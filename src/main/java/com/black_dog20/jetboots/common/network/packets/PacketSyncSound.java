package com.black_dog20.jetboots.common.network.packets;

import java.util.UUID;
import java.util.function.Supplier;

import com.black_dog20.jetboots.client.ClientHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketSyncSound {
	private final UUID uuid;
	
	public PacketSyncSound(UUID uuid) {
		this.uuid = uuid;
    }

    public static void encode(PacketSyncSound msg, PacketBuffer buffer) {
    	buffer.writeString(msg.uuid.toString());
    }

    public static PacketSyncSound decode(PacketBuffer buffer) {
        return new PacketSyncSound(UUID.fromString(buffer.readString()));
    }

    public static class Handler {
        public static void handle(PacketSyncSound msg, Supplier<NetworkEvent.Context> ctx) {
        	ctx.get().enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
          		PlayerEntity player = Minecraft.getInstance().world.getPlayerByUuid(msg.uuid);
          		ClientHelper.play(player);
        	}));

            ctx.get().setPacketHandled(true);
        }
    }
}
