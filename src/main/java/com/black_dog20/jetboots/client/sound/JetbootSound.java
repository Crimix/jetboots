package com.black_dog20.jetboots.client.sound;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.black_dog20.jetboots.common.util.JetbootsUtil;

import net.minecraft.client.audio.TickableSound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class JetbootSound extends TickableSound {
	private static final Map<Integer, JetbootSound> PLAYING_FOR = Collections.synchronizedMap(new HashMap<>());
	private final PlayerEntity player;
	
	public JetbootSound(PlayerEntity player) {
		super(ModSounds.JETBOOTS.get(), SoundCategory.PLAYERS);
		this.player = player;
		this.repeat = true;
	    this.repeatDelay = 0;
	    this.volume = 1.0F;
		PLAYING_FOR.put(player.getEntityId(), this);
	}
	
	public static boolean playing(int entityId) {
		return PLAYING_FOR.containsKey(entityId) && PLAYING_FOR.get(entityId) != null && !PLAYING_FOR.get(entityId).donePlaying;
	}
	
	public static void stopPlaying() {
		PLAYING_FOR.clear();
	}

	@Override
	public void tick() {
		BlockPos pos = this.player.getPosition();
		this.x = (float) pos.getX();
		this.y = (float) pos.getY();
		this.z = (float) pos.getZ();
		
		if (!JetbootsUtil.isFlying(this.player) || player.isInWater()) {
			synchronized (PLAYING_FOR) {
				PLAYING_FOR.remove(this.player.getEntityId());
				this.donePlaying = true;
			}
		}
	}
}