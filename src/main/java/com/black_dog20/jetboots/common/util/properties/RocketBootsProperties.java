package com.black_dog20.jetboots.common.util.properties;

import com.black_dog20.bml.utils.item.NBTUtil;
import net.minecraft.world.item.ItemStack;

import static com.black_dog20.jetboots.common.util.NBTTags.KEY_ENGINE_STATE;
import static com.black_dog20.jetboots.common.util.NBTTags.TAG_HAS_SHOCK_ABSORBER_UPGRADE;

public class RocketBootsProperties {

    private RocketBootsProperties() {
    }

    public static void setEngineState(ItemStack rocketboots, boolean state) {
        if (!rocketboots.isEmpty()) {
            NBTUtil.putBoolean(rocketboots, KEY_ENGINE_STATE, state);
        }
    }

    public static boolean getEngineState(ItemStack rocketboots) {
        return NBTUtil.getBoolean(rocketboots, KEY_ENGINE_STATE, true);
    }

    public static boolean hasShockUpgrade(ItemStack rocketboots) {
        return NBTUtil.getBoolean(rocketboots, TAG_HAS_SHOCK_ABSORBER_UPGRADE);
    }
}
