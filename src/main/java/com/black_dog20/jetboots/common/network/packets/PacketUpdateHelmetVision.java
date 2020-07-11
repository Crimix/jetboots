package com.black_dog20.jetboots.common.network.packets;

import com.black_dog20.jetboots.common.util.GuardinanHelmetProperties;
import com.black_dog20.jetboots.common.util.ModUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateHelmetVision {
    public PacketUpdateHelmetVision() {
    }

    public static void encode(PacketUpdateHelmetVision msg, PacketBuffer buffer) {

    }

    public static PacketUpdateHelmetVision decode(PacketBuffer buffer) {
        return new PacketUpdateHelmetVision();
    }

    public static class Handler {
        public static void handle(PacketUpdateHelmetVision msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();
                if (player == null)
                    return;

                ItemStack stack = ModUtils.getGuardianHelmet(player);

                if (!stack.isEmpty() && GuardinanHelmetProperties.getMode(stack)) {
                    GuardinanHelmetProperties.setNightVision(stack, !GuardinanHelmetProperties.getNightVision(stack));
                    player.sendStatusMessage(ModUtils.getHelmetNightVisionText(player), true);
                }
            });

            ctx.get().setPacketHandled(true);
        }
    }
}
