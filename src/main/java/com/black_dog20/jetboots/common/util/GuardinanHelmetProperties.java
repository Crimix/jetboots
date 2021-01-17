package com.black_dog20.jetboots.common.util;

import com.black_dog20.bml.utils.item.NBTUtil;
import net.minecraft.item.ItemStack;

import static com.black_dog20.jetboots.common.util.NBTTags.*;

public class GuardinanHelmetProperties {
    private GuardinanHelmetProperties() {
    }

    public static void setMode(ItemStack helmet, boolean mode) {
        if (!helmet.isEmpty()) {
            NBTUtil.putBoolean(helmet, HELMET_MODE, mode);
        }
    }

    public static boolean getMode(ItemStack helmet) {
        return NBTUtil.getBoolean(helmet, HELMET_MODE);
    }

    public static void setNightVision(ItemStack helmet, boolean visionOn) {
        if (!helmet.isEmpty()) {
            NBTUtil.putBoolean(helmet, HELMET_NIGHT_VISION, visionOn);
        }
    }

    public static boolean getNightVision(ItemStack helmet) {
        return NBTUtil.getBoolean(helmet, HELMET_NIGHT_VISION);
    }
}
