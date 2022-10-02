package com.black_dog20.jetboots.client;

import com.black_dog20.bml.client.overlay.OverlayRegistry;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.client.containers.ModContainers;
import com.black_dog20.jetboots.client.keybinds.Keybinds;
import com.black_dog20.jetboots.client.overlay.BatteryIconOverlay;
import com.black_dog20.jetboots.client.overlay.FlightBarOverlay;
import com.black_dog20.jetboots.client.screens.EnchantableItemScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber( modid = Jetboots.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientStartup {

    @SubscribeEvent
    public static void registerKeyBinding(RegisterKeyMappingsEvent event) {
        event.register(Keybinds.keyMode);
        event.register(Keybinds.keySpeed);
        event.register(Keybinds.keyHelmetMode);
        event.register(Keybinds.keyHelmetVision);
    }

    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        MenuScreens.register(ModContainers.ENCHANTABLE_ITEM_CONTAINER.get(), EnchantableItemScreen::new);
        OverlayRegistry.register(new BatteryIconOverlay());
    }

    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("flight_bar_overlay", new FlightBarOverlay());
    }
}
