package com.black_dog20.jetboots.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.crafting.NBTIngredient;

public class IngredientNBT extends NBTIngredient{

	protected IngredientNBT(ItemStack stack) {
		super(stack);
	}

	public static IngredientNBT fromNBTStack(ItemStack stack) {
		return new IngredientNBT(stack);
	}

}
