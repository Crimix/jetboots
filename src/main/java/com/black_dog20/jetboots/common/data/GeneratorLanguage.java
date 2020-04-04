package com.black_dog20.jetboots.common.data;

import com.black_dog20.bml.datagen.BaseLanguageProvider;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.ModItems;
import net.minecraft.data.DataGenerator;

import static com.black_dog20.jetboots.common.util.TranslationHelper.Tooltips.*;

public class GeneratorLanguage extends BaseLanguageProvider {
    
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
        addPrefixed(SHOW_UPGRADES,"Hold %s to show upgrades");
        addPrefixed(UPGRADES,"Upgrades");
        addPrefixed(CUSTOM_ARMOR,"Armored: %s");
        addPrefixed(LEATHER_ARMOR,"Armored: Leather");
        addPrefixed(IRON_ARMOR,"Armored: Iron");
        addPrefixed(DIAMOND_ARMOR,"Armored: Diamond");
        addPrefixed(ENGINE_UPGRADE,"Upgraded engine");
        addPrefixed(THRUSTER_UPGRADE,"Upgraded thruster");
        addPrefixed(SHOCK_ABSORBER_UPGRADE,"Shock absorber");
        addPrefixed(UNDERWATER_UPGRADE,"Water proof");
        addPrefixed(SOULBOUND_UPGRADE,"Soulbound");
        addPrefixed(MUFFLED_UPGRADE,"Muffled");
        addPrefixed(BATTERY_UPGRADE,"Battery: %s");
        
        addPrefixed(CHANGE_FLIGHT_MODE,"Use %s to change flight mode");
        addPrefixed(CHANGE_SPEED_MODE,"Use %s to change speed");
        addPrefixed(FLIGHT_MODE,"Flight mode: %s");
        addPrefixed(STORED_ENERGY,"Energy: %d/%d");
        addPrefixed(SPEED_MODE,"Speed: %s");
        addPrefixed(NORMAL,"Normal");
        addPrefixed(BASIC,"Basic");
        addPrefixed(ADVANCED,"Advanced");
        addPrefixed(SUPER,"Super");
        addPrefixed(ELYTRA,"Elytra");

    }
}
