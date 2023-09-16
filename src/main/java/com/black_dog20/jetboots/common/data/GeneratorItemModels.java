package com.black_dog20.jetboots.common.data;

import com.black_dog20.bml.datagen.BaseItemModelProvider;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.compat.ModCompat;
import com.black_dog20.jetboots.common.items.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class GeneratorItemModels extends BaseItemModelProvider {
    public GeneratorItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), Jetboots.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Register all of the upgrade items
        ModItems.ITEMS.getEntries().stream()
                .map(RegistryObject::get)
                .forEach(this::registerItemModel);

        ModCompat.ITEMS.getEntries().stream()
                .map(RegistryObject::get)
                .forEach(this::registerItemModel);
    }

    @Override
    public String getName() {
        return "Jet Boots: Item Models";
    }
}
