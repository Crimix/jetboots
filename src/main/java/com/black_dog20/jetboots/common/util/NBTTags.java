package com.black_dog20.jetboots.common.util;

import net.minecraft.item.ItemStack;

public class NBTTags {

	public static final String HAD_BOOTS_BEFORE = "had-jetboots-boots";
	public static final String WAS_FLYING_BEFORE = "was-jetboots-flying";
	public static final String SOULBOUND_INVENTORY = "jetboots-soulbound-inventory";
	
	public static final String KEY_MODE = "jetboots-mode";
	public static final String KEY_SPEED = "jetboots-speed";
	
	public static final String UPGRAE_ARMOR_LEATHER = "jetboots-upgrade-leather";
	public static final String UPGRAE_ARMOR_IRON = "jetboots-upgrade-iron";
	public static final String UPGRAE_ARMOR_DIAMOND = "jetboots-upgrade-diamond";
	public static final String UPGRAE_ARMOR_CUSTOM = "jetboots-upgrade-custom-armor";
	public static final String UPGRAE_TOUGHNESS_CUSTOM = "jetboots-upgrade-custom-toughness";
	public static final String UPGRAE_ARMOR_CUSTOM_NAME = "jetboots-upgrade-custom-name";
	public static final String UPGRAE_ENGINE = "jetboots-upgrade-engine";
	public static final String UPGRAE_THRUSTER = "jetboots-upgrade-thruster";
	public static final String UPGRAE_SHOCK_ABSORBER = "jetboots-upgrade-shock-absorber";
	public static final String UPGRAE_UNDERWATER = "jetboots-upgrade-underwater";
	public static final String UPGRAE_SOULBOUND = "jetboots-upgrade-soulbound";
	public static final String UPGRAE_ADVANCED_BATTERY = "jetboots-upgrade-advanced-battery";
	public static final String UPGRAE_SUPER_BATTERY = "jetboots-upgrade-super-battery";
	
	public static boolean doesItemStackHaveTag(ItemStack stack, String tag) {
		return !stack.isEmpty() && stack.getOrCreateTag().contains(tag);
	}
}
