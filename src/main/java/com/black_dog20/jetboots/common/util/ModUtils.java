package com.black_dog20.jetboots.common.util;

import com.black_dog20.jetboots.common.items.GuardianHelmetItem;
import com.black_dog20.jetboots.common.items.JetBootsItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import static com.black_dog20.jetboots.common.util.TranslationHelper.Tooltips.*;

public class ModUtils {

    public static boolean isFlying(PlayerEntity player) {
        return hasJetBoots(player) && (player.abilities.isFlying || player.isElytraFlying());
    }

    public static boolean hasJetBoots(PlayerEntity player) {
        return player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() instanceof JetBootsItem;
    }

    public static ItemStack getJetBoots(PlayerEntity player) {
        if (!hasJetBoots(player))
            return ItemStack.EMPTY;
        return player.getItemStackFromSlot(EquipmentSlotType.FEET);
    }

    public static boolean canFlyWithBoots(PlayerEntity player) {
        if (!hasJetBoots(player))
            return false;
        else {
            ItemStack jetboots = getJetBoots(player);
            if (player.isInWater() && !JetBootsProperties.hasUnderWaterUpgrade(jetboots)) {
                return false;
            }
            IEnergyStorage energy = jetboots.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
            if (energy != null) {
                return energy.getEnergyStored() >= EnergyUtil.getEnergyWhileFlying(jetboots);
            } else
                return false;
        }
    }

    public static ITextComponent getFlightModeText(PlayerEntity player) {
        ItemStack stack = ModUtils.getJetBoots(player);
        return getFlightModeText(stack);
    }

    public static ITextComponent getFlightModeText(ItemStack stack) {
        if (!stack.isEmpty()) {
            String mode = "";
            if (JetBootsProperties.getMode(stack)) {
                mode = TranslationHelper.translateToString(ELYTRA, TextFormatting.LIGHT_PURPLE);
            } else {
                mode = TranslationHelper.translateToString(NORMAL, TextFormatting.GREEN);
            }
            return TranslationHelper.translate(FLIGHT_MODE, TextFormatting.WHITE, mode);
        }
        return new StringTextComponent("");
    }

    public static ITextComponent getFlightSpeedText(PlayerEntity player) {
        ItemStack stack = ModUtils.getJetBoots(player);
        return getFlightSpeedText(stack);
    }

    public static ITextComponent getFlightSpeedText(ItemStack stack) {
        if (!stack.isEmpty()) {
            String mode = "";
            if (JetBootsProperties.getSpeed(stack)) {
                mode = TranslationHelper.translateToString(SUPER, TextFormatting.RED);
            } else {
                mode = TranslationHelper.translateToString(NORMAL, TextFormatting.GREEN);
            }
            return TranslationHelper.translate(SPEED_MODE, TextFormatting.WHITE, mode);
        }
        return new StringTextComponent("");
    }

    public static boolean hasGuardianHelmet(PlayerEntity player) {
        return player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() instanceof GuardianHelmetItem;
    }

    public static ItemStack getGuardianHelmet(PlayerEntity player) {
        if (!hasGuardianHelmet(player))
            return ItemStack.EMPTY;
        return player.getItemStackFromSlot(EquipmentSlotType.HEAD);
    }

    public static ITextComponent getHelmetModeText(PlayerEntity player) {
        ItemStack stack = ModUtils.getGuardianHelmet(player);
        return getHelmetModeText(stack);
    }

    public static ITextComponent getHelmetModeText(ItemStack helmet) {
        if (!helmet.isEmpty()) {
            String mode = "";
            if (GuardinanHelmetProperties.getMode(helmet)) {
                mode = TranslationHelper.translateToString(MATERIALIZED, TextFormatting.LIGHT_PURPLE);
            } else {
                mode = TranslationHelper.translateToString(DEMATERIALIZED, TextFormatting.BLUE);
            }
            return TranslationHelper.translate(HELMET_MODE, TextFormatting.WHITE, mode);
        }
        return new StringTextComponent("");
    }
}
