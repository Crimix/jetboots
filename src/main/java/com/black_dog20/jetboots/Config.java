package com.black_dog20.jetboots;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Config {

    public static final String CLIENT_SETTINGS = "client";
    public static final String CATEGORY_GENERAL = "general";

    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec CLIENT_CONFIG;
    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.BooleanValue MUFFLED_BOOTS;
    public static ForgeConfigSpec.BooleanValue BATTERY_ICON_STATE;
    public static ForgeConfigSpec.IntValue ICON_X_PERCENTAGE;
    public static ForgeConfigSpec.IntValue ICON_Y_PERCENTAGE;

    public static ForgeConfigSpec.IntValue DEFAULT_MAX_POWER;
    public static ForgeConfigSpec.IntValue POWER_COST;
    public static ForgeConfigSpec.BooleanValue HELMET_LOOT;

    static {
        CLIENT_BUILDER.comment("Client settings").push(CLIENT_SETTINGS);
        MUFFLED_BOOTS = CLIENT_BUILDER.comment("Is all jetboots muffled without the upgrade")
                .define("muffledBoots", false);
        BATTERY_ICON_STATE = CLIENT_BUILDER.comment("Is the battery hud icon visible")
                .define("batteryIconVisible", true);
        ICON_X_PERCENTAGE = CLIENT_BUILDER.comment("Battery hud icon x positive in percentage")
                .defineInRange("batteryIconX", 20, 0, 100);
        ICON_Y_PERCENTAGE = CLIENT_BUILDER.comment("Battery hud icon y positive in percentage")
                .defineInRange("batteryIconY", 92, 0, 100);
        CLIENT_BUILDER.pop();

        CLIENT_CONFIG = CLIENT_BUILDER.build();

        SERVER_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
        DEFAULT_MAX_POWER = SERVER_BUILDER.comment("Default maximum power for jetboots")
                .defineInRange("defaultMaxPower", 1000000, 0, Integer.MAX_VALUE);
        POWER_COST = SERVER_BUILDER.comment("Cost per tick to use jetboots")
                .defineInRange("powerCost", 50, 0, Integer.MAX_VALUE);
        HELMET_LOOT = SERVER_BUILDER.comment("If there is a chance for the helmet to be in a End City / Nether loot chests")
                .define("helmetLoot", true);
        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
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
    public static void onReload(final ModConfig.Reloading configEvent) {
    }


}
