package com.black_dog20.jetboots.common.events;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.network.PacketHandler;
import com.black_dog20.jetboots.common.network.packets.PacketSyncPartical;
import com.black_dog20.jetboots.common.network.packets.PacketSyncSound;
import com.black_dog20.jetboots.common.network.packets.PacketSyncStopSound;
import com.black_dog20.jetboots.common.util.JetBootsProperties;
import com.black_dog20.jetboots.common.util.JetbootsUtil;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID)
public class ServerHandler {

	@SubscribeEvent
	public static void onClientTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			if(event.player instanceof PlayerEntity) {
				PlayerEntity player = event.player;
				if(event.side.isServer()) {
					if (player != null && player.world != null && JetbootsUtil.isFlying(player)) {
						PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(()-> player), new PacketSyncPartical(player.getUniqueID(), player.abilities.isFlying));
						ItemStack jetboots = JetbootsUtil.getJetBoots(player);
						if(!JetBootsProperties.getMuffledUpgrade(jetboots)) {
							if (!player.isInWater()) {
								PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(()-> player), new PacketSyncSound(player.getUniqueID()));
							} else {
								PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(()-> player), new PacketSyncStopSound(player.getUniqueID()));
							}
						}
					} else {
						PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(()-> player), new PacketSyncStopSound(player.getUniqueID()));
					}
				}
			}
		}
	}
}

