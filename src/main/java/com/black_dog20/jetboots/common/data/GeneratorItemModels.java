package com.black_dog20.jetboots.common.data;

import com.black_dog20.bml.datagen.BaseItemModelProvider;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.compat.ModCompat;
import com.black_dog20.jetboots.common.items.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class GeneratorItemModels extends BaseItemModelProvider {
    public GeneratorItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Jetboots.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Register all of the upgrade items
        ModItems.ITEMS.getEntries().forEach(item -> {
            String path = item.get().getRegistryName().getPath();
            singleTexture(path, mcLoc("item/handheld"), "layer0", modLoc("item/" + path));
        });

        ModCompat.ITEMS.getEntries().forEach(item -> {
            String path = item.get().getRegistryName().getPath();
            singleTexture(path, mcLoc("item/handheld"), "layer0", modLoc("item/" + path));
        });
    }

    @Override
    public String getName() {
        return "Jet Boots: Item Models";
    }
}
