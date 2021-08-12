package com.black_dog20.jetboots.common.util;

import com.black_dog20.bml.utils.item.NBTItemBuilder;
import com.black_dog20.bml.utils.item.NBTUtil;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public class UpgradeFunctionUtil {

    public static Function<ItemStack, ItemStack> createApplyUpgradeBoolean(String key, Boolean value) {
        return stack -> {
            ItemStack copy = stack.copy();
            NBTUtil.putBoolean(copy, key, value);
            return copy;
        };
    }

    public static Function<ItemStack, ItemStack> createApplyUpgradeBoolean(String key) {
        return createApplyUpgradeBoolean(key, true);
    }

    public static Function<ItemStack, ItemStack> createMuffledApplyUpgradeFunc() {
        return stack -> {
            ItemStack copy = stack.copy();
            return NBTItemBuilder.init(copy)
                    .addTag(NBTTags.TAG_HAS_MUFFLED_UPGRADE, true)
                    .addTag(NBTTags.MUFFLED_UPGRADE_ON, true)
                    .build();
        };
    }

    public static Function<ItemStack, Boolean> createValidateUpgradeBoolean(String key, Boolean value) {
        return stack -> {
            ItemStack copy = stack.copy();
            return value.equals(NBTUtil.getBoolean(copy, key));
        };
    }

    public static Function<ItemStack, Boolean> createValidateUpgradeBoolean(String key) {
        return createValidateUpgradeBoolean(key, true);
    }

    public static Function<ItemStack, Boolean> createValidateUpgradeInt(String key, Function<Integer, Boolean> operator) {
        return stack -> {
            ItemStack copy = stack.copy();
            int value = NBTUtil.getInt(copy, key);
            return operator.apply(value);
        };
    }
}
