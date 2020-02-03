package com.black_dog20.jetboots.common.util;

import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.common.items.JetBootsItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class JetbootsUtil {

	public static boolean isFlying(PlayerEntity player) {
		return hasJetBoots(player) && (player.abilities.isFlying || player.isElytraFlying());
	}
	
	public static boolean hasJetBoots(PlayerEntity player) {
		return player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() instanceof JetBootsItem;
	}
	
	public static ItemStack getJetBoots(PlayerEntity player) {
		if(!hasJetBoots(player))
			return ItemStack.EMPTY;
		return player.getItemStackFromSlot(EquipmentSlotType.FEET);
	}
	
	public static boolean canFlyWithBoots(PlayerEntity player) {
		if(!hasJetBoots(player))
			return false;
		else {
			if(!Config.USE_POWER.get())
				return true;
			else {
				ItemStack jetboots = getJetBoots(player);
				IEnergyStorage energy = jetboots.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
				if(energy != null) {
					return energy.getEnergyStored() >= Config.POWER_COST.get();
				} else 
					return false;
			}
		}
	}
}
