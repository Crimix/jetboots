package com.black_dog20.jetboots.common.network.packets;

import com.black_dog20.jetboots.common.util.ModUtils;
import com.black_dog20.jetboots.common.util.properties.JetBootsProperties;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateFlightMode {
    public PacketUpdateFlightMode() {
    }

    public static void encode(PacketUpdateFlightMode msg, FriendlyByteBuf buffer) {

    }

    public static PacketUpdateFlightMode decode(FriendlyByteBuf buffer) {
        return new PacketUpdateFlightMode();
    }

    public static class Handler {
        public static void handle(PacketUpdateFlightMode msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player == null)
                    return;

                ItemStack stack = ModUtils.getJetBoots(player);

                if (!stack.isEmpty())
                    if (JetBootsProperties.hasEngineUpgrade(stack)) {
                        JetBootsProperties.setMode(stack, !JetBootsProperties.getMode(stack));
                        player.displayClientMessage(ModUtils.getFlightModeText(player), true);
                    }
            });

            ctx.get().setPacketHandled(true);
        }
    }
}
