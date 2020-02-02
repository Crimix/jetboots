package com.black_dog20.jetboots.client.events;

import java.util.Random;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.client.sound.JetbootSound;
import com.black_dog20.jetboots.common.util.JetbootsUtil;
import com.black_dog20.jetboots.common.util.Pos3D;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Jetboots.MOD_ID)
public class ClientHandler {

	private static Random rand = new Random();

	@SubscribeEvent
	public static void onRenderPlayer(RenderPlayerEvent.Post event) {
		Minecraft mc = Minecraft.getInstance();
		PlayerEntity player = event.getPlayer();
		if(!mc.isGamePaused()) {
			spawnJetParticals(mc, player);
		}
	}

	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		Minecraft mc = Minecraft.getInstance();
		PlayerEntity player = mc.player;
		if (event.phase == TickEvent.Phase.END) {
			if (mc.player != null && mc.world != null && JetbootsUtil.isFlying(mc.player)) {
				spawnJetParticals(mc, player);
				if (!JetbootSound.playing(mc.player.getEntityId()) && !player.isInWater()) {
					mc.getSoundHandler().play(new JetbootSound(mc.player));
				}
			}
		}
	}

	private static void spawnJetParticals(Minecraft mc, PlayerEntity player) {
		if(JetbootsUtil.isFlying(player)) {
			Pos3D playerPos = new Pos3D(player).translate(0, 1, 0);
			float random = (rand.nextFloat() - 0.5F) * 0.1F;
			double[] sneakBonus = player.isShiftKeyDown() ? new double[]{-0.30, -0.15} : new double[]{0, 0};

			if(player.abilities.isFlying) {
				Pos3D vLeft = new Pos3D(-0.1, -1.15 + sneakBonus[1], 0 + sneakBonus[0]).rotatePitch(0).rotateYaw(player.renderYawOffset);
				Pos3D vRight = new Pos3D(0.1, -1.15 + sneakBonus[1], 0 + sneakBonus[0]).rotatePitch(0).rotateYaw(player.renderYawOffset);

				Pos3D left = playerPos.translate(vLeft).translate(new Pos3D(player.getMotion().scale(0.01D)));
				Pos3D right = playerPos.translate(vRight).translate(new Pos3D(player.getMotion().scale(0.01D)));

				spawnPartical(mc, player, random, left, right);
			} else if(player.isElytraFlying()) {
				Pos3D vLeft = new Pos3D(-0.1, -0.90, 0).rotatePitch(0).rotateYaw(player.renderYawOffset);
				Pos3D vRight = new Pos3D(0.1, -0.90, 0).rotatePitch(0).rotateYaw(player.renderYawOffset);

				Pos3D left = playerPos.translate(vLeft).translate(new Pos3D(player.getMotion().scale(0.01D)));
				Pos3D right = playerPos.translate(vRight).translate(new Pos3D(player.getMotion().scale(0.01D)));

				spawnPartical(mc, player, random, left, right);
			}
		}
	}

	private static void spawnPartical(Minecraft mc, PlayerEntity player, float random, Pos3D left, Pos3D right) {
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
