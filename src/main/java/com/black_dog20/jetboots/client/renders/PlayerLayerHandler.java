package com.black_dog20.jetboots.client.renders;

import com.black_dog20.jetboots.Jetboots;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PlayerLayerHandler {

    @SubscribeEvent
    public static void setupRenders(EntityRenderersEvent.AddLayers event) {
        PlayerRenderer playerRendererDefault = event.getSkin("default");
        PlayerRenderer playerRendererSlim = event.getSkin("slim");

        if (playerRendererDefault != null) {
            playerRendererDefault.addLayer(new GuardianLayerRender<>(playerRendererDefault, event.getEntityModels(), false));
        }
        if (playerRendererSlim != null) {
            playerRendererSlim.addLayer(new GuardianLayerRender<>(playerRendererSlim, event.getEntityModels(), true));
        }
    }
}
