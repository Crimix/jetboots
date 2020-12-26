package com.black_dog20.jetboots.common.capabilities;

import com.black_dog20.jetboots.common.util.EnchantableItemHandler;
import com.black_dog20.jetboots.common.util.EnergyItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class JetbootsCapabilities implements ICapabilityProvider {
    private ItemStack jetboots;
    private int energyCapacity;
    private LazyOptional<IEnergyStorage> capability = LazyOptional.of(() -> new EnergyItem(jetboots, energyCapacity));
    private LazyOptional<IItemHandler> optional = LazyOptional.of(() -> new EnchantableItemHandler(jetboots));

    public JetbootsCapabilities(ItemStack stack, int energyCapacity) {
        jetboots = stack;
        this.energyCapacity = energyCapacity;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == null)
            return LazyOptional.empty();

        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return optional.cast();
        else if (cap == CapabilityEnergy.ENERGY)
            return capability.cast();
        else
            return LazyOptional.empty();
    }
}
