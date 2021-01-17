package com.black_dog20.jetboots.common.recipe;

import com.black_dog20.jetboots.Jetboots;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeSerializers {

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Jetboots.MOD_ID);

    public static final RegistryObject<SpecialRecipeSerializer<?>> CUSTOM_SMITHING = register("custom_smithing", new SpecialRecipeSerializer<>(CustomSmithingRecipe::new));

    private static <T extends IRecipeSerializer<?>> RegistryObject<T> register(String name, T recipeSerializer) {
        return RECIPE_SERIALIZERS.register(name, () -> recipeSerializer);
    }

}
