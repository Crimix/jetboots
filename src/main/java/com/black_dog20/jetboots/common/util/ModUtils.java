package com.black_dog20.jetboots.common.util;

import com.black_dog20.bml.utils.text.TextComponentBuilder;
import com.black_dog20.jetboots.common.items.equipment.GuardianHelmetItem;
import com.black_dog20.jetboots.common.items.equipment.JetBootsItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.function.Supplier;

import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.*;

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

    public static boolean canUseElytraFlight(PlayerEntity player) {
        if (!hasJetBoots(player))
            return false;
        else {
            ItemStack jetboots = getJetBoots(player);
            return canFlyWithBoots(player) && useElytraFlight(player, jetboots);
        }
    }

    public static boolean useElytraFlight(PlayerEntity player, ItemStack jetboots) {
        return startUseElytraFlight(jetboots) && (isTwoBlocksOverGround(player) || (player.isInWater() && JetBootsProperties.hasUnderWaterUpgrade(jetboots)));
    }

    private static boolean startUseElytraFlight(ItemStack jetboots) {
        return JetBootsProperties.hasEngineUpgrade(jetboots) && JetBootsProperties.getMode(jetboots);
    }

    private static boolean isTwoBlocksOverGround(PlayerEntity player) {
        if (player.isInWater()) {
            return true;
        }

        BlockPos blockPos = new BlockPos(player.getPosition());
        for (int i = 0; i < 3; i++) {
            if (player.world.isAirBlock(blockPos.down()))
                blockPos = blockPos.down();
            else
                break;
        }

        return blockPos.distanceSq(player.getPosX(), player.getPosY(), player.getPosZ(), false) > 1.9;
    }

    public static ITextComponent getFlightModeText(PlayerEntity player) {
        ItemStack stack = ModUtils.getJetBoots(player);
        return getFlightModeText(stack, TextFormatting.WHITE);
    }

    public static ITextComponent getFlightModeText(ItemStack stack, TextFormatting color) {
        if (!stack.isEmpty()) {
            Supplier<Boolean> elytraOn = () -> JetBootsProperties.getMode(stack);

            ITextComponent elytra = ELYTRA.get(TextFormatting.LIGHT_PURPLE);
            ITextComponent normal = NORMAL.get(TextFormatting.GREEN);
            return TextComponentBuilder.of(FLIGHT_MODE)
                    .format(color)
                    .with(":")
                    .format(color)
                    .space()
                    .with(elytra, normal, elytraOn)
                    .build();
        }
        return new StringTextComponent("");
    }

    public static ITextComponent getFlightSpeedText(PlayerEntity player) {
        ItemStack stack = ModUtils.getJetBoots(player);
        return getFlightSpeedText(stack, TextFormatting.WHITE);
    }

    public static ITextComponent getFlightSpeedText(ItemStack stack, TextFormatting color) {
        if (!stack.isEmpty()) {
            Supplier<Boolean> superSpeedOn = () -> JetBootsProperties.getSpeed(stack);

            ITextComponent superSpeed = SUPER.get(TextFormatting.RED);
            ITextComponent normal = NORMAL.get(TextFormatting.GREEN);
            return TextComponentBuilder.of(SPEED_MODE)
                    .format(color)
                    .with(":")
                    .format(color)
                    .space()
                    .with(superSpeed, normal, superSpeedOn)
                    .build();
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
            Supplier<Boolean> materializedOn = () -> GuardinanHelmetProperties.getMode(helmet);

            ITextComponent materialized = MATERIALIZED.get(TextFormatting.LIGHT_PURPLE);
            ITextComponent dematerialized = DEMATERIALIZED.get(TextFormatting.BLUE);
            return TextComponentBuilder.of(HELMET_MODE)
                    .with(":")
                    .space()
                    .with(materialized, dematerialized, materializedOn)
                    .build();
        }
        return new StringTextComponent("");
    }

    public static ITextComponent getHelmetNightVisionText(PlayerEntity player) {
        ItemStack stack = ModUtils.getGuardianHelmet(player);
        return getHelmetNightVisionText(stack);
    }

    public static ITextComponent getHelmetNightVisionText(ItemStack helmet) {
        if (!helmet.isEmpty()) {
            Supplier<Boolean> nightVisionOn = () -> GuardinanHelmetProperties.getMode(helmet);

            ITextComponent on = ON.get(TextFormatting.GREEN);
            ITextComponent off = OFF.get(TextFormatting.RED);
            return TextComponentBuilder.of(HELMET_NIGHT_VISION)
                    .with(":")
                    .space()
                    .with(on, off, nightVisionOn)
                    .build();
        }
        return new StringTextComponent("");
    }
}
