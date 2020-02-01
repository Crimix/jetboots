package com.black_dog20.jetboots.common.util;

import net.minecraft.entity.player.PlayerEntity;

public class JetbootsUtil {

	public static boolean isFlying(PlayerEntity player) {
		return player.abilities.isFlying || player.isElytraFlying();
	}
}
