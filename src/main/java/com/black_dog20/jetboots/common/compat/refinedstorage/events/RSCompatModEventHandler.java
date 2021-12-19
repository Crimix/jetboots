package com.black_dog20.jetboots.common.compat.refinedstorage.events;

import com.black_dog20.jetboots.common.compat.refinedstorage.RefinedStorageCompat;
import com.black_dog20.jetboots.common.compat.refinedstorage.grids.WirelessCraftingUpgradeGridFactory;
import com.black_dog20.jetboots.common.compat.refinedstorage.network.RSCompatPacketHandler;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class RSCompatModEventHandler {

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event) {
        RefinedStorageCompat.RSAPI.getGridManager().add(WirelessCraftingUpgradeGridFactory.ID, new WirelessCraftingUpgradeGridFactory());
        RSCompatPacketHandler.register();
        MinecraftForge.EVENT_BUS.register(new RSCompatEventHandler());
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(RSCompatKeybindHandler.keyOpenCraftingGrid);

        MinecraftForge.EVENT_BUS.register(new RSCompatKeybindHandler());
        MinecraftForge.EVENT_BUS.register(new RSCompatTooltipsHandler());
    }
}
