package com.black_dog20.jetboots.common.integrations.jei;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.util.TranslationHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import static com.black_dog20.jetboots.common.util.TranslationHelper.*;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Jetboots.MOD_ID);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addIngredientInfo(new ItemStack(ModItems.GUARDIAN_HELMET.get()), VanillaTypes.ITEM, TranslationHelper.translateToString(JeiInfo.GUARDING_HELMET));
        Jetboots.getLogger().debug("JEI Recipes registered");
    }

}