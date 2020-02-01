package com.black_dog20.jetboots.common.events;

import java.util.Random;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.client.sound.JetbootSound;
import com.black_dog20.jetboots.common.util.JetbootsUtil;
import com.black_dog20.jetboots.common.util.Pos3D;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundSource;
import net.minecraft.client.audio.SoundSystem;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID)
public class FlyingHandler {

	public static Random rand = new Random();

	@SubscribeEvent
	public static void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
		if(event.phase == TickEvent.Phase.END) { //END phase is important, otherwise this won't work because vanilla keeps resetting the elytra state!
			if(event.player instanceof PlayerEntity) {
				PlayerEntity player = event.player;
				if(!player.onGround && !player.abilities.isFlying && (getAltitudeAboveGround(player) > 0.9 || player.isInWater())) {
					player.func_226567_ej_();
					Vec3d vec3d = player.getLookVec();
					double d0 = 1.5D;
					double d1 = 0.1D;
					double d2 = 0.5D;
					double d3 = 4D;
					Vec3d vec3d1 = player.getMotion();
					player.setMotion(vec3d1.add(vec3d.x * d1 + (vec3d.x * d0 - vec3d1.x) * d2, vec3d.y * d1 + (vec3d.y * d0 - vec3d1.y) * d2, vec3d.z * d1 + (vec3d.z * d0 - vec3d1.z) * d2));
				} else if(player.isElytraFlying()) {
					player.func_226568_ek_();
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
	public static void onRenderPlayer(RenderPlayerEvent.Post event) {
		Minecraft mc = Minecraft.getInstance();
		PlayerEntity player = event.getPlayer();
		if(true && !mc.isGamePaused()) {
			Pos3D playerPos = new Pos3D(player).translate(0, 1, 0);
			float random = (rand.nextFloat() - 0.5F) * 0.1F;
			double[] sneakBonus = player.isShiftKeyDown() ? new double[]{-0.30, -0.15} : new double[]{0, 0};
			
			if(player.abilities.isFlying) {
				Pos3D vLeft = new Pos3D(-0.1, -1.15 + sneakBonus[1], 0 + sneakBonus[0]).rotatePitch(0).rotateYaw(player.renderYawOffset);
				Pos3D vRight = new Pos3D(0.1, -1.15 + sneakBonus[1], 0 + sneakBonus[0]).rotatePitch(0).rotateYaw(player.renderYawOffset);

				Pos3D left = playerPos.translate(vLeft).translate(new Pos3D(player.getMotion().scale(0.01D)));
				Pos3D right = playerPos.translate(vRight).translate(new Pos3D(player.getMotion().scale(0.01D)));

				if(!player.isInWater()) {
					mc.particles.addParticle(ParticleTypes.FLAME, left.x, left.y, left.z, random, -0.2D, random);
					mc.particles.addParticle(ParticleTypes.SMOKE, left.x, left.y, left.z, random, -0.2D, random);

					mc.particles.addParticle(ParticleTypes.FLAME, right.x, right.y, right.z, random, -0.2D, random);
					mc.particles.addParticle(ParticleTypes.SMOKE, right.x, right.y, right.z, random, -0.2D, random);
				} else {
					mc.particles.addParticle(ParticleTypes.BUBBLE, left.x, left.y, left.z, random, -0.2D, random);

					mc.particles.addParticle(ParticleTypes.BUBBLE, right.x, right.y, right.z, random, -0.2D, random);
				}
			} else if(player.isElytraFlying()) {
				Pos3D vLeft = new Pos3D(-0.1, -0.90, 0).rotatePitch(0).rotateYaw(player.renderYawOffset);
				Pos3D vRight = new Pos3D(0.1, -0.90, 0).rotatePitch(0).rotateYaw(player.renderYawOffset);

				Pos3D left = playerPos.translate(vLeft).translate(new Pos3D(player.getMotion().scale(0.01D)));
				Pos3D right = playerPos.translate(vRight).translate(new Pos3D(player.getMotion().scale(0.01D)));

				if(!player.isInWater()) {
					mc.particles.addParticle(ParticleTypes.FLAME, left.x, left.y, left.z, random, -0.2D, random);
					mc.particles.addParticle(ParticleTypes.SMOKE, left.x, left.y, left.z, random, -0.2D, random);

					mc.particles.addParticle(ParticleTypes.FLAME, right.x, right.y, right.z, random, -0.2D, random);
					mc.particles.addParticle(ParticleTypes.SMOKE, right.x, right.y, right.z, random, -0.2D, random);
				}
				else {
					mc.particles.addParticle(ParticleTypes.BUBBLE, left.x, left.y, left.z, random, -0.2D, random);

					mc.particles.addParticle(ParticleTypes.BUBBLE, right.x, right.y, right.z, random, -0.2D, random);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		Minecraft mc = Minecraft.getInstance();
		PlayerEntity player = mc.player;
        if (event.phase == TickEvent.Phase.END) {
            if (mc.player != null && mc.world != null) {
                if (!mc.isGamePaused()) {
                	Pos3D playerPos = new Pos3D(player).translate(0, 1, 0);
        			float random = (rand.nextFloat() - 0.5F) * 0.1F;
        			double[] sneakBonus = player.isShiftKeyDown() ? new double[]{-0.30, -0.15} : new double[]{0, 0};
        			
        			if(player.abilities.isFlying) {
        				Pos3D vLeft = new Pos3D(-0.1, -1.15 + sneakBonus[1], 0 + sneakBonus[0]).rotatePitch(0).rotateYaw(player.renderYawOffset);
        				Pos3D vRight = new Pos3D(0.1, -1.15 + sneakBonus[1], 0 + sneakBonus[0]).rotatePitch(0).rotateYaw(player.renderYawOffset);

        				Pos3D left = playerPos.translate(vLeft).translate(new Pos3D(player.getMotion().scale(0.01D)));
        				Pos3D right = playerPos.translate(vRight).translate(new Pos3D(player.getMotion().scale(0.01D)));

        				if(!player.isInWater()) {
        					mc.particles.addParticle(ParticleTypes.FLAME, left.x, left.y, left.z, random, -0.2D, random);
        					mc.particles.addParticle(ParticleTypes.SMOKE, left.x, left.y, left.z, random, -0.2D, random);

        					mc.particles.addParticle(ParticleTypes.FLAME, right.x, right.y, right.z, random, -0.2D, random);
        					mc.particles.addParticle(ParticleTypes.SMOKE, right.x, right.y, right.z, random, -0.2D, random);
        				} else {
        					mc.particles.addParticle(ParticleTypes.BUBBLE, left.x, left.y, left.z, random, -0.2D, random);

        					mc.particles.addParticle(ParticleTypes.BUBBLE, right.x, right.y, right.z, random, -0.2D, random);
        				}
        			} else if(player.isElytraFlying()) {
        				Pos3D vLeft = new Pos3D(-0.1, -0.90, 0).rotatePitch(0).rotateYaw(player.renderYawOffset);
        				Pos3D vRight = new Pos3D(0.1, -0.90, 0).rotatePitch(0).rotateYaw(player.renderYawOffset);

        				Pos3D left = playerPos.translate(vLeft).translate(new Pos3D(player.getMotion().scale(0.01D)));
        				Pos3D right = playerPos.translate(vRight).translate(new Pos3D(player.getMotion().scale(0.01D)));

        				if(!player.isInWater()) {
        					mc.particles.addParticle(ParticleTypes.FLAME, left.x, left.y, left.z, random, -0.2D, random);
        					mc.particles.addParticle(ParticleTypes.SMOKE, left.x, left.y, left.z, random, -0.2D, random);

        					mc.particles.addParticle(ParticleTypes.FLAME, right.x, right.y, right.z, random, -0.2D, random);
        					mc.particles.addParticle(ParticleTypes.SMOKE, right.x, right.y, right.z, random, -0.2D, random);
        				}
        				else {
        					mc.particles.addParticle(ParticleTypes.BUBBLE, left.x, left.y, left.z, random, -0.2D, random);

        					mc.particles.addParticle(ParticleTypes.BUBBLE, right.x, right.y, right.z, random, -0.2D, random);
        				}
        			}
                	
                    if (JetbootsUtil.isFlying(mc.player)) {
                       if (!JetbootSound.playing(mc.player.getEntityId())) {
                            mc.getSoundHandler().play(new JetbootSound(mc.player));
                        }
                    }
                }
            }
        }
	}

}
