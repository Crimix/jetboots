package com.black_dog20.jetboots;

import com.black_dog20.jetboots.client.containers.ModContainers;
import com.black_dog20.jetboots.client.sound.ModSounds;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.network.PacketHandler;
import com.black_dog20.jetboots.common.recipe.ModRecipeSerializers;
import com.black_dog20.jetboots.common.util.TranslationHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Jetboots.MOD_ID)
public class Jetboots {
    public static final String MOD_ID = "jetboots";
    private static final Logger LOGGER = LogManager.getLogger();

    public Jetboots() {
        IEventBus event = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MOD_ID + "-client.toml"));
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
        Config.loadConfig(Config.SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MOD_ID + "-server.toml"));

        ModItems.ITEMS.register(event);
//        ModCompat.register(event);
        ModContainers.CONTAINERS.register(event);
        ModSounds.SOUNDS.register(event);
        ModRecipeSerializers.RECIPE_SERIALIZERS.register(event);

        event.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void registerTabs(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(Jetboots.MOD_ID), builder -> builder
                .icon(() -> new ItemStack(ModItems.JET_BOOTS.get()))
                .title(TranslationHelper.Translations.ITEM_CATEGORY.get(ChatFormatting.RESET))
                .displayItems((parameters, output, vis) -> {
                    for (RegistryObject<Item> item : ModItems.ITEMS.getEntries()) {
                        output.accept(item.get());
                    }
                })
        );
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.register();
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
