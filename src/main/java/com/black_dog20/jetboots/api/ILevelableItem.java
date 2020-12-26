package com.black_dog20.jetboots.api;

import com.black_dog20.jetboots.common.util.LevelProperties;
import net.minecraft.item.ItemStack;

public interface ILevelableItem {

    default int getMaxLevel() {
        return 10;
    }

    default double getSoulboundLevel() {
        return 0.9;
    }

    default boolean isSoulboundByLevel(ItemStack tool) {
        return LevelProperties.isLevelAbovePercentage(getSoulboundLevel(), tool);
    }
}
