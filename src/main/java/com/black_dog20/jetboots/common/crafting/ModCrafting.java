package com.black_dog20.jetboots.common.crafting;

import com.black_dog20.jetboots.Jetboots;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModCrafting {
	
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, Jetboots.MOD_ID);
	
    public static final RegistryObject<IRecipeSerializer<?>> SHAPED_NBT = RECIPE_SERIALIZERS.register("jetboots_shaped_nbt", ()-> new ShapedNBTRecipe.Serializer());
    
    
    public static void registerRecipeSerialziers(RegistryEvent.Register<IRecipeSerializer<?>> event)
    {
        CraftingHelper.register(BatteryOnCondition.Serializer.INSTANCE);
    }
    
	
}
