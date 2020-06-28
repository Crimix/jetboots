package com.black_dog20.jetboots.common.util;

import com.black_dog20.jetboots.common.items.upgrades.api.IArmorUpgrade;
import com.black_dog20.jetboots.common.items.upgrades.api.IBatteryUpgrade;
import com.black_dog20.jetboots.common.items.upgrades.api.IConverterUpgrade;
import com.black_dog20.jetboots.common.items.upgrades.api.IFlatValueEnergyModifier;
import com.black_dog20.jetboots.common.items.upgrades.api.IPercentageValueEnergyModifier;
import com.black_dog20.jetboots.common.items.upgrades.api.IThrusterUpgrade;
import com.black_dog20.jetboots.common.items.upgrades.api.IUpgrade;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

import static com.black_dog20.jetboots.common.items.upgrades.api.IUpgrade.*;
import static com.black_dog20.jetboots.common.util.NBTTags.*;

public class JetBootsProperties {
    private JetBootsProperties() {
    }

    public static boolean setMode(ItemStack jetboots, boolean mode) {
        if (jetboots.isEmpty()) {
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
        if (jetboots.isEmpty()) {
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

    public static LazyOptional<IArmorUpgrade> getArmorUpgrade(ItemStack jetboots) {
        Item item = getUpgradeItem(jetboots, Type.ARMOR);

        if (item != Items.AIR) {
            IArmorUpgrade armorUpgrade = (IArmorUpgrade) item;
            return LazyOptional.of(() -> armorUpgrade);
        }
        return LazyOptional.empty();
    }

    public static boolean hasEngineUpgrade(ItemStack jetboots) {
        return getEngineUpgrade(jetboots).isPresent();
    }

    public static LazyOptional<IUpgrade> getEngineUpgrade(ItemStack jetboots) {
        Item item = getUpgradeItem(jetboots, Type.ENGINE);

        if (item != Items.AIR) {
            IUpgrade upgrade = (IUpgrade) item;
            return LazyOptional.of(() -> upgrade);
        }
        return LazyOptional.empty();
    }

    public static boolean hasThrusterUpgrade(ItemStack jetboots) {
        return getThrusterUpgrade(jetboots).isPresent();
    }

    public static LazyOptional<IThrusterUpgrade> getThrusterUpgrade(ItemStack jetboots) {
        Item item = getUpgradeItem(jetboots, Type.THRUSTER);

        if (item != Items.AIR) {
            IThrusterUpgrade upgrade = (IThrusterUpgrade) item;
            return LazyOptional.of(() -> upgrade);
        }
        return LazyOptional.empty();
    }

    public static boolean hasShockUpgrade(ItemStack jetboots) {
        return getShockUpgrade(jetboots).isPresent();
    }

    public static LazyOptional<IUpgrade> getShockUpgrade(ItemStack jetboots) {
        Item item = getUpgradeItem(jetboots, Type.SHOCK_ABSORBER);

        if (item != Items.AIR) {
            IUpgrade upgrade = (IUpgrade) item;
            return LazyOptional.of(() -> upgrade);
        }
        return LazyOptional.empty();
    }

    public static boolean hasUnderWaterUpgrade(ItemStack jetboots) {
        return getUnderWaterUpgrade(jetboots).isPresent();
    }

    public static LazyOptional<IUpgrade> getUnderWaterUpgrade(ItemStack jetboots) {
        Item item = getUpgradeItem(jetboots, Type.UNDERWATER);

        if (item != Items.AIR) {
            IUpgrade upgrade = (IUpgrade) item;
            return LazyOptional.of(() -> upgrade);
        }
        return LazyOptional.empty();
    }

    public static boolean hasSoulboundUpgrade(ItemStack jetboots) {
        return getSoulboundUpgrade(jetboots).isPresent();
    }

    public static LazyOptional<IUpgrade> getSoulboundUpgrade(ItemStack jetboots) {
        Item item = getUpgradeItem(jetboots, Type.SOULBOUND);

        if (item != Items.AIR) {
            IUpgrade upgrade = (IUpgrade) item;
            return LazyOptional.of(() -> upgrade);
        }
        return LazyOptional.empty();
    }

    public static boolean hasMuffledUpgrade(ItemStack jetboots) {
        return getMuffledUpgrade(jetboots).isPresent();
    }

    public static LazyOptional<IUpgrade> getMuffledUpgrade(ItemStack jetboots) {
        Item item = getUpgradeItem(jetboots, Type.MUFFLED);

        if (item != Items.AIR) {
            IUpgrade upgrade = (IUpgrade) item;
            return LazyOptional.of(() -> upgrade);
        }
        return LazyOptional.empty();
    }

    public static boolean hasBatteryUpgrade(ItemStack jetboots) {
        return getBatteryUpgrade(jetboots).isPresent();
    }

    public static LazyOptional<IBatteryUpgrade> getBatteryUpgrade(ItemStack jetboots) {
        IUpgrade.Type type = IUpgrade.Type.BATTERY;

        Item item = getUpgradeItem(jetboots, type);

        if (item != Items.AIR) {
            IBatteryUpgrade upgrade = (IBatteryUpgrade) item;
            return LazyOptional.of(() -> upgrade);
        }
        return LazyOptional.empty();
    }

    public static boolean hasConverterUpgrade(ItemStack jetboots) {
        return getConverterUpgrade(jetboots).isPresent();
    }

    public static LazyOptional<IConverterUpgrade> getConverterUpgrade(ItemStack jetboots) {
        IUpgrade.Type type = IUpgrade.Type.CONVERTER;

        Item item = getUpgradeItem(jetboots, type);

        if (item != Items.AIR) {
            IConverterUpgrade upgrade = (IConverterUpgrade) item;
            return LazyOptional.of(() -> upgrade);
        }
        return LazyOptional.empty();
    }

    private static Item getUpgradeItem(ItemStack jetboots, IUpgrade.Type type) {
        LazyOptional<IItemHandler> handler = jetboots.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (handler.isPresent()) {
            JetBootsItemHandler inventory = (JetBootsItemHandler) handler.orElse(null);
            return inventory.getStackInSlotByType(type).getItem();
        }
        return Items.AIR;
    }

    public static List<IFlatValueEnergyModifier> getFlatEnergyModifiers(ItemStack jetboots) {
        List<IFlatValueEnergyModifier> result = new ArrayList<>();

        for (IUpgrade.Type type : IUpgrade.Type.values()) {
            Item upgrade = getUpgradeItem(jetboots, type);
            if (upgrade != Items.AIR && upgrade instanceof IFlatValueEnergyModifier) {
                result.add((IFlatValueEnergyModifier) upgrade);
            }
        }

        return result;
    }

    public static List<IPercentageValueEnergyModifier> getPercentageEnergyModifiers(ItemStack jetboots) {
        List<IPercentageValueEnergyModifier> result = new ArrayList<>();

        for (IUpgrade.Type type : IUpgrade.Type.values()) {
            Item upgrade = getUpgradeItem(jetboots, type);
            if (upgrade != Items.AIR && upgrade instanceof IPercentageValueEnergyModifier) {
                result.add((IPercentageValueEnergyModifier) upgrade);
            }
        }

        return result;
    }
}
