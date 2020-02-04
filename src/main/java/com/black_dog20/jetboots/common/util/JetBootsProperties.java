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
        return compound.getBoolean(KEY_SPEED);
    }
    
    public static boolean getLeatherArmorUpgrade(ItemStack jetboots) {
        CompoundNBT compound = jetboots.getOrCreateTag();
        return compound.getBoolean(UPGRAE_ARMOR_LEATHER);
    }

    public static boolean getIronArmorUpgrade(ItemStack jetboots) {
        CompoundNBT compound = jetboots.getOrCreateTag();
        return compound.getBoolean(UPGRAE_ARMOR_IRON);
    }
    
    public static boolean getDiamondArmorUpgrade(ItemStack jetboots) {
        CompoundNBT compound = jetboots.getOrCreateTag();
        return compound.getBoolean(UPGRAE_ARMOR_DIAMOND);
    }
    
    public static int getCustomArmorUpgrade(ItemStack jetboots) {
        CompoundNBT compound = jetboots.getOrCreateTag();
        return !compound.contains(UPGRAE_ARMOR_CUSTOM) ? -1 : compound.getInt(UPGRAE_ARMOR_CUSTOM);
    }
    
    public static int getCustomToughnessUpgrade(ItemStack jetboots) {
        CompoundNBT compound = jetboots.getOrCreateTag();
        return !compound.contains(UPGRAE_TOUGHNESS_CUSTOM) ? -1 : compound.getInt(UPGRAE_TOUGHNESS_CUSTOM);
    }
    
    public static String getCustomArmorUpgradeName(ItemStack jetboots) {
        CompoundNBT compound = jetboots.getOrCreateTag();
        return compound.getString(UPGRAE_ARMOR_CUSTOM_NAME);
    }
    
    public static boolean getEngineUpgrade(ItemStack jetboots) {
        CompoundNBT compound = jetboots.getOrCreateTag();
        return compound.getBoolean(UPGRAE_ENGINE);
    }
    
    public static boolean getThrusterUpgrade(ItemStack jetboots) {
        CompoundNBT compound = jetboots.getOrCreateTag();
        return compound.getBoolean(UPGRAE_THRUSTER);
    }
    
    public static boolean getShockUpgrade(ItemStack jetboots) {
        CompoundNBT compound = jetboots.getOrCreateTag();
        return compound.getBoolean(UPGRAE_SHOCK_ABSORBER);
    }
    
    public static boolean getUnderWaterUpgrade(ItemStack jetboots) {
        CompoundNBT compound = jetboots.getOrCreateTag();
        return compound.getBoolean(UPGRAE_UNDERWATER);
    }
    
    public static boolean getSoulboundUpgrade(ItemStack jetboots) {
        CompoundNBT compound = jetboots.getOrCreateTag();
        return compound.getBoolean(UPGRAE_SOULBOUND);
    }
    
    public static boolean getAdvancedBattery(ItemStack jetboots) {
        CompoundNBT compound = jetboots.getOrCreateTag();
        return compound.getBoolean(UPGRAE_ADVANCED_BATTERY);
    }
    
    public static boolean getSuperBattery(ItemStack jetboots) {
        CompoundNBT compound = jetboots.getOrCreateTag();
        return compound.getBoolean(UPGRAE_SUPER_BATTERY);
    }
}
