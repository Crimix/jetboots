package com.black_dog20.jetboots.common.util.properties;

import com.black_dog20.bml.utils.item.NBTUtil;
import net.minecraft.world.item.ItemStack;

import static com.black_dog20.jetboots.common.util.NBTTags.*;

public class JetBootsProperties {

    private JetBootsProperties() {
    }

    public static void setMode(ItemStack jetboots, boolean mode) {
        if (!jetboots.isEmpty()) {
            NBTUtil.putBoolean(jetboots, KEY_MODE, mode);
        }
    }

    public static boolean getMode(ItemStack jetboots) {
        return NBTUtil.getBoolean(jetboots, KEY_MODE);
    }

    public static void setSpeed(ItemStack jetboots, boolean speed) {
        if (!jetboots.isEmpty()) {
            NBTUtil.putBoolean(jetboots, KEY_SPEED, speed);
        }
    }

    public static boolean getSpeed(ItemStack jetboots) {
        return NBTUtil.getBoolean(jetboots, KEY_SPEED);
    }

    public static boolean hasEngineUpgrade(ItemStack jetboots) {
        return NBTUtil.getBoolean(jetboots, TAG_HAS_ENGINE_UPGRADE);
    }

    public static boolean hasThrusterUpgrade(ItemStack jetboots) {
        return NBTUtil.getBoolean(jetboots, TAG_HAS_THRUSTER_UPGRADE);
    }

    public static boolean hasShockUpgrade(ItemStack jetboots) {
        return NBTUtil.getBoolean(jetboots, TAG_HAS_SHOCK_ABSORBER_UPGRADE);
    }

    public static boolean hasUnderWaterUpgrade(ItemStack jetboots) {
        return NBTUtil.getBoolean(jetboots, TAG_HAS_UNDERWATER_UPGRADE);
    }

    public static boolean hasMuffledUpgrade(ItemStack jetboots) {
        return NBTUtil.getBoolean(jetboots, TAG_HAS_MUFFLED_UPGRADE);
    }

    public static boolean hasActiveMuffledUpgrade(ItemStack jetboots) {
        return hasMuffledUpgrade(jetboots) && NBTUtil.getBoolean(jetboots, MUFFLED_UPGRADE_ON);
    }

    public static void setActiveMuffledUpgrade(ItemStack jetboots, boolean muffledMode) {
        if (!jetboots.isEmpty()) {
            NBTUtil.putBoolean(jetboots, MUFFLED_UPGRADE_ON, muffledMode);
        }
    }
}
