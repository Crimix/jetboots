package com.black_dog20.jetboots.common.data;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.ModItems;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class GeneratorLanguage extends LanguageProvider {
    
	public GeneratorLanguage(DataGenerator gen) {
        super(gen, Jetboots.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.jetboots", "Jet Boots");
        addItem(ModItems.JET_BOOTS, "Jet Boots");

        // Blocks
        //addBlock(ModBlocks., "");

        // Tooltips
        addPrefixed("tooltip.item.show_upgrades","Hold %s to show upgrades");
        addPrefixed("tooltip.item.upgrades","Upgrades");
        addPrefixed("tooltip.item.custom_armor_upgrade","Armored: %s");
        addPrefixed("tooltip.item.leather_armor_upgrade","Armored: Leather");
        addPrefixed("tooltip.item.iron_armor_upgrade","Armored: Iron");
        addPrefixed("tooltip.item.diamond_armor_upgrade","Armored: Diamond");
        addPrefixed("tooltip.item.engine_upgrade","Upgraded engine");
        addPrefixed("tooltip.item.thruster_upgrade","Upgraded thruster");
        addPrefixed("tooltip.item.shock_absorber_upgrade","Shock absorber");
        addPrefixed("tooltip.item.underwater_upgrade","Water proof");
        addPrefixed("tooltip.item.soulbound_upgrade","Soulbound");
        addPrefixed("tooltip.item.muffled_upgrade","Muffled");
        addPrefixed("tooltip.item.battery_upgrade","Battery: %s");
        
        addPrefixed("tooltip.item.change_flight","Use %s to change flight mode");
        addPrefixed("tooltip.item.change_speed","Use %s to change speed");
        addPrefixed("tooltip.item.flight_mode","Flight mode: %s");
        addPrefixed("tooltip.item.stored_energy","Energy: %d/%d");
        addPrefixed("tooltip.item.speed_mode","Speed: %s");
        addPrefixed("tooltip.item.normal","Normal");
        addPrefixed("tooltip.item.basic","Basic");
        addPrefixed("tooltip.item.advanced","Advanced");
        addPrefixed("tooltip.item.super","Super");
        addPrefixed("tooltip.item.elytra","Elytra");

    }

    /**
     * Very simply, prefixes all the keys with the mod_id.{key} instead of
     * having to input it manually
     */
    private void addPrefixed(String key, String text) {
        add(String.format("%s.%s", Jetboots.MOD_ID, key), text);
    }
}
