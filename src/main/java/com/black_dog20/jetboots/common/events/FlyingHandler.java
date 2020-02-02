package com.black_dog20.jetboots.common.events;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.util.JetBootsProperties;
import com.black_dog20.jetboots.common.util.JetbootsUtil;
import com.black_dog20.jetboots.common.util.NBTTags;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID)
public class FlyingHandler {

	@SubscribeEvent
	public static void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
		if(event.phase == TickEvent.Phase.END) { //END phase is important, otherwise this won't work because vanilla keeps resetting the elytra state!
			if(event.player instanceof PlayerEntity) {
				PlayerEntity player = event.player;
				if(JetbootsUtil.hasJetBoots(player)) {
					player.abilities.allowFlying = true;
					ItemStack jetboots = JetbootsUtil.getJetBoots(player);
					if(!player.getPersistentData().getBoolean(NBTTags.HAD_BOOTS_BEFORE)) {
						player.getPersistentData().putBoolean(NBTTags.HAD_BOOTS_BEFORE, true);
					}		
					if(!player.onGround && JetBootsProperties.getMode(jetboots) && (getAltitudeAboveGround(player) > 0.9 || player.isInWater())) {
						if(player.abilities.isFlying) {
							player.getPersistentData().putBoolean(NBTTags.WAS_FLYING_BEFORE, true);
							player.abilities.isFlying = false;
						}
						player.abilities.isFlying = false;
						player.func_226567_ej_();
						Vec3d vec3d = player.getLookVec();
						double d0 = 1.5D;
						double d1 = 0.1D;
						double d2 = 0.5D;
						double d3 = 3D;
						Vec3d vec3d1 = player.getMotion();
						if(!player.isInWater()) {
							double speed = JetBootsProperties.getSpeed(jetboots) ? d3 : d0;
							player.setMotion(vec3d1.add(vec3d.x * d1 + (vec3d.x * speed - vec3d1.x) * d2, vec3d.y * d1 + (vec3d.y * speed - vec3d1.y) * d2, vec3d.z * d1 + (vec3d.z * speed - vec3d1.z) * d2));
						} else {
							double speed = JetBootsProperties.getSpeed(jetboots) ? d0 : d2;
							player.setMotion(vec3d1.add(vec3d.x * d1 + (vec3d.x * speed - vec3d1.x) * d2, vec3d.y * d1 + (vec3d.y * speed - vec3d1.y) * d2, vec3d.z * d1 + (vec3d.z * speed - vec3d1.z) * d2));
						}
					} else if(player.isElytraFlying()) {
						player.func_226568_ek_();
						if(player.getPersistentData().getBoolean(NBTTags.WAS_FLYING_BEFORE)) {
							player.abilities.isFlying = true;
							player.getPersistentData().remove(NBTTags.WAS_FLYING_BEFORE);
						}
					}
				} else {
					if(player.getPersistentData().getBoolean(NBTTags.HAD_BOOTS_BEFORE)) {
						player.abilities.allowFlying = false;
						player.abilities.isFlying = false;
						player.getPersistentData().putBoolean(NBTTags.HAD_BOOTS_BEFORE, true);
					}
				}
			}
		}
	}

	public static double getAltitudeAboveGround(PlayerEntity player)
	{
		BlockPos blockPos = new BlockPos(player);
		while (player.world.isAirBlock(blockPos.down())) {
			blockPos = blockPos.down();
		}
		// calculate the entity's current altitude above the ground
		return blockPos.distanceSq(player.getPosX(), player.getPosY(), player.getPosZ(), false);
	}

	@SubscribeEvent
	public static void onPlayerFlyFall(PlayerFlyableFallEvent event) {
		if(true) { //Change later to logic such that upgrades for the boots removes fall damage
			PlayerEntity player = event.getPlayer();
			int i = calc(player, event.getDistance(), event.getMultiplier());
			if (i > 0) {
				player.playSound(getFallSound(i), 1.0F, 1.0F);
				playFallSound(player);
				player.attackEntityFrom(DamageSource.FALL, (float)i);
			}
		}
	}


	private static void playFallSound(PlayerEntity player) {
		if (!player.isSilent()) {
			int i = MathHelper.floor(player.getPosX());
			int j = MathHelper.floor(player.getPosY() - (double)0.2F);
			int k = MathHelper.floor(player.getPosZ());
			BlockPos pos = new BlockPos(i, j, k);
			BlockState blockstate = player.world.getBlockState(pos);
			if (!blockstate.isAir(player.world, pos)) {
				SoundType soundtype = blockstate.getSoundType(player.world, pos, player);
				player.playSound(soundtype.getFallSound(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
			}

		}
	}

	private static int calc(PlayerEntity player, float distance, float damageMultiplier) {
		EffectInstance effectinstance = player.getActivePotionEffect(Effects.JUMP_BOOST);
		float f = effectinstance == null ? 0.0F : (float)(effectinstance.getAmplifier() + 1);
		return MathHelper.ceil((distance - 3.0F - f) * damageMultiplier);
	}

	private static SoundEvent getFallSound(int heightIn) {
		return heightIn > 4 ? SoundEvents.ENTITY_PLAYER_BIG_FALL : SoundEvents.ENTITY_PLAYER_SMALL_FALL;
	}


}