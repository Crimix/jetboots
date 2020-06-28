package com.black_dog20.jetboots.common.network.packets;

import com.black_dog20.jetboots.common.util.JetBootsProperties;
import com.black_dog20.jetboots.common.util.ModUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateFlightSpeed {
    public PacketUpdateFlightSpeed() {
    }

    public static void encode(PacketUpdateFlightSpeed msg, PacketBuffer buffer) {

    }

    public static PacketUpdateFlightSpeed decode(PacketBuffer buffer) {
        return new PacketUpdateFlightSpeed();
    }

    public static class Handler {
        public static void handle(PacketUpdateFlightSpeed msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();
                if (player == null)
                    return;

                ItemStack stack = ModUtils.getJetBoots(player);

                if (!stack.isEmpty())
                    if (JetBootsProperties.hasThrusterUpgrade(stack)) {
                        JetBootsProperties.setSpeed(stack, !JetBootsProperties.getSpeed(stack));
                        player.sendStatusMessage(ModUtils.getFlightSpeedText(player), true);
                    }
            });

            ctx.get().setPacketHandled(true);
        }
    }
}
