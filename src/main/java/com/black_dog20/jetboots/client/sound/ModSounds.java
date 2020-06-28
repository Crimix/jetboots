package com.black_dog20.jetboots.client.sound;

import com.black_dog20.jetboots.Jetboots;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, Jetboots.MOD_ID);

    public static final RegistryObject<SoundEvent> JETBOOTS = SOUNDS.register("jetboots", () -> new SoundEvent(new ResourceLocation(Jetboots.MOD_ID, "jetboots")));

}
