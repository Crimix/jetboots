package com.black_dog20.jetboots.common.util;

import com.black_dog20.bml.utils.text.TextComponentBuilder;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.items.equipment.GuardianHelmetItem;
import com.black_dog20.jetboots.common.items.equipment.JetBootsItem;
import com.black_dog20.jetboots.common.items.equipment.RocketBootsItem;
import com.black_dog20.jetboots.common.util.properties.GuardinanHelmetProperties;
import com.black_dog20.jetboots.common.util.properties.JetBootsProperties;
import com.black_dog20.jetboots.common.util.properties.RocketBootsProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.function.Supplier;

import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.*;

public class ModUtils {

    public static boolean isJetbootsFlying(Player player) {
        return hasJetBoots(player) && (player.getAbilities().flying || player.isFallFlying());
    }

    public static boolean hasJetBoots(Player player) {
        return player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof JetBootsItem;
    }

    public static ItemStack getJetBoots(Player player) {
        if (!hasJetBoots(player))
            return ItemStack.EMPTY;
        return player.getItemBySlot(EquipmentSlot.FEET);
    }

    public static boolean canFlyWithJetboots(Player player) {
        if (player.isCreative()){
            return true;
        }

        if (!hasJetBoots(player))
            return false;
        else {
            ItemStack jetboots = getJetBoots(player);
            if (player.isInWater() && !JetBootsProperties.hasUnderWaterUpgrade(jetboots)) {
                return false;
            }
            IEnergyStorage energy = jetboots.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
            if (energy != null) {
                return energy.getEnergyStored() + EnergyUtil.getEnergyWhileFlying(jetboots) >= 0; //Needs to be plus as the return of getEnergyWhileFlying is negative
            } else
                return false;
        }
    }

    public static boolean canUseElytraFlight(Player player) {
        if (!hasJetBoots(player))
            return false;
        else {
            ItemStack jetboots = getJetBoots(player);
            return canFlyWithJetboots(player) && useElytraFlight(player, jetboots);
        }
    }

    public static boolean useElytraFlight(Player player, ItemStack jetboots) {
        return startUseElytraFlight(jetboots) && (isTwoBlocksOverGround(player) || (player.isInWater() && JetBootsProperties.hasUnderWaterUpgrade(jetboots)));
    }

    private static boolean startUseElytraFlight(ItemStack jetboots) {
        return JetBootsProperties.hasEngineUpgrade(jetboots) && JetBootsProperties.getMode(jetboots);
    }

    private static boolean isTwoBlocksOverGround(Player player) {
        return isBlocksOverGround(player, 1.9, 3);
    }

    public static boolean isBlocksOverGround(Player player, double target, int max) {
        if (player.isInWater()) {
            return true;
        }

        BlockPos blockPos = new BlockPos(player.blockPosition());
        for (int i = 0; i < max; i++) {
            if (player.level.isEmptyBlock(blockPos.below()))
                blockPos = blockPos.below();
            else
                break;
        }

        return blockPos.distSqr(new Vec3i(player.getX(), player.getY(), player.getZ())) > target;
    }

    public static boolean isBetweenBlocksOverGround(Player player, double minTarget, double maxTarget) {
        if (player.isInWater()) {
            return true;
        }

        int max = (int) maxTarget + 1;

        BlockPos blockPos = new BlockPos(player.blockPosition());
        for (int i = 0; i < max; i++) {
            if (player.level.isEmptyBlock(blockPos.below()))
                blockPos = blockPos.below();
            else
                break;
        }

        double distance = blockPos.distSqr(new Vec3i(player.getX(), player.getY(), player.getZ()));

        return minTarget < distance && distance < maxTarget;
    }

    public static Component getFlightModeText(Player player) {
        ItemStack stack = ModUtils.getJetBoots(player);
        return getFlightModeText(stack, ChatFormatting.WHITE);
    }

