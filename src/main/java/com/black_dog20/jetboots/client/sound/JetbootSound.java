package com.black_dog20.jetboots.client.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class JetbootSound extends TickableSound {
    private static final Map<UUID, JetbootSound> PLAYING_FOR = Collections.synchronizedMap(new HashMap<>());
    private final PlayerEntity player;

    public JetbootSound(PlayerEntity player) {
        super(ModSounds.JETBOOTS.get(), SoundCategory.PLAYERS);
        this.player = player;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 1.0F;
        PLAYING_FOR.put(player.getUniqueID(), this);
    }

    public static boolean playing(PlayerEntity player) {
        return PLAYING_FOR.containsKey(player.getUniqueID()) && PLAYING_FOR.get(player.getUniqueID()) != null && !PLAYING_FOR.get(player.getUniqueID()).isDonePlaying();
    }

    public static void stop(PlayerEntity player) {
        if (PLAYING_FOR.containsKey(player.getUniqueID()) && PLAYING_FOR.get(player.getUniqueID()) != null) {
            synchronized (PLAYING_FOR) {
                JetbootSound sound = PLAYING_FOR.get(player.getUniqueID());
                sound.func_239509_o_();
                PLAYING_FOR.remove(player.getUniqueID());
            }
        }
    }

    @Override
    public void tick() {
        BlockPos pos = this.player.getPosition();
        this.x = (float) pos.getX();
        this.y = (float) pos.getY();
        this.z = (float) pos.getZ();
        BlockPos posClient = Minecraft.getInstance().player.getPosition();
        double maxDistance = 1024D;
        double distance = posClient.distanceSq(pos);
        if (distance < maxDistance) {
            double ratio = 1 - (distance / maxDistance);
            this.volume = MathHelper.clamp((float) ratio, 0.0F, 1.0F);
        } else {
            this.volume = 0.0F;
        }
    }
}