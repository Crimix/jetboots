package com.black_dog20.jetboots.common.network.packets;

import java.util.function.Supplier;

import com.black_dog20.jetboots.common.util.JetBootsProperties;
import com.black_dog20.jetboots.common.util.JetbootsUtil;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

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

                ItemStack stack = JetbootsUtil.getJetBoots(player);
                
                if(!stack.isEmpty())
                	if(JetBootsProperties.getThrusterUpgrade(stack))
                		JetBootsProperties.setSpeed(stack, !JetBootsProperties.getSpeed(stack));
            });

            ctx.get().setPacketHandled(true);
        }
    }
}
