package com.black_dog20.jetboots.client.events;

import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.client.ClientHelper;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.items.upgrades.api.IFlatValueEnergyModifier;
import com.black_dog20.jetboots.common.items.upgrades.api.IPercentageValueEnergyModifier;
import com.black_dog20.jetboots.common.util.GuardinanHelmetProperties;
import com.black_dog20.jetboots.common.util.ModUtils;
import com.black_dog20.jetboots.common.util.TranslationHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

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

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent e) {
        if (e.getItemStack().isEmpty())
            return;
        List<ITextComponent> tooltips = e.getToolTip();
        Item module = e.getItemStack().getItem();

        if (module instanceof IPercentageValueEnergyModifier) {

            IPercentageValueEnergyModifier modifier = (IPercentageValueEnergyModifier) module;
            if (modifier.getPercentageModifierType() == null)
                return;
            switch (modifier.getPercentageModifierType()) {
                case ON_USE:
                    tooltips.add(getPercentageModifierText(TranslationHelper.Tooltips.ENERGY_USE_MORE, TranslationHelper.Tooltips.ENERGY_USE_LESS, modifier));
                    break;
            }
        }

        if (module instanceof IFlatValueEnergyModifier) {
            IFlatValueEnergyModifier modifier = (IFlatValueEnergyModifier) module;
            if (modifier.getFlatModifierType() == null)
                return;
            switch (modifier.getFlatModifierType()) {
                case ON_USE:
                    tooltips.add(getFlatModifierText(TranslationHelper.Tooltips.ENERGY_USE_DRAW, TranslationHelper.Tooltips.ENERGY_USE_GENERATE, modifier));
                    break;
                case ON_HURT:
                    tooltips.add(getFlatModifierText(TranslationHelper.Tooltips.ENERGY_HURT_DRAW, TranslationHelper.Tooltips.ENERGY_HURT_GENERATE, modifier));
                    break;
                case ON_HIT:
                    tooltips.add(getFlatModifierText(TranslationHelper.Tooltips.ENERGY_HIT_DRAW, TranslationHelper.Tooltips.ENERGY_HIT_GENERATE, modifier));
                    break;
                case ON_WALK:
                    tooltips.add(getFlatModifierText(TranslationHelper.Tooltips.ENERGY_WALK_DRAW, TranslationHelper.Tooltips.ENERGY_WALK_GENERATE, modifier));
                    break;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onTooltipLoot(ItemTooltipEvent e) {
        if (e.getItemStack().isEmpty())
            return;
        Item item = e.getItemStack().getItem();
        List<ITextComponent> tooltips = e.getToolTip();
        if (item.equals(ModItems.GUARDIAN_HELMET.get()) && !Config.HELMET_LOOT.get() ) {
           tooltips.add(TranslationHelper.translate(TranslationHelper.Tooltips.NO_CHANCE_TO_FIND));
        } else if (item.equals(ModItems.FORCEFIELD_PROJECTOR.get()) && !Config.FORCEFIELD_PARTS_LOOT.get() ) {
            tooltips.add(TranslationHelper.translate(TranslationHelper.Tooltips.NO_CHANCE_TO_FIND));
        } else if (item.equals(ModItems.FORCEFIELD_GENERATOR.get()) && !Config.FORCEFIELD_PARTS_LOOT.get()  ) {
            tooltips.add(TranslationHelper.translate(TranslationHelper.Tooltips.NO_CHANCE_TO_FIND));
        }
    }

    private static ITextComponent getFlatModifierText(TranslationHelper.Tooltips draw, TranslationHelper.Tooltips generate, IFlatValueEnergyModifier modifier) {
        int value = modifier.getFlatEnergyModifier();
        if (value < 0)
            return TranslationHelper.translate(draw, Math.abs(modifier.getFlatEnergyModifier()));
        else
            return TranslationHelper.translate(generate, modifier.getFlatEnergyModifier());
    }

    private static ITextComponent getPercentageModifierText(TranslationHelper.Tooltips more, TranslationHelper.Tooltips less, IPercentageValueEnergyModifier modifier) {
        double value = Math.max(modifier.getPercentageEnergyModifier(), 0);
        if (value < 1.0)
            return TranslationHelper.translate(less, getPercentageEnergyModifierDisplayValue(modifier));
        else
            return TranslationHelper.translate(more, getPercentageEnergyModifierDisplayValue(modifier));
    }

    private static double getPercentageEnergyModifierDisplayValue(IPercentageValueEnergyModifier modifier) {
        double value = Math.max(modifier.getPercentageEnergyModifier(), 0);
        if (value < 1.0)
            return Math.round((1.0 - value) * 100);
        else
            return Math.round(value * 100);
    }
}
