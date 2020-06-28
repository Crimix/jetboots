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
}
