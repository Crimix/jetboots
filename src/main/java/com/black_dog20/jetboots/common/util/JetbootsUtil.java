package com.black_dog20.jetboots.common.util;

import com.black_dog20.jetboots.common.items.JetBootsItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class JetbootsUtil {

	public static boolean isFlying(PlayerEntity player) {
		return hasJetBoots(player) && (player.abilities.isFlying || player.isElytraFlying());
	}
	
	public static boolean hasJetBoots(PlayerEntity player) {
		return player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() instanceof JetBootsItem;
	}
}
