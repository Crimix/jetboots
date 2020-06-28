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

    public static ForgeConfigSpec.IntValue DEFAULT_MAX_POWER;
    public static ForgeConfigSpec.IntValue POWER_COST;
    public static ForgeConfigSpec.IntValue HELMET_LOOT_WEIGHT;
    public static ForgeConfigSpec.IntValue FORCEFIELD_PARTS_LOOT_WEIGHT;
    public static ForgeConfigSpec.BooleanValue EAT_WITH_HELMET;

    static {
        CLIENT_BUILDER.comment("Client settings").push(CLIENT_SETTINGS);
        MUFFLED_BOOTS = CLIENT_BUILDER.comment("Is all jetboots muffled without the upgrade")
                .define("muffledBoots", false);
        CLIENT_BUILDER.pop();

        CLIENT_CONFIG = CLIENT_BUILDER.build();

        SERVER_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
        DEFAULT_MAX_POWER = SERVER_BUILDER.comment("Default maximum power for jetboots")
                .defineInRange("defaultMaxPower", 1000000, 0, Integer.MAX_VALUE);
        POWER_COST = SERVER_BUILDER.comment("Cost per tick to use jetboots")
                .defineInRange("powerCost", 50, 0, Integer.MAX_VALUE);
        EAT_WITH_HELMET = SERVER_BUILDER.comment("Can a player eat and drink while having a materialized helmet")
                .define("eatWithHelmet", false);
        HELMET_LOOT_WEIGHT = SERVER_BUILDER.comment("The weight for the helmet to be in a End City loot chests")
                .defineInRange("helmetLootWeight", 10, 0, 100);
        FORCEFIELD_PARTS_LOOT_WEIGHT = SERVER_BUILDER.comment("The weight for the forcefield parts to be in a End City / Nether fort loot chests")
                .defineInRange("forcefieldPartsLootWeight", 10, 0, 100);
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
