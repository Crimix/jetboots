package com.black_dog20.jetboots.common.items;

import com.black_dog20.bml.api.ILevelableItem;
import com.black_dog20.bml.api.ISoulbindable;
import com.black_dog20.bml.utils.leveling.ItemLevelProperties;
import com.black_dog20.bml.utils.translate.TranslationUtil;
import com.black_dog20.jetboots.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.SOULBOUND;

public interface IBaseGuradianEquipment extends ISoulbindable, ILevelableItem {

    @Override
    default boolean isSoulbound(ItemStack stack) {
        return Config.DOES_ITEMS_GET_SOULBOUND.get() && isSoulboundByLevel(stack);
    }

    @Override
    default int getBaseXp() {
        return Config.BASE_XP.get();
    }

    @Override
    default double getLevelXpMultiplier() {
        return Config.LEVEL_XP_MULTIPLIER.get();
    }

    default List<Component> getLevelTooltips(ItemStack stack) {
        List<Component> tooltip = new ArrayList<>();
        if (ItemLevelProperties.getMaxLevel(stack) > 0) {
            tooltip.add(ItemLevelProperties.getLevelProgress(stack));
        }

        if (isSoulbound(stack)) {
            tooltip.add(TranslationUtil.translate(SOULBOUND, ChatFormatting.LIGHT_PURPLE));
        }

        return tooltip;
    }
}
