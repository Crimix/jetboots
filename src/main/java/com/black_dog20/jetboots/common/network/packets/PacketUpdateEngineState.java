package com.black_dog20.jetboots.common.network.packets;

import com.black_dog20.jetboots.common.util.ModUtils;
import com.black_dog20.jetboots.common.util.properties.RocketBootsProperties;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateEngineState {
    public PacketUpdateEngineState() {
    }

    public static void encode(PacketUpdateEngineState msg, FriendlyByteBuf buffer) {

    }

    public static PacketUpdateEngineState decode(FriendlyByteBuf buffer) {
        return new PacketUpdateEngineState();
    }

    public static class Handler {
        public static void handle(PacketUpdateEngineState msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player == null)
                    return;

                ItemStack stack = ModUtils.getRocketBoots(player);

                if (!stack.isEmpty()) {
                    RocketBootsProperties.setEngineState(stack, !RocketBootsProperties.getEngineState(stack));
                    player.displayClientMessage(ModUtils.getEngineStateText(player), true);
                }
            });

            ctx.get().setPacketHandled(true);
        }
    }
}
