package com.black_dog20.jetboots.client.events;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.client.keybinds.Keybinds;
import com.black_dog20.jetboots.common.network.PacketHandler;
import com.black_dog20.jetboots.common.network.packets.PacketUpdateFlightMode;
import com.black_dog20.jetboots.common.network.packets.PacketUpdateFlightSpeed;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID, value = Dist.CLIENT)
public class KeyEventHandler {

	@SubscribeEvent
	public static void onEvent(KeyInputEvent event)
	{
		if(Minecraft.getInstance().currentScreen == null) {
			if(Keybinds.keyMode.isPressed()) {
				PacketHandler.sendToServer(new PacketUpdateFlightMode());
			} else if(Keybinds.keySpeed.isPressed()) {
				PacketHandler.sendToServer(new PacketUpdateFlightSpeed());
			}
		}
	}
}
