package com.black_dog20.jetboots.common.capabilities;

import com.black_dog20.jetboots.common.util.objects.EnchantableItemHandler;
import com.black_dog20.jetboots.common.util.objects.EnergyItem;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
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

        if (cap == ForgeCapabilities.ITEM_HANDLER)
            return optional.cast();
        else if (cap == ForgeCapabilities.ENERGY)
            return capability.cast();
        else
            return LazyOptional.empty();
    }
}
