package com.black_dog20.jetboots.common.events;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.util.SoulBoundInventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameRules;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID)
public class SoulboundHandler {

	@SubscribeEvent(priority=EventPriority.LOW)
	public static void onPlayerDeath(LivingDeathEvent event) {
		if(event.getEntity() != null && event.getEntity() instanceof PlayerEntity && !(event.getEntity() instanceof FakePlayer)) {
			PlayerEntity player = (PlayerEntity) event.getEntity();
			if (player.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY))
				return;
			if(event.isCanceled())
				return;
			SoulBoundInventory soul = new SoulBoundInventory(player);
			soul.writeToNBT();
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onPlayerSpawn(PlayerEvent.Clone event) {
		if (!event.isWasDeath() || event.isCanceled())
			return;

		if (event.getOriginal() == null || event.getPlayer() == null || event.getPlayer() instanceof FakePlayer) 
			return;
		PlayerEntity player = event.getPlayer();
		PlayerEntity original = event.getOriginal();

		if (player.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY))
			return;

		if (original == player || original.inventory == player.inventory || (original.inventory.armorInventory == player.inventory.armorInventory && original.inventory.mainInventory == player.inventory.mainInventory))
			return;
		
		SoulBoundInventory soul = SoulBoundInventory.GetForPlayer(original);
		soul.restoreMain(player);
		soul.restoreArmor(player);
		soul.restoreHand(player);
		soul.clear();

	}
}
