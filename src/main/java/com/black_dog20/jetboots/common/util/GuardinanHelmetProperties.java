package com.black_dog20.jetboots.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import static com.black_dog20.jetboots.common.util.NBTTags.*;

public class GuardinanHelmetProperties {
    private GuardinanHelmetProperties() {
    }

    public static boolean setMode(ItemStack helmet, boolean mode) {
        if (helmet.isEmpty()) {
            return false;
        } else {
            helmet.getOrCreateTag().putBoolean(HELMET_MODE, mode);
            return true;
        }
    }

    public static boolean getMode(ItemStack helmet) {
        CompoundNBT compound = helmet.getOrCreateTag();
        return !compound.contains(HELMET_MODE) ? setMode(helmet, false) : compound.getBoolean(HELMET_MODE);
    }

    public static boolean setNightVision(ItemStack helmet, boolean visionOn) {
        if (helmet.isEmpty()) {
            return false;
        } else {
            helmet.getOrCreateTag().putBoolean(HELMET_NIGHT_VISION, visionOn);
            return true;
        }
    }

    public static boolean getNightVision(ItemStack helmet) {
        CompoundNBT compound = helmet.getOrCreateTag();
        return !compound.contains(HELMET_NIGHT_VISION) ? setNightVision(helmet, false) : compound.getBoolean(HELMET_NIGHT_VISION);
    }
}
