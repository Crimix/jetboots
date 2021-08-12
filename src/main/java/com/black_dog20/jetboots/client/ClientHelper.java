package com.black_dog20.jetboots.client;

import com.black_dog20.bml.utils.math.Pos3D;
import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.client.sound.JetbootSound;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

public class ClientHelper {

    private static Random rand = new Random();

    public static void spawnJetParticals(Minecraft mc, Player player, boolean flying) {
        Pos3D playerPos = new Pos3D(player).translate(0, 1, 0);
        float random = (rand.nextFloat() - 0.5F) * 0.1F;
        double[] sneakBonus = player.isShiftKeyDown() ? new double[]{-0.30, -0.15} : new double[]{0, 0};
        if (flying) {
            Pos3D vLeft = new Pos3D(-0.1, -1.15 + sneakBonus[1], 0 + sneakBonus[0]).xRot(0).yRot(player.yBodyRot);
            Pos3D vRight = new Pos3D(0.1, -1.15 + sneakBonus[1], 0 + sneakBonus[0]).xRot(0).yRot(player.yBodyRot);

            Pos3D left = playerPos.translate(vLeft).translate(new Pos3D(player.getDeltaMovement().scale(0.01D)));
            Pos3D right = playerPos.translate(vRight).translate(new Pos3D(player.getDeltaMovement().scale(0.01D)));

            spawnPartical(mc, player, random, left, right);
        } else if (player.isFallFlying()) {
            Pos3D vLeft = new Pos3D(-0.1, -0.90, 0).xRot(0).yRot(player.yBodyRot);
            Pos3D vRight = new Pos3D(0.1, -0.90, 0).xRot(0).yRot(player.yBodyRot);

            Pos3D left = playerPos.translate(vLeft).translate(new Pos3D(player.getDeltaMovement().scale(0.01D)));
            Pos3D right = playerPos.translate(vRight).translate(new Pos3D(player.getDeltaMovement().scale(0.01D)));

            spawnPartical(mc, player, random, left, right);
        }
    }

    private static void spawnPartical(Minecraft mc, Player player, float random, Pos3D left, Pos3D right) {
        if (!player.isInWater()) {
            mc.particleEngine.createParticle(ParticleTypes.FLAME, left.x, left.y, left.z, random, -0.2D, random);
            mc.particleEngine.createParticle(ParticleTypes.SMOKE, left.x, left.y, left.z, random, -0.2D, random);

            mc.particleEngine.createParticle(ParticleTypes.FLAME, right.x, right.y, right.z, random, -0.2D, random);
            mc.particleEngine.createParticle(ParticleTypes.SMOKE, right.x, right.y, right.z, random, -0.2D, random);
        } else {
            mc.particleEngine.createParticle(ParticleTypes.BUBBLE, left.x, left.y, left.z, random, -0.2D, random);

            mc.particleEngine.createParticle(ParticleTypes.BUBBLE, right.x, right.y, right.z, random, -0.2D, random);
        }
    }

    public static void play(Player player) {
        if (!JetbootSound.playing(player) && !Config.MUFFLED_BOOTS.get()) {
            Minecraft.getInstance().getSoundManager().play(new JetbootSound(player));
        }
    }

    public static void stop(Player player) {
        if (JetbootSound.playing(player)) {
            JetbootSound.stop(player);
        }
    }
}
