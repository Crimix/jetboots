package com.black_dog20.jetboots.common.util;

import com.black_dog20.bml.utils.leveling.ItemLevelProperties;
import com.black_dog20.bml.utils.math.MathUtil;
import com.black_dog20.bml.utils.text.TextComponentBuilder;
import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.common.items.equipment.JetBootsItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.Optional;

import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.FLYING_ENERGY;

public class EnergyUtil {

    public static int getEnergyWhileFlying(ItemStack jetboots) {
        if (jetboots.isEmpty() || !(jetboots.getItem() instanceof JetBootsItem))
            return 0;
        double modifier = ItemLevelProperties.calculateValue(1.0, 0.5, jetboots);
        modifier = MathUtil.clamp(modifier, 0.0, 1.0);
        return (int) Math.ceil(-Math.abs(Config.POWER_COST.get()) * modifier);
    }

    public static void extractOrReceive(IEnergyStorage energyStorage, int value) {
        if (value < 0)
            energyStorage.extractEnergy(Math.abs(value), false);
        else
            energyStorage.receiveEnergy(value, false);
    }

    public static int getBatteryPercentage(ItemStack jetboots) {
        IEnergyStorage energy = jetboots.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
        if (energy != null)
            return Math.min((int) (((float) energy.getEnergyStored() / energy.getMaxEnergyStored()) * 100), 100);
        return 0;
    }

    public static Optional<Component> getFormattedEnergyCost(ItemStack jetboots) {
        int value = getEnergyWhileFlying(jetboots);
        if (value == 0)
            return Optional.empty();
        else {
            Component textComponent = TextComponentBuilder.of(FLYING_ENERGY)
                    .format(ChatFormatting.GRAY)
                    .with(":")
                    .format(ChatFormatting.GRAY)
                    .space()
                    .with(String.format("-%d FE", Math.abs(value)))
                    .format(ChatFormatting.RED)
                    .build();
            return Optional.of(textComponent);
        }
    }
}
