package com.black_dog20.jetboots.client.events;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.client.keybinds.Keybinds;
import com.black_dog20.jetboots.common.network.PacketHandler;
import com.black_dog20.jetboots.common.network.packets.PacketUpdateEngineState;
import com.black_dog20.jetboots.common.network.packets.PacketUpdateFlightMode;
import com.black_dog20.jetboots.common.network.packets.PacketUpdateFlightSpeed;
import com.black_dog20.jetboots.common.network.packets.PacketUpdateHelmetMode;
import com.black_dog20.jetboots.common.network.packets.PacketUpdateHelmetVision;
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
    public static void onEvent(KeyInputEvent event) {
        if (Minecraft.getInstance().screen == null) {
            if (Keybinds.keyMode.consumeClick()) {
                PacketHandler.sendToServer(new PacketUpdateFlightMode());
            } else if (Keybinds.keySpeed.consumeClick()) {
                PacketHandler.sendToServer(new PacketUpdateFlightSpeed());
            } else if (Keybinds.keyHelmetMode.consumeClick()) {
                PacketHandler.sendToServer(new PacketUpdateHelmetMode());
            } else if (Keybinds.keyHelmetVision.consumeClick()) {
                PacketHandler.sendToServer(new PacketUpdateHelmetVision());
            } else if (Keybinds.keyRocketBoots.consumeClick()) {
                 PacketHandler.sendToServer(new PacketUpdateEngineState());
            }
        }
    }
}
