package com.black_dog20.jetboots.common.crafting;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;

public class ModCrafting {

    public static void registerRecipeSerialziers(RegistryEvent.Register<IRecipeSerializer<?>> event)
    {
        CraftingHelper.register(BatteryOnCondition.Serializer.INSTANCE);
    }
    
	
}
