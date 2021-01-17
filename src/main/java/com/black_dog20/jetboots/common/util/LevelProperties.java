package com.black_dog20.jetboots.common.util;

import com.black_dog20.bml.utils.item.NBTUtil;
import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.api.ILevelableItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.Optional;

import static com.black_dog20.jetboots.common.util.NBTTags.*;
import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.*;

public class LevelProperties {

    public static void addXp(PlayerEntity player, ItemStack tool, int xp) {
        if (canLevel(tool)) {
            ILevelableItem levelableItem = (ILevelableItem) tool.getItem();
            int currentLevel = getCurrentLevel(tool);
            int currentXP = getCurrentXp(tool);

            if (currentLevel == levelableItem.getMaxLevel()) {
                return;
            }

            int nextXp = currentXP + xp;
            int nextLevel = currentLevel;


            int xpForLevelUp = getXpToNextLevel(tool, currentLevel + 1);
            if (nextXp >=  xpForLevelUp) {
                nextXp -= xpForLevelUp;
                nextLevel++;
            }

            CompoundNBT compoundNBT = tool.getOrCreateTag();
            compoundNBT.putInt(TAG_XP, nextXp);
            if (currentLevel != nextLevel) {
                boolean wasSoulbound = isLevelAbovePercentage(levelableItem.getSoulboundLevel(), currentLevel, tool);
                boolean isSoulbound = isLevelAbovePercentage(levelableItem.getSoulboundLevel(), nextLevel, tool);
                compoundNBT.putInt(TAG_LEVEL, nextLevel);
                if (!wasSoulbound && isSoulbound) {
                    player.sendMessage(SOULBOUND_ACHIEVED.get(TextFormatting.AQUA, tool.getDisplayName().getString().toLowerCase()), player.getUniqueID());
                }
            }
        }
    }

    private static int getXpToNextLevel(ItemStack tool, int level) {
        if(level <= 1) {
            return Config.BASE_XP.get();
        }
        return (int) ((float) getXpToNextLevel(tool, level - 1) * Config.LEVEL_XP_MULTIPLIER.get());
    }

    public static ITextComponent getXpToNextLevel(ItemStack tool) {
        if (!isLevelable(tool)){
            return NOT_LEVELABLE.get();
        } else if(getCurrentLevel(tool) >= ((ILevelableItem)tool.getItem()).getMaxLevel()) {
            return MAX_LEVEL.get();
        }

        int level = getCurrentLevel(tool) + 1;
        int xpToNextLevel;
        if (level <= 1) {
            xpToNextLevel = Config.BASE_XP.get();
        } else {
            xpToNextLevel = (int) ((float) getXpToNextLevel(tool, level - 1) * Config.LEVEL_XP_MULTIPLIER.get());
        }

        return PROGRESS.get(TextFormatting.GRAY, getCurrentLevel(tool), getCurrentXp(tool), xpToNextLevel);
    }

    public static int getCurrentXp(ItemStack tool) {
        if (!isLevelable(tool))
            return 0;
        return NBTUtil.getInt(tool, TAG_XP);
    }

    public static int getCurrentLevel(ItemStack tool) {
        if (!isLevelable(tool))
            return 0;
        return NBTUtil.getInt(tool, TAG_LEVEL);
    }

    public static boolean isLevelAbovePercentage(double percentage, ItemStack tool) {
        if(!isLevelable(tool))
            return false;

        return isLevelAbovePercentage(percentage, LevelProperties.getCurrentLevel(tool), tool);
    }

    public static boolean isLevelAbovePercentage(double percentage, int level, ItemStack tool) {
        return getLevelableItem(tool)
                .map(item -> level >= percentage * item.getMaxLevel())
                .orElse(false);
    }

    public static double calculateValue(double base, double wantedLevel, ItemStack tool) {
        if (!isLevelable(tool))
            return 0;
        ILevelableItem levelableItem = (ILevelableItem) tool.getItem();

        if (base < wantedLevel) {
            double gainPerLevel = (wantedLevel - base) / (double) levelableItem.getMaxLevel();
            return base + Math.abs(gainPerLevel * LevelProperties.getCurrentLevel(tool));
        } else if(base > wantedLevel) {
            double lossPerLevel = (base - wantedLevel) / (double) levelableItem.getMaxLevel();
            return base - Math.abs(lossPerLevel * LevelProperties.getCurrentLevel(tool));
        }
        return base;
    }

    private static boolean canLevel(ItemStack tool) {
        return getLevelableItem(tool)
                .map(item -> getCurrentLevel(tool) < item.getMaxLevel())
                .orElse(false);
    }

    private static boolean isLevelable(ItemStack tool) {
        return getLevelableItem(tool).isPresent();
    }

    private static Optional<ILevelableItem> getLevelableItem(ItemStack tool) {
        return Optional.of(tool)
                .map(ItemStack::getItem)
                .filter(ILevelableItem.class::isInstance)
                .map(ILevelableItem.class::cast);
    }

}
