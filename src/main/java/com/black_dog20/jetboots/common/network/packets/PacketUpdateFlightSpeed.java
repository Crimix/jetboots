package com.black_dog20.jetboots.common.network.packets;

import com.black_dog20.jetboots.common.util.ModUtils;
import com.black_dog20.jetboots.common.util.properties.JetBootsProperties;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateFlightSpeed {
    public PacketUpdateFlightSpeed() {
    }

    public static void encode(PacketUpdateFlightSpeed msg, FriendlyByteBuf buffer) {

    }

    public static PacketUpdateFlightSpeed decode(FriendlyByteBuf buffer) {
        return new PacketUpdateFlightSpeed();
    }

    public static class Handler {
        public static void handle(PacketUpdateFlightSpeed msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player == null)
                    return;

                ItemStack stack = ModUtils.getJetBoots(player);

                if (!stack.isEmpty())
                    if (JetBootsProperties.hasThrusterUpgrade(stack)) {
                        JetBootsProperties.setSpeed(stack, !JetBootsProperties.getSpeed(stack));
                        player.displayClientMessage(ModUtils.getFlightSpeedText(player), true);
                    }
            });

            ctx.get().setPacketHandled(true);
        }
    }
}
