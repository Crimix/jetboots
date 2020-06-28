package com.black_dog20.jetboots.client;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.upgrades.api.IFlatValueEnergyModifier;
import com.black_dog20.jetboots.common.items.upgrades.api.IPercentageValueEnergyModifier;
import com.black_dog20.jetboots.common.util.GuardinanHelmetProperties;
import com.black_dog20.jetboots.common.util.ModUtils;
import com.black_dog20.jetboots.common.util.TranslationHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.black_dog20.jetboots.common.util.TranslationHelper.*;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler {

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

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent e) {
        if (e.getItemStack().isEmpty())
            return;
        if (e.getItemStack().getItem() instanceof IFlatValueEnergyModifier) {
            List<ITextComponent> tooltips = e.getToolTip();
            IFlatValueEnergyModifier modifier = (IFlatValueEnergyModifier) e.getItemStack().getItem();
            if (modifier.getFlatModifierType() == null)
                return;
            switch (modifier.getFlatModifierType()) {
                case ON_USE:
                    tooltips.add(getFlatModifierText(Tooltips.ENERGY_USE_DRAW, Tooltips.ENERGY_USE_GENERATE, modifier));
                    break;
                case ON_HURT:
                    tooltips.add(getFlatModifierText(Tooltips.ENERGY_HURT_DRAW, Tooltips.ENERGY_HURT_GENERATE, modifier));
                    break;
                case ON_HIT:
                    tooltips.add(getFlatModifierText(Tooltips.ENERGY_HIT_DRAW, Tooltips.ENERGY_HIT_GENERATE, modifier));
                    break;
                case ON_WALK:
                    tooltips.add(getFlatModifierText(Tooltips.ENERGY_WALK_DRAW, Tooltips.ENERGY_WALK_GENERATE, modifier));
                    break;
            }
        } else if (e.getItemStack().getItem() instanceof IPercentageValueEnergyModifier) {
            List<ITextComponent> tooltips = e.getToolTip();
            IPercentageValueEnergyModifier modifier = (IPercentageValueEnergyModifier) e.getItemStack().getItem();
            if (modifier.getPercentageModifierType() == null)
                return;
            switch (modifier.getPercentageModifierType()) {
                case ON_USE:
                    tooltips.add(getPercentageModifierText(Tooltips.ENERGY_USE_MORE, Tooltips.ENERGY_USE_LESS, modifier));
                    break;
            }
        }
    }

    private static ITextComponent getFlatModifierText(Tooltips draw, Tooltips generate, IFlatValueEnergyModifier modifier) {
        int value = modifier.getFlatEnergyModifier();
        if (value < 0)
            return TranslationHelper.translate(draw, Math.abs(modifier.getFlatEnergyModifier()));
        else
            return TranslationHelper.translate(generate, modifier.getFlatEnergyModifier());
    }

    private static ITextComponent getPercentageModifierText(Tooltips more, Tooltips less, IPercentageValueEnergyModifier modifier) {
        double value = Math.max(modifier.getPercentageEnergyModifier(), 0);
        if (value < 1.0)
            return TranslationHelper.translate(less, getPercentageEnergyModifierDisplayValue(modifier));
        else
            return TranslationHelper.translate(more, getPercentageEnergyModifierDisplayValue(modifier));
    }

    private static double getPercentageEnergyModifierDisplayValue(IPercentageValueEnergyModifier modifier) {
        double value = Math.max(modifier.getPercentageEnergyModifier(), 0);
        if (value < 1.0)
            return (1.0 - value) * 100;
        else
            return value * 100;
    }
}
