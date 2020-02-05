package com.black_dog20.jetboots.common.util;

import com.black_dog20.jetboots.Config;

import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.EnergyStorage;

/**
 * More or less borrowed from 
 * https://github.com/Direwolf20-MC/MiningGadgets/blob/master/src/main/java/com/direwolf20/mininggadgets/common/util/EnergisedItem.java
 */
public class EnergyItem extends EnergyStorage {
    private ItemStack stack;

    public EnergyItem(ItemStack stack, int capacity) {
        super(getMaxCapacity(stack, capacity), Integer.MAX_VALUE, Integer.MAX_VALUE);

        this.stack = stack;
        this.energy = stack.hasTag() && stack.getTag().contains("energy") ? stack.getTag().getInt("energy") : 0;
    }

    private static int getMaxCapacity(ItemStack stack, int capacity) {
        if( !stack.hasTag() || !stack.getTag().contains("max_energy") ) {
            stack.getOrCreateTag().putInt("max_energy", capacity);
            stack.getOrCreateTag().putInt("energy_mult", 1);
            return capacity;
        }

        return stack.getTag().getInt("max_energy")*stack.getTag().getInt("energy_mult");
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int amount = super.receiveEnergy(maxReceive, simulate);
        stack.getOrCreateTag().putInt("energy", this.energy);

        return amount;
    }

    @Override
    public int getEnergyStored() {
        int amount = super.getEnergyStored();
        stack.getOrCreateTag().putInt("energy", amount);

        return amount;
    }
    
    @Override
    public int getMaxEnergyStored()
    {
        return capacity;
    }
    
    @Override
    public boolean canExtract()
    {
        if(Config.USE_POWER.get()) {
        	return super.canExtract();
        }
        return false;
    }

    @Override
    public boolean canReceive()
    {
        if(Config.USE_POWER.get()) {
        	return super.canReceive();
        }
        return false;
    }
}
    
