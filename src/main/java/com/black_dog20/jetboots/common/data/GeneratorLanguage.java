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
        add("itemGroup.mininggadgets", "Mining Gadgets");
        addItem(ModItems.JET_BOOTS, "Jet Boots");

        // Blocks
        //addBlock(ModBlocks., "");

        // Tooltips
        addPrefixed("tooltip.single.insert", "Insert");

    }

    /**
     * Very simply, prefixes all the keys with the mod_id.{key} instead of
     * having to input it manually
     */
    private void addPrefixed(String key, String text) {
        add(String.format("%s.%s", Jetboots.MOD_ID, key), text);
    }
}
