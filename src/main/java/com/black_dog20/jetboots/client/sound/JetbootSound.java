package com.black_dog20.jetboots.client.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class JetbootSound extends AbstractTickableSoundInstance {
    private static final Map<UUID, JetbootSound> PLAYING_FOR = Collections.synchronizedMap(new HashMap<>());
    private final Player player;

    public JetbootSound(Player player) {
        super(ModSounds.JETBOOTS.get(), SoundSource.PLAYERS);
        this.player = player;
        this.looping = true;
        this.delay = 0;
        this.volume = 1.0F;
        PLAYING_FOR.put(player.getUUID(), this);
    }

    public static boolean playing(Player player) {
        return PLAYING_FOR.containsKey(player.getUUID()) && PLAYING_FOR.get(player.getUUID()) != null && !PLAYING_FOR.get(player.getUUID()).isStopped();
    }

    public static void stop(Player player) {
        if (PLAYING_FOR.containsKey(player.getUUID()) && PLAYING_FOR.get(player.getUUID()) != null) {
            synchronized (PLAYING_FOR) {
                JetbootSound sound = PLAYING_FOR.get(player.getUUID());
                sound.stop();
                PLAYING_FOR.remove(player.getUUID());
            }
        }
    }

    @Override
    public void tick() {
        BlockPos pos = this.player.blockPosition();
        this.x = (float) pos.getX();
        this.y = (float) pos.getY();
        this.z = (float) pos.getZ();
        BlockPos posClient = Minecraft.getInstance().player.blockPosition();
        double maxDistance = 1024D;
        double distance = posClient.distSqr(pos);
        if (distance < maxDistance) {
            double ratio = 1 - (distance / maxDistance);
            this.volume = Mth.clamp((float) ratio, 0.0F, 1.0F);
        } else {
            this.volume = 0.0F;
        }
    }
}