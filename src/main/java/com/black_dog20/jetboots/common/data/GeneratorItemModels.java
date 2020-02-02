package com.black_dog20.jetboots.common.data;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;

public class GeneratorItemModels extends ItemModelProvider {
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
    }

    private void registerBlockModel(Block block) {
        String path = block.getRegistryName().getPath();
        getBuilder(path).parent(new ModelFile.UncheckedModelFile(modLoc("block/" + path)));
    }

    @Override
    public String getName() {
        return "Item Models";
    }
}
