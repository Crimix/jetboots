package com.black_dog20.jetboots.client.events;

import com.black_dog20.bml.event.ArmorEvent;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.client.ClientHelper;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.util.EnergyUtil;
import com.black_dog20.jetboots.common.util.GuardinanHelmetProperties;
import com.black_dog20.jetboots.common.util.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.black_dog20.jetboots.common.util.TranslationHelper.*;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID, value = Dist.CLIENT)
public class ClientHandler {

    @SubscribeEvent
    public void onStopTracking(PlayerEvent.StopTracking event) {
        ClientHelper.stop(event.getPlayer());
    }

    @SubscribeEvent
    public static void overlayEvent(RenderGameOverlayEvent.Pre event) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null && ModUtils.hasGuardianHelmet(player)) {
            if (GuardinanHelmetProperties.getMode(ModUtils.getGuardianHelmet(player))) {
                if (event.getType() == RenderGameOverlayEvent.ElementType.AIR)
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
        if (event.player.getUniqueID() != Minecraft.getInstance().player.getUniqueID())
            return;

        if (tickCounter % 240 == 0) {
            ItemStack jetboots = event.armor;
            int percent = EnergyUtil.getBatteryPercentage(jetboots);
            if (percent < 10) {
                Minecraft.getInstance().ingameGUI.setOverlayMessage(Translations.POWER_LOW.get(), false);
            }
            tickCounter = 1;
        }

        tickCounter++;
    }
}
