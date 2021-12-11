package com.black_dog20.jetboots;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Config {

    public static final String CLIENT_SETTINGS = "client";
    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_LEVEL = "leveling";
    public static final String CATEGORY_HELMET = "helmet";
    public static final String CATEGORY_JACKET = "jacket";
    public static final String CATEGORY_PANTS = "pants";
    public static final String CATEGORY_JETBOOTS = "jetboots";
    public static final String CATEGORY_ROCKETBOOTS = "rocketboots";
    public static final String CATEGORY_SWORD = "sword";

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

    public static ForgeConfigSpec.IntValue BASE_XP;
    public static ForgeConfigSpec.DoubleValue LEVEL_XP_MULTIPLIER;
    public static ForgeConfigSpec.IntValue TICKS_BETWEEN_FLIGHT_XP_GAIN;
    public static ForgeConfigSpec.IntValue FLIGHT_XP;
    public static ForgeConfigSpec.DoubleValue HURT_XP_MIN;
    public static ForgeConfigSpec.DoubleValue HURT_XP_MODIFIER;
    public static ForgeConfigSpec.DoubleValue HURT_XP_MAX;
    public static ForgeConfigSpec.DoubleValue ATTACK_XP_MIN;
    public static ForgeConfigSpec.DoubleValue ATTACK_XP_MODIFIER;
    public static ForgeConfigSpec.DoubleValue ATTACK_XP_MAX;
    public static ForgeConfigSpec.IntValue ROCKET_XP_MIN;
    public static ForgeConfigSpec.DoubleValue ROCKET_XP_MODIFIER;
    public static ForgeConfigSpec.IntValue ROCKET_XP_MAX;
    public static ForgeConfigSpec.BooleanValue DOES_ITEMS_GET_SOULBOUND;

    public static ForgeConfigSpec.IntValue HELMET_MAX_LEVEL;
    public static ForgeConfigSpec.DoubleValue HELMET_BASE_DAMAGE_REDUCE_AMOUNT;
    public static ForgeConfigSpec.DoubleValue HELMET_MAX_DAMAGE_REDUCE_AMOUNT;
    public static ForgeConfigSpec.DoubleValue HELMET_BASE_TOUGHNESS_AMOUNT;
    public static ForgeConfigSpec.DoubleValue HELMET_MAX_TOUGHNESS_AMOUNT;
    public static ForgeConfigSpec.DoubleValue HELMET_BASE_KNOCKBACK_RESISTANCE_AMOUNT;
    public static ForgeConfigSpec.DoubleValue HELMET_MAX_KNOCKBACK_RESISTANCE_AMOUNT;

    public static ForgeConfigSpec.IntValue JACKET_MAX_LEVEL;
    public static ForgeConfigSpec.DoubleValue JACKET_BASE_DAMAGE_REDUCE_AMOUNT;
    public static ForgeConfigSpec.DoubleValue JACKET_MAX_DAMAGE_REDUCE_AMOUNT;
    public static ForgeConfigSpec.DoubleValue JACKET_BASE_TOUGHNESS_AMOUNT;
    public static ForgeConfigSpec.DoubleValue JACKET_MAX_TOUGHNESS_AMOUNT;
    public static ForgeConfigSpec.DoubleValue JACKET_BASE_KNOCKBACK_RESISTANCE_AMOUNT;
    public static ForgeConfigSpec.DoubleValue JACKET_MAX_KNOCKBACK_RESISTANCE_AMOUNT;

    public static ForgeConfigSpec.IntValue PANTS_MAX_LEVEL;
    public static ForgeConfigSpec.DoubleValue PANTS_BASE_DAMAGE_REDUCE_AMOUNT;
    public static ForgeConfigSpec.DoubleValue PANTS_MAX_DAMAGE_REDUCE_AMOUNT;
    public static ForgeConfigSpec.DoubleValue PANTS_BASE_TOUGHNESS_AMOUNT;
    public static ForgeConfigSpec.DoubleValue PANTS_MAX_TOUGHNESS_AMOUNT;
    public static ForgeConfigSpec.DoubleValue PANTS_BASE_KNOCKBACK_RESISTANCE_AMOUNT;
    public static ForgeConfigSpec.DoubleValue PANTS_MAX_KNOCKBACK_RESISTANCE_AMOUNT;

    public static ForgeConfigSpec.IntValue JETBOOTS_MAX_LEVEL;
    public static ForgeConfigSpec.DoubleValue JETBOOTS_BASE_DAMAGE_REDUCE_AMOUNT;
    public static ForgeConfigSpec.DoubleValue JETBOOTS_MAX_DAMAGE_REDUCE_AMOUNT;
    public static ForgeConfigSpec.DoubleValue JETBOOTS_BASE_TOUGHNESS_AMOUNT;
    public static ForgeConfigSpec.DoubleValue JETBOOTS_MAX_TOUGHNESS_AMOUNT;
    public static ForgeConfigSpec.DoubleValue JETBOOTS_BASE_KNOCKBACK_RESISTANCE_AMOUNT;
    public static ForgeConfigSpec.DoubleValue JETBOOTS_MAX_KNOCKBACK_RESISTANCE_AMOUNT;

    public static ForgeConfigSpec.IntValue ROCKETBOOTS_MAX_LEVEL;
    public static ForgeConfigSpec.DoubleValue ROCKETBOOTS_BASE_DAMAGE_REDUCE_AMOUNT;
    public static ForgeConfigSpec.DoubleValue ROCKETBOOTS_MAX_DAMAGE_REDUCE_AMOUNT;
    public static ForgeConfigSpec.DoubleValue ROCKETBOOTS_BASE_TOUGHNESS_AMOUNT;
    public static ForgeConfigSpec.DoubleValue ROCKETBOOTS_MAX_TOUGHNESS_AMOUNT;
    public static ForgeConfigSpec.DoubleValue ROCKETBOOTS_BASE_KNOCKBACK_RESISTANCE_AMOUNT;
    public static ForgeConfigSpec.DoubleValue ROCKETBOOTS_MAX_KNOCKBACK_RESISTANCE_AMOUNT;
    public static ForgeConfigSpec.IntValue ROCKETBOOTS_BASE_FLIGHT_TIME;
    public static ForgeConfigSpec.IntValue ROCKETBOOTS_MAX_FLIGHT_TIME;

    public static ForgeConfigSpec.IntValue SWORD_MAX_LEVEL;
    public static ForgeConfigSpec.DoubleValue SWORD_BASE_ATTACK_DAMAGE;
    public static ForgeConfigSpec.DoubleValue SWORD_MAX_ATTACK_DAMAGE;
    public static ForgeConfigSpec.DoubleValue SWORD_BASE_ATTACK_SPEED;
    public static ForgeConfigSpec.DoubleValue SWORD_MAX_ATTACK_SPEED;

    static {
        com.electronwill.nightconfig.core.Config.setInsertionOrderPreserved(true); //Needed until https://github.com/TheElectronWill/night-config/issues/85 is released
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
        SERVER_BUILDER.pop();

        SERVER_BUILDER.push(CATEGORY_LEVEL);
        BASE_XP = SERVER_BUILDER.comment("The base xp required to level up a levelable items")
                .defineInRange("baseLevelXp", 500, 100, Integer.MAX_VALUE);
        LEVEL_XP_MULTIPLIER = SERVER_BUILDER.comment("The xp multiplier added ot the base xp required for each level")
                .defineInRange("levelXpMultiplier", 1.1, 1, Double.MAX_VALUE);
        TICKS_BETWEEN_FLIGHT_XP_GAIN = SERVER_BUILDER.comment("How often in ticks the player can earn xp by flying with jetboots (Only for jetboots)")
                .defineInRange("ticksBetweenFlightXp", 80, 20, 1200);
        FLIGHT_XP = SERVER_BUILDER.comment("Xp awarded for flying with the jetboots")
                .defineInRange("flightXp", 8, 1, 100);
        HURT_XP_MIN = SERVER_BUILDER.comment("Lower bound for xp awarded for taking damage")
                .defineInRange("hurtXpMin", 3F, 1F, 100F);
        HURT_XP_MODIFIER = SERVER_BUILDER.comment("Modifier that the damage amount on hurt gets multiplied with", "Will used in the following way, clamp(damage * modifier, min, max)", "Gets added to all levelable armor")
                .defineInRange("hurtXpModifier", 1F, 1F, 100F);
        HURT_XP_MAX = SERVER_BUILDER.comment("Upper bound for xp awarded for taking damage")
                .defineInRange("hurtXpMax", 10F, 1F, 300F);
        ATTACK_XP_MIN = SERVER_BUILDER.comment("Lower bound for xp awarded for dealing damage")
                .defineInRange("attackXpMin", 1F, 1F, 100F);
        ATTACK_XP_MODIFIER = SERVER_BUILDER.comment("Modifier that the damage amount on attack gets multiplied with", "Will used in the following way, clamp(damage * modifier, min, max)", "Gets added to The Godslayer")
                .defineInRange("attackXpModifier", 1F, 1F, 100F);
        ATTACK_XP_MAX = SERVER_BUILDER.comment("Upper bound for xp awarded for dealing damage")
                .defineInRange("attackXpMax", 8F, 1F, 300F);
        ROCKET_XP_MIN = SERVER_BUILDER.comment("Lower bound for xp awarded for flying with rocket boots")
                .defineInRange("rocketXpMin", 1, 1, 100);
        ROCKET_XP_MODIFIER = SERVER_BUILDER.comment("Modifier that the flight ticks gets multiplied with", "Will used in the following way, clamp(ticks * modifier, min, max)", "Gets added Rocket Boots")
                .defineInRange("rocketXpModifier", 0.1F, 0.1F, 10F);
        ROCKET_XP_MAX = SERVER_BUILDER.comment("Upper bound for xp awarded for flying with rocket boots")
                .defineInRange("rocketXpMax", 15, 1, 300);
        DOES_ITEMS_GET_SOULBOUND = SERVER_BUILDER.comment("Does items become soulbound upon reaching 90% leveled up")
                .define("itemsBecomeSoulbound", true);

        SERVER_BUILDER.push(CATEGORY_HELMET);
        HELMET_MAX_LEVEL = SERVER_BUILDER.comment("The max level of the helmet", "If set to 0 it will cause the max value to be applied when the item is crafted")
                .defineInRange("helmetMaxLevel", 10, 0, Integer.MAX_VALUE);
        HELMET_BASE_DAMAGE_REDUCE_AMOUNT = SERVER_BUILDER.comment("The base damage reducation amount of the helmet")
                .defineInRange("helmetBaseDamageReducation", 3D, 2D, 20D);
        HELMET_MAX_DAMAGE_REDUCE_AMOUNT = SERVER_BUILDER.comment("The max damage reducation amount of the helmet")
                .defineInRange("helmetMaxDamageReducation", 6D, 2D, 20D);
        HELMET_BASE_TOUGHNESS_AMOUNT = SERVER_BUILDER.comment("The base toughness amount of the helmet")
                .defineInRange("helmetBaseToughness", 0D, 0D, 20D);
        HELMET_MAX_TOUGHNESS_AMOUNT = SERVER_BUILDER.comment("The max toughness amount of the helmet")
                .defineInRange("helmetMaxToughness", 4D, 0D, 20D);
        HELMET_BASE_KNOCKBACK_RESISTANCE_AMOUNT = SERVER_BUILDER.comment("The base knockback resistance amount of the helmet")
                .defineInRange("helmetBaseKnockbackResistance", 0D, 0D, 20D);
        HELMET_MAX_KNOCKBACK_RESISTANCE_AMOUNT = SERVER_BUILDER.comment("The max knockback resistance amount of the helmet")
                .defineInRange("helmetMaxKnockbackResistance", 2D, 0D, 20D);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.push(CATEGORY_JACKET);
        JACKET_MAX_LEVEL = SERVER_BUILDER.comment("The max level of the jacket", "If set to 0 it will cause the max value to be applied when the item is crafted")
                .defineInRange("jacketMaxLevel", 10, 0, Integer.MAX_VALUE);
        JACKET_BASE_DAMAGE_REDUCE_AMOUNT = SERVER_BUILDER.comment("The base damage reducation amount of the jacket")
                .defineInRange("jacketBaseDamageReducation", 6D, 3D, 20D);
        JACKET_MAX_DAMAGE_REDUCE_AMOUNT = SERVER_BUILDER.comment("The max damage reducation amount of the jacket")
                .defineInRange("jacketMaxDamageReducation", 10D, 3D, 20D);
        JACKET_BASE_TOUGHNESS_AMOUNT = SERVER_BUILDER.comment("The base toughness amount of the jacket")
                .defineInRange("jacketBaseToughness", 0D, 0D, 20D);
        JACKET_MAX_TOUGHNESS_AMOUNT = SERVER_BUILDER.comment("The max toughness amount of the jacket")
                .defineInRange("jacketMaxToughness", 4D, 0D, 20D);
        JACKET_BASE_KNOCKBACK_RESISTANCE_AMOUNT = SERVER_BUILDER.comment("The base knockback resistance amount of the jacket")
                .defineInRange("jacketBaseKnockbackResistance", 0D, 0D, 20D);
        JACKET_MAX_KNOCKBACK_RESISTANCE_AMOUNT = SERVER_BUILDER.comment("The max knockback resistance amount of the jacket")
                .defineInRange("jacketMaxKnockbackResistance", 2D, 0D, 20D);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.push(CATEGORY_PANTS);
        PANTS_MAX_LEVEL = SERVER_BUILDER.comment("The max level of the pants", "If set to 0 it will cause the max value to be applied when the item is crafted")
                .defineInRange("pantsMaxLevel", 10, 0, Integer.MAX_VALUE);
        PANTS_BASE_DAMAGE_REDUCE_AMOUNT = SERVER_BUILDER.comment("The base damage reducation amount of the pants")
                .defineInRange("pantsBaseDamageReducation", 5D, 2D, 20D);
        PANTS_MAX_DAMAGE_REDUCE_AMOUNT = SERVER_BUILDER.comment("The max damage reducation amount of the pants")
                .defineInRange("pantsMaxDamageReducation", 8D, 2D, 20D);
        PANTS_BASE_TOUGHNESS_AMOUNT = SERVER_BUILDER.comment("The base toughness amount of the pants")
                .defineInRange("pantsBaseToughness", 0D, 0D, 20D);
        PANTS_MAX_TOUGHNESS_AMOUNT = SERVER_BUILDER.comment("The max toughness amount of the pants")
                .defineInRange("pantsMaxToughness", 4D, 0D, 20D);
        PANTS_BASE_KNOCKBACK_RESISTANCE_AMOUNT = SERVER_BUILDER.comment("The base knockback resistance amount of the pants")
                .defineInRange("pantsBaseKnockbackResistance", 0D, 0D, 20D);
        PANTS_MAX_KNOCKBACK_RESISTANCE_AMOUNT = SERVER_BUILDER.comment("The max knockback resistance amount of the pants")
                .defineInRange("pantsMaxKnockbackResistance", 2D, 0D, 20D);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.push(CATEGORY_JETBOOTS);
        JETBOOTS_MAX_LEVEL = SERVER_BUILDER.comment("The max level of the jetboots", "If set to 0 it will cause the max value to be applied when the item is crafted")
                .defineInRange("jetbootsMaxLevel", 10, 0, Integer.MAX_VALUE);
        JETBOOTS_BASE_DAMAGE_REDUCE_AMOUNT = SERVER_BUILDER.comment("The base damage reducation amount of the jetboots")
                .defineInRange("jetbootsBaseDamageReducation", 2D, 2D, 20D);
        JETBOOTS_MAX_DAMAGE_REDUCE_AMOUNT = SERVER_BUILDER.comment("The max damage reducation amount of the jetboots")
                .defineInRange("jetbootsMaxDamageReducation", 6D, 2D, 20D);
        JETBOOTS_BASE_TOUGHNESS_AMOUNT = SERVER_BUILDER.comment("The base toughness amount of the jetboots")
                .defineInRange("jetbootsBaseToughness", 0D, 0D, 20D);
        JETBOOTS_MAX_TOUGHNESS_AMOUNT = SERVER_BUILDER.comment("The max toughness amount of the jetboots")
                .defineInRange("jetbootsMaxToughness", 4D, 0D, 20D);
        JETBOOTS_BASE_KNOCKBACK_RESISTANCE_AMOUNT = SERVER_BUILDER.comment("The base knockback resistance amount of the jetboots")
                .defineInRange("jetbootsBaseKnockbackResistance", 0D, 0D, 20D);
        JETBOOTS_MAX_KNOCKBACK_RESISTANCE_AMOUNT = SERVER_BUILDER.comment("The max knockback resistance amount of the jetboots")
                .defineInRange("jetbootsMaxKnockbackResistance", 2D, 0D, 20D);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.push(CATEGORY_ROCKETBOOTS);
        ROCKETBOOTS_MAX_LEVEL = SERVER_BUILDER.comment("The max level of the rocketboots", "If set to 0 it will cause the max value to be applied when the item is crafted")
                .defineInRange("rocketbootsMaxLevel", 3, 0, Integer.MAX_VALUE);
        ROCKETBOOTS_BASE_DAMAGE_REDUCE_AMOUNT = SERVER_BUILDER.comment("The base damage reducation amount of the rocketboots")
                .defineInRange("rocketbootsBaseDamageReducation", 2D, 2D, 20D);
        ROCKETBOOTS_MAX_DAMAGE_REDUCE_AMOUNT = SERVER_BUILDER.comment("The max damage reducation amount of the rocketboots")
                .defineInRange("rocketbootsMaxDamageReducation", 4D, 2D, 20D);
        ROCKETBOOTS_BASE_TOUGHNESS_AMOUNT = SERVER_BUILDER.comment("The base toughness amount of the rocketboots")
                .defineInRange("rocketbootsBaseToughness", 0D, 0D, 20D);
        ROCKETBOOTS_MAX_TOUGHNESS_AMOUNT = SERVER_BUILDER.comment("The max toughness amount of the rocketboots")
                .defineInRange("rocketbootsMaxToughness", 2D, 0D, 20D);
        ROCKETBOOTS_BASE_KNOCKBACK_RESISTANCE_AMOUNT = SERVER_BUILDER.comment("The base knockback resistance amount of the rocketboots")
                .defineInRange("rocketbootsBaseKnockbackResistance", 0D, 0D, 20D);
        ROCKETBOOTS_MAX_KNOCKBACK_RESISTANCE_AMOUNT = SERVER_BUILDER.comment("The max knockback resistance amount of the rocketboots")
                .defineInRange("rocketbootsMaxKnockbackResistance", 0D, 0D, 20D);
        ROCKETBOOTS_BASE_FLIGHT_TIME = SERVER_BUILDER.comment("The base time that rocketboots can fly")
                .defineInRange("rocketbootsBaseFlightTime", 100, 20, 300);
        ROCKETBOOTS_MAX_FLIGHT_TIME = SERVER_BUILDER.comment("The max time that rocketboots can fly")
                .defineInRange("rocketbootsMaxFlightTime", 150, 50, 400);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.push(CATEGORY_SWORD);
        SWORD_MAX_LEVEL = SERVER_BUILDER.comment("The max level of the sword", "If set to 0 it will cause the max value to be applied when the item is crafted")
                .defineInRange("swordMaxLevel", 10, 0, Integer.MAX_VALUE);
        SWORD_BASE_ATTACK_DAMAGE = SERVER_BUILDER.comment("The base attack damage of the sword")
                .defineInRange("swordBaseAttackDamage", 5D, 4D, Double.MAX_VALUE);
        SWORD_MAX_ATTACK_DAMAGE = SERVER_BUILDER.comment("The max attack damage of the sword")
                .defineInRange("swordMaxAttackDamage", 11D, 4D, Double.MAX_VALUE);
        SWORD_BASE_ATTACK_SPEED = SERVER_BUILDER.comment("The base attack speed modifier of the sword")
                .defineInRange("swordBaseAttackSpeed", -2.4F, -2.4F, -0F);
        SWORD_MAX_ATTACK_SPEED = SERVER_BUILDER.comment("The max attack speed modifier of the sword")
                .defineInRange("swordMaxAttackSpeed", -1.6F, -2.4F, -0F);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .preserveInsertionOrder()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {

    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading configEvent) {
    }
}
