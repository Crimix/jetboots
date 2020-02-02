package com.black_dog20.jetboots.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class JetBootsProperties {
    private JetBootsProperties() {}

    private static final String KEY_MODE = "jetboots-mode";
    private static final String KEY_SPEED = "jetboots-speed";

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
