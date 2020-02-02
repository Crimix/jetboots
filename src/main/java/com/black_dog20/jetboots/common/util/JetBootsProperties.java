package com.black_dog20.jetboots.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import static com.black_dog20.jetboots.common.util.NBTTags.*;

public class JetBootsProperties {
    private JetBootsProperties() {}

    public static boolean setMode(ItemStack jetboots, boolean mode) {
    	if(jetboots.isEmpty()) {
    		return false;
    	} else {
    		jetboots.getOrCreateTag().putBoolean(KEY_MODE, mode);
    		return true;
    	}
    }

    public static boolean getMode(ItemStack jetboots) {
        CompoundNBT compound = jetboots.getOrCreateTag();
        return !compound.contains(KEY_MODE) ? setMode(jetboots, false) : compound.getBoolean(KEY_MODE);
    }
    
    public static boolean setSpeed(ItemStack jetboots, boolean speed) {
    	if(jetboots.isEmpty()) {
    		return false;
    	} else {
    		jetboots.getOrCreateTag().putBoolean(KEY_SPEED, speed);
    		return true;
    	}
    }
    
    public static boolean getSpeed(ItemStack jetboots) {
        CompoundNBT compound = jetboots.getOrCreateTag();
        return !compound.contains(KEY_SPEED) ? setSpeed(jetboots, false) : compound.getBoolean(KEY_SPEED);
    }
}
