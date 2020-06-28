package com.black_dog20.jetboots.common.network.packets;

import com.black_dog20.jetboots.common.util.GuardinanHelmetProperties;
import com.black_dog20.jetboots.common.util.ModUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateHelmetMode {
    public PacketUpdateHelmetMode() {
    }

    public static void encode(PacketUpdateHelmetMode msg, PacketBuffer buffer) {

    }

    public static PacketUpdateHelmetMode decode(PacketBuffer buffer) {
        return new PacketUpdateHelmetMode();
    }

    public static class Handler {
        public static void handle(PacketUpdateHelmetMode msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();
                if (player == null)
                    return;

                ItemStack stack = ModUtils.getGuardianHelmet(player);

                if (!stack.isEmpty()) {
                    GuardinanHelmetProperties.setMode(stack, !GuardinanHelmetProperties.getMode(stack));
                    player.sendStatusMessage(ModUtils.getHelmetModeText(player), true);
                }
            });

            ctx.get().setPacketHandled(true);
        }
    }
}
