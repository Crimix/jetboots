package com.black_dog20.jetboots.client.sound;

import com.black_dog20.jetboots.Jetboots;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Jetboots.MOD_ID);

    public static final RegistryObject<SoundEvent> JETBOOTS = SOUNDS.register("jetboots", () -> new SoundEvent(new ResourceLocation(Jetboots.MOD_ID, "jetboots")));

}
