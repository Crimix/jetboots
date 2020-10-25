package com.black_dog20.jetboots.client;

import com.black_dog20.bml.client.overlay.OverlayRegistry;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.client.containers.ModContainers;
import com.black_dog20.jetboots.client.keybinds.Keybinds;
import com.black_dog20.jetboots.client.overlay.BatteryIconOverlay;
import com.black_dog20.jetboots.client.screens.JetBootsScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber( modid = Jetboots.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientStartup {

    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(Keybinds.keyMode);
        ClientRegistry.registerKeyBinding(Keybinds.keySpeed);
        ClientRegistry.registerKeyBinding(Keybinds.keyHelmetMode);
        ClientRegistry.registerKeyBinding(Keybinds.keyHelmetVision);
        ScreenManager.registerFactory(ModContainers.JETBOOTS_CONTAINER.get(), JetBootsScreen::new);
        OverlayRegistry.register(new BatteryIconOverlay());
    }
}
