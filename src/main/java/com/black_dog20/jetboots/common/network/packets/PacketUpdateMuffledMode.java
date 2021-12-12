package com.black_dog20.jetboots.common.network.packets;

import com.black_dog20.jetboots.common.items.equipment.JetBootsItem;
import com.black_dog20.jetboots.common.util.properties.JetBootsProperties;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateMuffledMode {
    public PacketUpdateMuffledMode() {
    }

    public static void encode(PacketUpdateMuffledMode msg, FriendlyByteBuf buffer) {

    }

    public static PacketUpdateMuffledMode decode(FriendlyByteBuf buffer) {
        return new PacketUpdateMuffledMode();
    }

    public static class Handler {
        public static void handle(PacketUpdateMuffledMode msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player == null)
                    return;

                ItemStack stack = player.getMainHandItem();

                if (!stack.isEmpty() && stack.getItem() instanceof JetBootsItem) {
                    JetBootsProperties.setActiveMuffledUpgrade(stack, !JetBootsProperties.hasActiveMuffledUpgrade(stack));
                }
            });

            ctx.get().setPacketHandled(true);
        }
    }
}
