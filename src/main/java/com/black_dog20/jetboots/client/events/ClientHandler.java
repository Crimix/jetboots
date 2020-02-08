package com.black_dog20.jetboots.client.events;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.client.ClientHelper;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID, value = Dist.CLIENT)
public class ClientHandler {

	@SubscribeEvent
	public void onStopTracking(PlayerEvent.StopTracking event) {
		ClientHelper.stop(event.getPlayer());
	}
}
