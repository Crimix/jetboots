package com.black_dog20.jetboots.common.util;

import com.black_dog20.bml.utils.math.MathUtil;
import com.black_dog20.bml.utils.text.TextComponentBuilder;
import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.common.items.JetBootsItem;
import com.black_dog20.jetboots.common.items.upgrades.api.IFlatValueEnergyModifier;
import com.black_dog20.jetboots.common.items.upgrades.api.IPercentageValueEnergyModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.Optional;

import static com.black_dog20.jetboots.common.items.upgrades.api.IFlatValueEnergyModifier.*;
import static com.black_dog20.jetboots.common.items.upgrades.api.IPercentageValueEnergyModifier.*;

public class EnergyUtil {

    public static int getSumFlatValueEnergyModifier(ItemStack jetboots, FlatModifierType type) {
        if (jetboots.isEmpty() || !(jetboots.getItem() instanceof JetBootsItem))
            return 0;
        return JetBootsProperties.getFlatEnergyModifiers(jetboots).stream()
                .filter(e -> e.getFlatModifierType() == type)
                .map(IFlatValueEnergyModifier::getFlatEnergyModifier)
                .reduce(0, Integer::sum);
    }

    public static double getPercentageValueEnergyModifier(ItemStack jetboots, PercentageModifierType type) {
        if (jetboots.isEmpty() || !(jetboots.getItem() instanceof JetBootsItem))
            return 1;

        return JetBootsProperties.getPercentageEnergyModifiers(jetboots).stream()
                .filter(e -> e.getPercentageModifierType() == type)
                .map(IPercentageValueEnergyModifier::getPercentageEnergyModifier)
                .reduce(1.0, (a, b) -> a * b);
    }

    public static Optional<ITextComponent> getFormattedEnergyValue(int value) {
        if (value == 0)
            return Optional.empty();
        else if (value > 0) {
            ITextComponent textComponent = TextComponentBuilder.of(new StringTextComponent(String.format("+%d FE", Math.abs(value))))
                    .format(TextFormatting.GREEN)
                    .build();
            return Optional.of(textComponent);
        } else {
            ITextComponent textComponent = TextComponentBuilder.of(new StringTextComponent(String.format("-%d FE", Math.abs(value))))
                    .format(TextFormatting.RED)
                    .build();
            return Optional.of(textComponent);
        }
    }

    public static int getEnergyWhileFlying(ItemStack jetboots) {
        if (jetboots.isEmpty() || !(jetboots.getItem() instanceof JetBootsItem))
            return 0;
        double modifier = EnergyUtil.getPercentageValueEnergyModifier(jetboots, PercentageModifierType.ON_USE);
        modifier = MathUtil.clamp(modifier, 0.0, 5.0);
        int cost = (int) Math.ceil(-Config.POWER_COST.get() * modifier);
        return cost + EnergyUtil.getSumFlatValueEnergyModifier(jetboots, FlatModifierType.ON_USE);
    }

    public static int getEnergyOnHit(ItemStack jetboots) {
        if (jetboots.isEmpty() || !(jetboots.getItem() instanceof JetBootsItem))
            return 0;
        return EnergyUtil.getSumFlatValueEnergyModifier(jetboots, FlatModifierType.ON_HIT);
    }

    public static int getEnergyOnHurt(ItemStack jetboots) {
        if (jetboots.isEmpty() || !(jetboots.getItem() instanceof JetBootsItem))
            return 0;
        return EnergyUtil.getSumFlatValueEnergyModifier(jetboots, FlatModifierType.ON_HURT);
    }

    public static int getEnergyWhileWalking(ItemStack jetboots) {
        if (jetboots.isEmpty() || !(jetboots.getItem() instanceof JetBootsItem))
            return 0;
        return EnergyUtil.getSumFlatValueEnergyModifier(jetboots, FlatModifierType.ON_WALK);
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
}
