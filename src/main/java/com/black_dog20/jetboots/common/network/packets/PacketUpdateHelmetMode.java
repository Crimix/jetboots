package com.black_dog20.jetboots.common.network.packets;

import com.black_dog20.jetboots.common.util.ModUtils;
import com.black_dog20.jetboots.common.util.properties.GuardinanHelmetProperties;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateHelmetMode {
    public PacketUpdateHelmetMode() {
    }

    public static void encode(PacketUpdateHelmetMode msg, FriendlyByteBuf buffer) {

    }

    public static PacketUpdateHelmetMode decode(FriendlyByteBuf buffer) {
        return new PacketUpdateHelmetMode();
    }

    public static class Handler {
        public static void handle(PacketUpdateHelmetMode msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player == null)
                    return;

                ItemStack stack = ModUtils.getGuardianHelmet(player);

                if (!stack.isEmpty()) {
                    GuardinanHelmetProperties.setMode(stack, !GuardinanHelmetProperties.getMode(stack));
                    player.displayClientMessage(ModUtils.getHelmetModeText(player), true);
                }
            });

            ctx.get().setPacketHandled(true);
        }
    }
}
