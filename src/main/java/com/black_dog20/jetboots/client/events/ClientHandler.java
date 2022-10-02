package com.black_dog20.jetboots.client.events;

import com.black_dog20.bml.event.ArmorEvent;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.client.ClientHelper;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.util.EnergyUtil;
import com.black_dog20.jetboots.common.util.ModUtils;
import com.black_dog20.jetboots.common.util.TranslationHelper.Translations;
import com.black_dog20.jetboots.common.util.properties.GuardinanHelmetProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID, value = Dist.CLIENT)
public class ClientHandler {

    @SubscribeEvent
    public void onStopTracking(PlayerEvent.StopTracking event) {
        if (event.getTarget() instanceof Player player) {
            ClientHelper.stop(player);
        }
    }

    @SubscribeEvent
    public static void overlayEvent(RenderGuiOverlayEvent.Pre event) {
        Player player = Minecraft.getInstance().player;
        if (player != null && ModUtils.hasGuardianHelmet(player)) {
            if (GuardinanHelmetProperties.getMode(ModUtils.getGuardianHelmet(player))) {
                if (event.getOverlay().id().equals(VanillaGuiOverlay.AIR_LEVEL.id()))
                    event.setCanceled(true);
            }
        }
    }

    private static int tickCounter = 0;

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onArmorTick(ArmorEvent.Tick event) {
        if (!event.isArmorEqualTo(ModItems.JET_BOOTS.get()))
            return;
        if (event.phase != TickEvent.Phase.START)
            return;
        if(Minecraft.getInstance().player == null)
            return;
        if (event.player.getUUID() != Minecraft.getInstance().player.getUUID())
            return;

        if (tickCounter % 240 == 0) {
            ItemStack jetboots = event.armor;
            int percent = EnergyUtil.getBatteryPercentage(jetboots);
            if (percent < 10) {
                Minecraft.getInstance().gui.setOverlayMessage(Translations.POWER_LOW.get(), false);
            }
            tickCounter = 1;
        }

        tickCounter++;
    }
}
