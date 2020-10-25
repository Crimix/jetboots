package com.black_dog20.jetboots.common.data;

import com.black_dog20.bml.datagen.BaseLanguageProvider;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.compat.Cyclic;
import com.black_dog20.jetboots.common.compat.MekanismTools;
import com.black_dog20.jetboots.common.items.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.ModList;

import static com.black_dog20.jetboots.common.util.TranslationHelper.*;
import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.*;

public class GeneratorLanguage extends BaseLanguageProvider {

    public GeneratorLanguage(DataGenerator gen) {
        super(gen, Jetboots.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.jetboots", "Jet Boots");
        add("itemGroup.jetboots_compat", "Jet Boots Extras");
        add("key.jetboots.engine", "Toggle Jetboots engine mode");
        add("key.jetboots.speed", "Toggle Jetboots speed mode");
        add("key.jetboots.helmet", "Toggle Guardian Helmet mode");
        add("key.jetboots.helmet_vision", "Toggle Guardian Helmet night vision");
        addPrefixed(KEY_CATEGORY, "Jet Boots");
        addItem(ModItems.JET_BOOTS, "Jet Boots");
        addItem(ModItems.BASE_UPGRADE, "Base Upgrade");
        addItem(ModItems.LEATHER_ARMOR_UPGRADE, "Leather Armor Upgrade");
        addItem(ModItems.IRON_ARMOR_UPGRADE, "Iron Armor Upgrade");
        addItem(ModItems.DIAMOND_ARMOR_UPGRADE, "Diamond Armor Upgrade");
        addItem(ModItems.ADVANCED_BATTERY_UPGRADE, "Advanced Battery Upgrade");
        addItem(ModItems.ELITE_BATTERY_UPGRADE, "Elite Battery Upgrade");
        addItem(ModItems.ULTIMATE_BATTERY_UPGRADE, "Ultimate Battery Upgrade");
        addItem(ModItems.THRUSTER_UPGRADE, "Thruster Upgrade");
        addItem(ModItems.BASIC_CONVERTER_UPGRADE, "Basic Converter Upgrade");
        addItem(ModItems.ADVANCED_CONVERTER_UPGRADE, "Advanced Converter Upgrade");
        addItem(ModItems.ELITE_CONVERTER_UPGRADE, "Elite Converter Upgrade");
        addItem(ModItems.ULTIMATE_CONVERTER_UPGRADE, "Ultimate Converter Upgrade");
        addItem(ModItems.ENGINE_UPGRADE, "Engine Upgrade");
        addItem(ModItems.SHOCK_ABSORBER_UPGRADE, "Shock Absorber Upgrade");
        addItem(ModItems.MUFFLED_UPGRADE, "Muffled Upgrade");
        addItem(ModItems.UNDERWATER_UPGRADE, "Underwater Upgrade");
        addItem(ModItems.SOULBOUND_UPGRADE, "Soulbound Upgrade");
        addItem(ModItems.GUARDIAN_HELMET, "Guardian Helmet");

        // Blocks
        //addBlock(ModBlocks., "");

        // Tooltips
        addPrefixed(SHOW_UPGRADES, "Hold %s to show upgrades");
        addPrefixed(OPEN_UPGRADES, "Sneak + Right click to apply upgrades");
        addPrefixed(SHOW_ENERGY, "Hold %s + %s to show energy stats");
        addPrefixed(UPGRADES, "Upgrades");
        addPrefixed(LEATHER_ARMOR, "Armored: Leather", TextFormatting.GRAY);
        addPrefixed(IRON_ARMOR, "Armored: Iron", TextFormatting.GRAY);
        addPrefixed(DIAMOND_ARMOR, "Armored: Diamond", TextFormatting.BLUE);
        addPrefixed(ENGINE_UPGRADE, "Upgraded engine", TextFormatting.GRAY);
        addPrefixed(THRUSTER_UPGRADE, "Upgraded thruster", TextFormatting.GRAY);
        addPrefixed(SHOCK_ABSORBER_UPGRADE, "Shock absorber", TextFormatting.GRAY);
        addPrefixed(UNDERWATER_UPGRADE, "Water proof", TextFormatting.AQUA);
        addPrefixed(SOULBOUND_UPGRADE, "Soulbound", TextFormatting.LIGHT_PURPLE);
        addPrefixed(MUFFLED_UPGRADE, "Muffled", TextFormatting.GRAY);
        addPrefixed(BASIC_BATTERY_UPGRADE, "Basic Battery", TextFormatting.GRAY);
        addPrefixed(ADVANCED_BATTERY_UPGRADE, "Advanced Battery", TextFormatting.BLUE);
        addPrefixed(ELITE_BATTERY_UPGRADE, "Elite Battery", TextFormatting.RED);
        addPrefixed(ULTIMATE_BATTERY_UPGRADE, "Ultimate Battery", TextFormatting.LIGHT_PURPLE);
        addPrefixed(BASIC_CONVERTER_UPGRADE, "Basic Converter", TextFormatting.GRAY);
        addPrefixed(ADVANCED_CONVERTER_UPGRADE, "Advanced Converter", TextFormatting.BLUE);
        addPrefixed(ELITE_CONVERTER_UPGRADE, "Elite Converter", TextFormatting.RED);
        addPrefixed(ULTIMATE_CONVERTER_UPGRADE, "Ultimate Converter", TextFormatting.LIGHT_PURPLE);

        addPrefixed(ARMOR_INFO, "Provides protection", TextFormatting.GRAY);
        addPrefixed(ENGINE_UPGRADE_INFO, "Unlocks Elytra based flight", TextFormatting.GRAY);
        addPrefixed(THRUSTER_UPGRADE_INFO, "Unlocks super speed toggle", TextFormatting.GRAY);
        addPrefixed(SHOCK_ABSORBER_UPGRADE_INFO, "Removes fall damage while wearing jet boots", TextFormatting.GRAY);
        addPrefixed(UNDERWATER_UPGRADE_INFO, "Makes the jet boots work under water", TextFormatting.GRAY);
        addPrefixed(SOULBOUND_UPGRADE_INFO, "Death will not part you from these boots", TextFormatting.GRAY);
        addPrefixed(MUFFLED_UPGRADE_INFO, "Makes the jet boots silent", TextFormatting.GRAY);
        addPrefixed(BATTERY_UPGRADE_INFO, "Provides %dx more energy storage", TextFormatting.GRAY);
        addPrefixed(CONVERTER_UPGRADE_INFO, "Increases power efficiency", TextFormatting.GRAY);

        addPrefixed(CHANGE_FLIGHT_MODE, "Use %s to change flight mode");
        addPrefixed(CHANGE_SPEED_MODE, "Use %s to change speed");
        addPrefixed(FLIGHT_MODE, "Flight mode: %s");
        addPrefixed(STORED_ENERGY, "Energy: %d/%d");
        addPrefixed(SPEED_MODE, "Speed: %s");
        addPrefixed(NORMAL, "Normal");
        addPrefixed(BASIC, "Basic");
        addPrefixed(ADVANCED, "Advanced");
        addPrefixed(SUPER, "Super");
        addPrefixed(ELYTRA, "Elytra");
        addPrefixed(ENERGY_USE_MORE, "Makes the boots draw %.2f%% more FE while flying", TextFormatting.GRAY);
        addPrefixed(ENERGY_USE_LESS, "Makes the boots draw %.2f%% less FE while flying", TextFormatting.GRAY);
        addPrefixed(ENERGY_USE_DRAW, "While flying draws %d FE", TextFormatting.GRAY);
        addPrefixed(ENERGY_USE_GENERATE, "While flying generate %d FE", TextFormatting.GRAY);
        addPrefixed(ENERGY_HIT_DRAW, "Every hit draws %d FE", TextFormatting.GRAY);
        addPrefixed(ENERGY_HIT_GENERATE, "Every hit generate %d FE", TextFormatting.GRAY);
        addPrefixed(ENERGY_HURT_DRAW, "Every time the wearer is hurt draws %d FE", TextFormatting.GRAY);
        addPrefixed(ENERGY_HURT_GENERATE, "Every time the wearer is hurt generate %d FE", TextFormatting.GRAY);
        addPrefixed(ENERGY_WALK_DRAW, "Draws %d FE while walking on ground", TextFormatting.GRAY);
        addPrefixed(ENERGY_WALK_GENERATE, "Generate %d FE while walking on ground", TextFormatting.GRAY);
        addPrefixed(FLYING_ENERGY, "%s while flying", TextFormatting.GRAY);
        addPrefixed(HIT_ENERGY, "%s on hit", TextFormatting.GRAY);
        addPrefixed(HURT_ENERGY, "%s on being hurt", TextFormatting.GRAY);
        addPrefixed(WALKING_ENERGY, "%s while walking", TextFormatting.GRAY);
        addPrefixed(JETBOOTS_UPGRADES, "Jet Boots Upgrades");
        addPrefixed(CHANGE_HELMET_MODE, "Use %s to change helmet mode");
        addPrefixed(CHANGE_HELMET_NIGHT_VISION, "Use %s to toggle night vision");
        addPrefixed(HELMET_INFO, "Protects against air loss and other ill effects when materialized");
        addPrefixed(HELMET_MODE, "Guardian Helmet: %s");
        addPrefixed(MATERIALIZED, "Materialized");
        addPrefixed(DEMATERIALIZED, "Dematerialized");
        addPrefixed(CANNOT_EAT_WHILE_MATERIALIZED, "You cannot " + style("eat", TextFormatting.LIGHT_PURPLE) + " or " + style("drink", TextFormatting.LIGHT_PURPLE) + " while the helmet is materialized");
        addPrefixed(ARMOR_VALUE, "Armor: %d", TextFormatting.BLUE);
        addPrefixed(TOUGHNESS_VALUE, "Toughness: %d", TextFormatting.BLUE);
        addPrefixed(NO_CHANCE_TO_FIND, "There are no chance to find this item!", TextFormatting.RED);
        addPrefixed(HELMET_NIGHT_VISION, "Night Vision: %s");
        addPrefixed(POWER_LOW, "WARNING Jet boots power low!!", TextFormatting.RED);
        addPrefixed(ON, "On");
        addPrefixed(OFF, "Off");

        //JEI
        addPrefixed(JeiInfo.GUARDING_HELMET, "The Guarding went missing in The End or was it Nether, more important did he take his helmet with him?", TextFormatting.DARK_GRAY);

        //Compat
        if (ModList.get().isLoaded(Cyclic.MOD_ID)) {
            addItem(Cyclic.CRYSTAL_ARMOR_UPGRADE_CYCLIC, "Crystal Armor Upgrade (Cyclic)");
            addItem(Cyclic.EMERALD_ARMOR_UPGRADE_CYCLIC, "Emerald Armor Upgrade (Cyclic)");

            addPrefixed(Compat.EMERALD_ARMOR_CYCLIC, "Armored: Emerald (Cyclic)", TextFormatting.GREEN);
            addPrefixed(Compat.CRYSTAL_ARMOR_CYCLIC, "Armored: Crystal (Cyclic)", TextFormatting.DARK_PURPLE);
        }

        if (ModList.get().isLoaded(Cyclic.MOD_ID)) {
            addItem(MekanismTools.BRONZE_ARMOR_UPGRADE_MEKANISM, "Broze Armor Upgrade (Mekanism)");
            addItem(MekanismTools.LAPIS_ARMOR_UPGRADE_MEKANISM, "Lapis Lazuli Armor Upgrade (Mekanism)");
            addItem(MekanismTools.OSMIUM_ARMOR_UPGRADE_MEKANISM, "Osmium Armor Upgrade (Mekanism)");
            addItem(MekanismTools.REFINED_GLOWSTONE_ARMOR_UPGRADE_MEKANISM, "Refined Glowstone Armor Upgrade (Mekanism)");
            addItem(MekanismTools.REFINED_OBSIDIAN_ARMOR_UPGRADE_MEKANISM, "Refined Obsidian Armor Upgrade (Mekanism)");
            addItem(MekanismTools.STEEL_ARMOR_UPGRADE_MEKANISM, "Steel Armor Upgrade (Mekanism)");


            addPrefixed(Compat.BRONZE_ARMOR_MEKANISM, "Armored: Bronze (Mekanism)", TextFormatting.GOLD);
            addPrefixed(Compat.LAPIS_ARMOR_MEKANISM, "Armored: Lapis Lazuli (Mekanism)", TextFormatting.BLUE);
            addPrefixed(Compat.OSMIUM_ARMOR_MEKANISM, "Armored: Osmium (Mekanism)", TextFormatting.GRAY);
            addPrefixed(Compat.REFINED_GLOWSTONE_ARMOR_MEKANISM, "Armored: Refined Glowstone (Mekanism)", TextFormatting.YELLOW);
            addPrefixed(Compat.REFINED_OBSIDIAN_ARMOR_MEKANISM, "Armored: Refined Obsidian (Mekanism)", TextFormatting.DARK_PURPLE);
            addPrefixed(Compat.STEEL_ARMOR_MEKANISM, "Armored: Steel (Mekanism)", TextFormatting.DARK_GRAY);
        }

    }
}
