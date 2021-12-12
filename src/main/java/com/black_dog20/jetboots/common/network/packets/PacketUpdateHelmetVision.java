package com.black_dog20.jetboots.common.network.packets;

import com.black_dog20.jetboots.common.util.ModUtils;
import com.black_dog20.jetboots.common.util.properties.GuardinanHelmetProperties;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateHelmetVision {
    public PacketUpdateHelmetVision() {
    }

    public static void encode(PacketUpdateHelmetVision msg, FriendlyByteBuf buffer) {

    }

    public static PacketUpdateHelmetVision decode(FriendlyByteBuf buffer) {
        return new PacketUpdateHelmetVision();
    }

    public static class Handler {
        public static void handle(PacketUpdateHelmetVision msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player == null)
                    return;

                ItemStack stack = ModUtils.getGuardianHelmet(player);

                if (!stack.isEmpty() && GuardinanHelmetProperties.getMode(stack)) {
                    GuardinanHelmetProperties.setNightVision(stack, !GuardinanHelmetProperties.getNightVision(stack));
                    player.displayClientMessage(ModUtils.getHelmetNightVisionText(player), true);
                }
            });

            ctx.get().setPacketHandled(true);
        }
    }
}
