package com.black_dog20.jetboots.common.data;

import com.black_dog20.bml.datagen.BaseLanguageProvider;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.compat.refinedstorage.RefinedStorageCompat;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.util.TranslationHelper;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.ModList;

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
        addItem(ModItems.OBSIDIAN_INFUSED_GOLD, "Obsidian-Infused Gold");
        addItem(ModItems.ARMOR_CORE, "Armor Core");
        addItem(ModItems.THRUSTER_UPGRADE, "Thruster Upgrade");
        addItem(ModItems.ENGINE_UPGRADE, "Engine Upgrade");
        addItem(ModItems.SHOCK_ABSORBER_UPGRADE, "Shock Absorber Upgrade");
        addItem(ModItems.MUFFLED_UPGRADE, "Muffled Upgrade");
        addItem(ModItems.UNDERWATER_UPGRADE, "Underwater Upgrade");
        addItem(ModItems.GUARDIAN_HELMET, "Guardian Helmet");
        addItem(ModItems.GUARDIAN_JACKET, "Guardian Jacket");
        addItem(ModItems.GUARDIAN_PANTS, "Guardian Pants");
        addItem(ModItems.GUARDIAN_SWORD, "Godslayer");

        // Blocks
        //addBlock(ModBlocks., "");

        // Tooltips
        addPrefixed(SHOW_MORE_INFO, "Hold %s to show more info");
        addPrefixed(OPEN_CONTAINER, "Sneak + Right click to apply enchantments");
        addPrefixed(ENGINE_UPGRADE, "Upgraded engine", TextFormatting.GRAY);
        addPrefixed(THRUSTER_UPGRADE, "Upgraded thruster", TextFormatting.GRAY);
        addPrefixed(SHOCK_ABSORBER_UPGRADE, "Shock absorber", TextFormatting.GRAY);
        addPrefixed(UNDERWATER_UPGRADE, "Water proof", TextFormatting.AQUA);
        addPrefixed(MUFFLED_UPGRADE, "Muffled", TextFormatting.GRAY);

        addPrefixed(ENGINE_UPGRADE_INFO, "Unlocks Elytra based flight", TextFormatting.GRAY);
        addPrefixed(THRUSTER_UPGRADE_INFO, "Unlocks super speed toggle", TextFormatting.GRAY);
        addPrefixed(SHOCK_ABSORBER_UPGRADE_INFO, "Removes fall damage while wearing jet boots", TextFormatting.GRAY);
        addPrefixed(UNDERWATER_UPGRADE_INFO, "Makes the jet boots work under water", TextFormatting.GRAY);
        addPrefixed(MUFFLED_UPGRADE_INFO, "Makes the jet boots silent", TextFormatting.GRAY);

        addPrefixed(NOT_LEVELABLE, "Unlevelable", TextFormatting.RED);
        addPrefixed(MAX_LEVEL, "Max", TextFormatting.DARK_PURPLE);
        addPrefixed(LEVEL, "Level");
        addPrefixed(PROGRESS, "%d (%d/%d)");
        addPrefixed(SOULBOUND_ACHIEVED, "You feel fused together with your %s, not even death will part you with it now!");

        addPrefixed(CHANGE_FLIGHT_MODE, "Use %s to change flight mode");
        addPrefixed(CHANGE_SPEED_MODE, "Use %s to change speed");
        addPrefixed(FLIGHT_MODE, "Flight mode");
        addPrefixed(STORED_ENERGY, "Energy: %d/%d");
        addPrefixed(SPEED_MODE, "Speed");
        addPrefixed(NORMAL, "Normal");
        addPrefixed(SUPER, "Super");
        addPrefixed(ELYTRA, "Elytra");
        addPrefixed(SOULBOUND, "Soulbound");
        addPrefixed(FLYING_ENERGY, "Cost", TextFormatting.GRAY);
        addPrefixed(JETBOOTS_UPGRADES, "Jet Boots Upgrades");
        addPrefixed(CHANGE_HELMET_MODE, "Use %s to change helmet mode");
        addPrefixed(CHANGE_HELMET_NIGHT_VISION, "Use %s to toggle night vision");
        addPrefixed(HELMET_INFO, "Protects against air loss and other ill effects when materialized");
        addPrefixed(HELMET_MODE, "Guardian Helmet");
        addPrefixed(MATERIALIZED, "Materialized");
        addPrefixed(DEMATERIALIZED, "Dematerialized");
        addPrefixed(HELMET_NIGHT_VISION, "Night Vision");
        addPrefixed(POWER_LOW, "WARNING Jet boots power low!!", TextFormatting.RED);
        addPrefixed(ON, "On");
        addPrefixed(OFF, "Off");

        if (ModList.get().isLoaded(RefinedStorageCompat.MOD_ID)) {
            add("key.jetboots.open_crafting_grid", "Open Refined Storage crafting grid");
            addItem(RefinedStorageCompat.WIRELESS_CRAFTING_UPGRADE, "Wireless crafting grid upgrade (RS)");
            addItem(RefinedStorageCompat.WIRELESS_RANGE_UPGRADE, "Wireless grid range upgrade (RS)");

            addPrefixed(TranslationHelper.Compat.WIRELESS_CRAFTING_USE, "Use %s to access RS network", TextFormatting.GRAY);
            addPrefixed(TranslationHelper.Compat.WIRELESS_CRAFTING_UPGRADE, "Wireless Transmitter (RS)", TextFormatting.GRAY);
            addPrefixed(TranslationHelper.Compat.WIRELESS_CRAFTING_UPGRADE_INFO, "Upgrades the helmet to access the RS network", TextFormatting.GRAY);
            addPrefixed(TranslationHelper.Compat.WIRELESS_RANGE_UPGRADE, "Wireless Range %s (RS)", TextFormatting.GRAY);
            addPrefixed(TranslationHelper.Compat.WIRELESS_RANGE_UPGRADE_INFO, "Upgrades the helmet range to the RS network", TextFormatting.GRAY);
            addPrefixed(TranslationHelper.Compat.WIRELESS_RANGE_UPGRADE_INFO_2, "Max 4 times", TextFormatting.GRAY);
            addPrefixed(TranslationHelper.Compat.WIRELESS_CRAFTING_UPGRADE_NOT_INSTALLED, "Wireless crafting grid upgrade not installed!");
            addPrefixed(TranslationHelper.Compat.NETWORK_LINKED, "Helmet linked to network!");
            addPrefixed(TranslationHelper.Compat.NOT_LINKED_TO_NETWORK, "Not linked to a network!\nRight click the helmet on a network");
        }
    }

    @Override
    public String getName() {
        return "Jet Boots: Languages: en_us";
    }
}
