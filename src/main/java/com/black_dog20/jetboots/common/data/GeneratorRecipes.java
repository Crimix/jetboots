package com.black_dog20.jetboots.common.data;

import static com.black_dog20.jetboots.common.items.ModItems.JET_BOOTS;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraftforge.common.Tags;

public class GeneratorRecipes extends RecipeProvider {
	
    public GeneratorRecipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(JET_BOOTS.get())
                .key('r', Tags.Items.DUSTS_REDSTONE)
                .key('g', Tags.Items.GLASS_PANES)
                .key('l', Tags.Items.GEMS_LAPIS)
                .key('d', Tags.Items.GEMS_DIAMOND)
                .patternLine("rlr")
                .patternLine("dgd")
                .patternLine("rlr")
                .build(consumer);

    }
}
