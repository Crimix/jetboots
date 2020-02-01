package com.black_dog20.jetboots;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.black_dog20.jetboots.client.keybinds.Keybinds;
import com.black_dog20.jetboots.client.sound.ModSounds;
import com.black_dog20.jetboots.common.items.ModItems;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(Jetboots.MOD_ID)
public class Jetboots
{
	public static final String MOD_ID = "jetboots";
	private static final Logger LOGGER = LogManager.getLogger();

	public static ItemGroup itemGroup = new ItemGroup(Jetboots.MOD_ID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModItems.JET_BOOTS.get());
		}
	};

	public Jetboots() {
		IEventBus event = FMLJavaModLoadingContext.get().getModEventBus();

		ModItems.ITEMS.register(event);
		ModSounds.SOUNDS.register(event);

		event.addListener(this::setup);
		event.addListener(this::setupClient);

		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

		// Register the setup method for modloading
		event.addListener(this::setup);
		MinecraftForge.EVENT_BUS.register(this);

		Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MOD_ID + "-client.toml"));
		Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MOD_ID + "-common.toml"));
	}

	private void setup(final FMLCommonSetupEvent event){
	}

	private void setupClient(final FMLClientSetupEvent event) {
		ClientRegistry.registerKeyBinding(Keybinds.keyEngine);
		ClientRegistry.registerKeyBinding(Keybinds.keySpeed);
	}

	public static Logger getLogger() {
		return LOGGER;
	}
}