    public static Component getFlightModeText(ItemStack stack, ChatFormatting color) {
        if (!stack.isEmpty()) {
            Supplier<Boolean> elytraOn = () -> JetBootsProperties.getMode(stack);

            Component elytra = ELYTRA.get(ChatFormatting.LIGHT_PURPLE);
            Component normal = NORMAL.get(ChatFormatting.GREEN);
            return TextComponentBuilder.of(FLIGHT_MODE)
                    .format(color)
                    .with(":")
                    .format(color)
                    .space()
                    .with(elytra, normal, elytraOn)
                    .build();
        }
        return Component.literal("");
    }

    public static Component getFlightSpeedText(Player player) {
        ItemStack stack = ModUtils.getJetBoots(player);
        return getFlightSpeedText(stack, ChatFormatting.WHITE);
    }

    public static Component getFlightSpeedText(ItemStack stack, ChatFormatting color) {
        if (!stack.isEmpty()) {
            Supplier<Boolean> superSpeedOn = () -> JetBootsProperties.getSpeed(stack);

            Component superSpeed = SUPER.get(ChatFormatting.RED);
            Component normal = NORMAL.get(ChatFormatting.GREEN);
            return TextComponentBuilder.of(SPEED_MODE)
                    .format(color)
                    .with(":")
                    .format(color)
                    .space()
                    .with(superSpeed, normal, superSpeedOn)
                    .build();
        }
        return Component.literal("");
    }

    public static boolean hasGuardianHelmet(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof GuardianHelmetItem;
    }

    public static ItemStack getGuardianHelmet(Player player) {
        if (!hasGuardianHelmet(player))
            return ItemStack.EMPTY;
        return player.getItemBySlot(EquipmentSlot.HEAD);
    }

    public static Component getHelmetModeText(Player player) {
        ItemStack stack = ModUtils.getGuardianHelmet(player);
        return getHelmetModeText(stack);
    }

    public static Component getHelmetModeText(ItemStack helmet) {
        if (!helmet.isEmpty()) {
            Supplier<Boolean> materializedOn = () -> GuardinanHelmetProperties.getMode(helmet);

            Component materialized = MATERIALIZED.get(ChatFormatting.LIGHT_PURPLE);
            Component dematerialized = DEMATERIALIZED.get(ChatFormatting.BLUE);
            return TextComponentBuilder.of(HELMET_MODE)
                    .with(":")
                    .space()
                    .with(materialized, dematerialized, materializedOn)
                    .build();
        }
        return Component.literal("");
    }

    public static Component getHelmetNightVisionText(Player player) {
        ItemStack stack = ModUtils.getGuardianHelmet(player);
        return getHelmetNightVisionText(stack);
    }

    public static Component getHelmetNightVisionText(ItemStack helmet) {
        if (!helmet.isEmpty()) {
            Supplier<Boolean> nightVisionOn = () -> GuardinanHelmetProperties.getNightVision(helmet);

            Component on = ON.get(ChatFormatting.GREEN);
            Component off = OFF.get(ChatFormatting.RED);
            return TextComponentBuilder.of(HELMET_NIGHT_VISION)
                    .with(":")
                    .space()
                    .with(on, off, nightVisionOn)
                    .build();
        }
        return Component.literal("");
    }

    public static boolean hasRocketBoots(Player player) {
        return player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof RocketBootsItem;
    }

    public static ItemStack getRocketBoots(Player player) {
        if (!hasRocketBoots(player))
            return ItemStack.EMPTY;
        return player.getItemBySlot(EquipmentSlot.FEET);
    }

    public static Component getEngineStateText(Player player) {
        ItemStack stack = ModUtils.getRocketBoots(player);
        return getEngineStateText(stack, ChatFormatting.WHITE);
    }

    public static Component getEngineStateText(ItemStack stack, ChatFormatting color) {
        if (!stack.isEmpty()) {
            Supplier<Boolean> isOn = () -> RocketBootsProperties.getEngineState(stack);

            Component on = ON.get(ChatFormatting.GREEN);
            Component off = OFF.get(ChatFormatting.RED);
            return TextComponentBuilder.of(ModItems.ROCKET_BOOTS.get().getName(stack))
                    .format(color)
                    .with(":")
                    .format(color)
                    .space()
                    .with(on, off, isOn)
                    .build();
        }
        return Component.literal("");
    }
}
