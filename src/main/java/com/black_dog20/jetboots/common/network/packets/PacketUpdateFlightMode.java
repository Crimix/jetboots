package com.black_dog20.jetboots.common.network.packets;

import com.black_dog20.jetboots.common.util.JetBootsProperties;
import com.black_dog20.jetboots.common.util.JetbootsUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateFlightMode {
	public PacketUpdateFlightMode() {
    }

    public static void encode(PacketUpdateFlightMode msg, PacketBuffer buffer) {

    }

    public static PacketUpdateFlightMode decode(PacketBuffer buffer) {
        return new PacketUpdateFlightMode();
    }

    public static class Handler {
        public static void handle(PacketUpdateFlightMode msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();
                if (player == null)
                    return;

                ItemStack stack = JetbootsUtil.getJetBoots(player);
                
                if(!stack.isEmpty())
                	if(JetBootsProperties.hasEngineUpgrade(stack)) {
                		JetBootsProperties.setMode(stack, !JetBootsProperties.getMode(stack));
                		player.sendStatusMessage(JetbootsUtil.getFlightModeText(player), true);
                	}
            });

            ctx.get().setPacketHandled(true);
        }
    }
}
