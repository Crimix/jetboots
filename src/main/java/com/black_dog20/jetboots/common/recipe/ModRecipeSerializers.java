package com.black_dog20.jetboots.common.recipe;

import com.black_dog20.jetboots.Jetboots;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Jetboots.MOD_ID);

    public static final RegistryObject<SimpleRecipeSerializer<?>> CUSTOM_SMITHING = register("custom_smithing", new SimpleRecipeSerializer<>(CustomSmithingRecipe::new));

    private static <T extends RecipeSerializer<?>> RegistryObject<T> register(String name, T recipeSerializer) {
        return RECIPE_SERIALIZERS.register(name, () -> recipeSerializer);
    }

}
