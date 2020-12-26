package com.black_dog20.jetboots.common.util;

import com.black_dog20.bml.utils.math.MathUtil;
import com.black_dog20.bml.utils.text.TextComponentBuilder;
import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.common.items.equipment.JetBootsItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.Optional;

import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.*;

public class EnergyUtil {

    public static int getEnergyWhileFlying(ItemStack jetboots) {
        if (jetboots.isEmpty() || !(jetboots.getItem() instanceof JetBootsItem))
            return 0;
        double modifier = LevelProperties.calculateValue(1.0, 0.5, jetboots);
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

    public static Optional<ITextComponent> getFormattedEnergyCost(ItemStack jetboots) {
        int value = getEnergyWhileFlying(jetboots);
        if (value == 0)
            return Optional.empty();
        else {
            ITextComponent textComponent = TextComponentBuilder.of(FLYING_ENERGY)
                    .format(TextFormatting.GRAY)
                    .with(":")
                    .format(TextFormatting.GRAY)
                    .space()
                    .with(String.format("-%d FE", Math.abs(value)))
                    .format(TextFormatting.RED)
                    .build();
            return Optional.of(textComponent);
        }
    }
}
