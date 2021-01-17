package com.black_dog20.jetboots.common.network.packets;

import com.black_dog20.jetboots.common.items.equipment.JetBootsItem;
import com.black_dog20.jetboots.common.util.JetBootsProperties;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateMuffledMode {
    public PacketUpdateMuffledMode() {
    }

    public static void encode(PacketUpdateMuffledMode msg, PacketBuffer buffer) {

    }

    public static PacketUpdateMuffledMode decode(PacketBuffer buffer) {
        return new PacketUpdateMuffledMode();
    }

    public static class Handler {
        public static void handle(PacketUpdateMuffledMode msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();
                if (player == null)
                    return;

                ItemStack stack = player.getHeldItemMainhand();

                if (!stack.isEmpty() && stack.getItem() instanceof JetBootsItem) {
                    JetBootsProperties.setActiveMuffledUpgrade(stack, !JetBootsProperties.hasActiveMuffledUpgrade(stack));
                }
            });

            ctx.get().setPacketHandled(true);
        }
    }
}
