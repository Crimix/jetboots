package com.black_dog20.jetboots.common.integrations.jei;

import com.black_dog20.jetboots.Jetboots;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Jetboots.MOD_ID);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(RecipeTypes.SMITHING, CustomSmithingRecipeMaker.getRecipes());
        Jetboots.getLogger().debug("JEI Recipes registered");
    }

}