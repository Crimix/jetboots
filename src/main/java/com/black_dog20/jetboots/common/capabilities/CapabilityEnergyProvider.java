package com.black_dog20.jetboots.common.capabilities;

import com.black_dog20.jetboots.common.util.EnergyItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * More or less borrowed from
 * https://github.com/Direwolf20-MC/MiningGadgets/blob/master/src/main/java/com/direwolf20/mininggadgets/common/capabilities/CapabilityEnergyProvider.java
 */
public class CapabilityEnergyProvider implements ICapabilityProvider {
    private ItemStack stack;
    private int energyCapacity;
    private LazyOptional<IEnergyStorage> capability = LazyOptional.of(() -> new EnergyItem(stack, energyCapacity));

    public CapabilityEnergyProvider(ItemStack stack, int energyCapacity) {
        this.stack = stack;
        this.energyCapacity = energyCapacity;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityEnergy.ENERGY ? capability.cast() : LazyOptional.empty();
    }
}