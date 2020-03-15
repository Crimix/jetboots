package com.black_dog20.jetboots;

import java.nio.file.Path;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.config.ModConfig.ConfigReloading;

@Mod.EventBusSubscriber
public class Config {

    public static final String CATEGORY_GENERAL = "general";

    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec SERVER_CONFIG;
    
    public static ForgeConfigSpec.BooleanValue USE_POWER;
    public static ForgeConfigSpec.IntValue DEFAULT_MAX_POWER;
    public static ForgeConfigSpec.IntValue POWER_COST;

    static {

        SERVER_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
        USE_POWER = SERVER_BUILDER.comment("Does jetboots use power?")
        		.define("usePower", true);
        DEFAULT_MAX_POWER = SERVER_BUILDER.comment("Default maximum power for jetboots")
                .defineInRange("defaultMaxPower", 1000000, 0, Integer.MAX_VALUE);
        POWER_COST = SERVER_BUILDER.comment("Cost per tick to use jetboots")
                .defineInRange("powerCost", 50, 0, Integer.MAX_VALUE);
        SERVER_BUILDER.pop();


        SERVER_CONFIG = SERVER_BUILDER.build();
        //CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {

    }

    @SubscribeEvent
    public static void onReload(final ConfigReloading configEvent) {
    }


}
