package com.black_dog20.jetboots.common.capabilities;

import com.black_dog20.jetboots.common.util.objects.EnchantableItemHandler;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GuardianCapabilities implements ICapabilityProvider {
    private ItemStack equipment;
    private LazyOptional<IItemHandler> optional = LazyOptional.of(() -> new EnchantableItemHandler(equipment));

    public GuardianCapabilities(ItemStack stack) {
        equipment = stack;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == null)
            return LazyOptional.empty();

        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return optional.cast();
        else
            return LazyOptional.empty();
    }
}