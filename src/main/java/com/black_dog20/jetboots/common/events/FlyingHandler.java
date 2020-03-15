package com.black_dog20.jetboots.common.events;

import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.network.PacketHandler;
import com.black_dog20.jetboots.common.network.packets.PacketSyncPartical;
import com.black_dog20.jetboots.common.network.packets.PacketSyncSound;
import com.black_dog20.jetboots.common.network.packets.PacketSyncStopSound;
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
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID)
public class FlyingHandler {

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
		PlayerEntity player = event.player;
		if(JetbootsUtil.canFlyWithBoots(player)) {
			player.abilities.allowFlying = true;
			ItemStack jetboots = JetbootsUtil.getJetBoots(player);
			setNbtHasBoots(player);

			if(useElytraFlight(player, jetboots)) {
				startElytraFlight(player, jetboots);
			} else if(player.isElytraFlying()) {
				stopElytraFlight(player);
			} else if(player.abilities.isFlying) {
				drawpower(jetboots);
			}
			sendSoundAndPartical(event, player, jetboots);
		} else {
			stopFlightIfHadBoots(player);
		}
	}

	private static boolean useElytraFlight(PlayerEntity player, ItemStack jetboots) {
		return JetBootsProperties.getEngineUpgrade(jetboots) && JetBootsProperties.getMode(jetboots) && (getAltitudeAboveGround(player) > 1.9 || (player.isInWater() && JetBootsProperties.getUnderWaterUpgrade(jetboots)));
	}

	private static void stopFlightIfHadBoots(PlayerEntity player) {
		if(player.getPersistentData().getBoolean(NBTTags.HAD_BOOTS_BEFORE)) {
			player.abilities.allowFlying = false;
			player.abilities.isFlying = false;
			if(JetbootsUtil.getJetBoots(player).isEmpty()) {
				player.getPersistentData().remove(NBTTags.HAD_BOOTS_BEFORE);
			}
		}
	}

	private static void setNbtHasBoots(PlayerEntity player) {
		if(!player.getPersistentData().getBoolean(NBTTags.HAD_BOOTS_BEFORE)) {
			player.getPersistentData().putBoolean(NBTTags.HAD_BOOTS_BEFORE, true);
		}
	}

	private static void sendSoundAndPartical(TickEvent.PlayerTickEvent event, PlayerEntity player, ItemStack jetboots) {
		if(event.side == LogicalSide.SERVER) {
			if (JetbootsUtil.isFlying(player)) {
				PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(()-> player), new PacketSyncPartical(player.getUniqueID(), player.abilities.isFlying));
				if(!JetBootsProperties.getMuffledUpgrade(jetboots)) {
					if (!player.isInWater()) {
						PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(()-> player), new PacketSyncSound(player.getUniqueID()));
					} else {
						PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(()-> player), new PacketSyncStopSound(player.getUniqueID()));
					}
				}
			} else {
				PacketHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(()-> player), new PacketSyncStopSound(player.getUniqueID()));
			}
		}
	}

	private static void stopElytraFlight(PlayerEntity player) {
		player.func_226568_ek_();
		if(player.getPersistentData().getBoolean(NBTTags.WAS_FLYING_BEFORE)) {
			player.abilities.isFlying = true;
			player.sendPlayerAbilities();
			player.getPersistentData().remove(NBTTags.WAS_FLYING_BEFORE);
		}
	}

	private static void startElytraFlight(PlayerEntity player, ItemStack jetboots) {
		drawpower(jetboots);
		if(player.abilities.isFlying) {
			player.getPersistentData().putBoolean(NBTTags.WAS_FLYING_BEFORE, true);
			player.abilities.isFlying = false;
		}
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
	}

	private static void drawpower(ItemStack jetboots) {
		if(Config.USE_POWER.get()) {
			IEnergyStorage energy = jetboots.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
			if(energy != null)
				energy.extractEnergy(Config.POWER_COST.get(), false);
		}
	}

	public static double getAltitudeAboveGround(PlayerEntity player)
	{
		if(player.isInWater()) {
			return 0;
		}
		BlockPos blockPos = new BlockPos(player);
		while (player.world.isAirBlock(blockPos.down())) {
			blockPos = blockPos.down();
		}
		// calculate the entity's current altitude above the ground
		return blockPos.distanceSq(player.getPosX(), player.getPosY(), player.getPosZ(), false);
	}

	@SubscribeEvent
	public static void onPlayerFlyFall(PlayerFlyableFallEvent event) {
		PlayerEntity player = event.getPlayer();
		if(JetbootsUtil.hasJetBoots(player)) {
			ItemStack jetboots = JetbootsUtil.getJetBoots(player);
			if(!JetBootsProperties.getShockUpgrade(jetboots)) {
				int i = calc(player, event.getDistance(), event.getMultiplier());
				if (i > 0) {
					player.playSound(getFallSound(i), 1.0F, 1.0F);
					playFallSound(player);
					player.attackEntityFrom(DamageSource.FALL, (float)i);
				}
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
