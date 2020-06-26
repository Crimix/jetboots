package com.black_dog20.jetboots.client.renders;

import com.black_dog20.jetboots.Jetboots;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PlayerLayerHandler {

    @SubscribeEvent
    public static void setupRenders(FMLClientSetupEvent event) {
        PlayerRenderer playerRendererDefault = Minecraft.getInstance().getRenderManager().getSkinMap().get("default");
        PlayerRenderer playerRendererSlim = Minecraft.getInstance().getRenderManager().getSkinMap().get("slim");

        playerRendererDefault.addLayer(new GuardianHelmetLayerRender<>(playerRendererDefault));
        playerRendererSlim.addLayer(new GuardianHelmetLayerRender<>(playerRendererSlim));
    }
}
