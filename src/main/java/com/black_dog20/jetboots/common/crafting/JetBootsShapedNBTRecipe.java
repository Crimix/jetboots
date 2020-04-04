package com.black_dog20.jetboots.common.crafting;

import com.black_dog20.bml.crafting.ShapedNBTRecipe;
import com.black_dog20.jetboots.common.items.JetBootsItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class JetBootsShapedNBTRecipe extends ShapedNBTRecipe {

	private JetBootsShapedNBTRecipe(final ResourceLocation id, final String group, final int recipeWidth, final int recipeHeight, final NonNullList<Ingredient> ingredients, final ItemStack recipeOutput) {
		super(id, group, recipeWidth, recipeHeight, ingredients, recipeOutput);
	}

	@Override
	public ItemStack getCraftingResult(final CraftingInventory inv) {
		final ItemStack output = super.getCraftingResult(inv);

		if (!output.isEmpty()) {
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				final ItemStack ingredient = inv.getStackInSlot(i);

				if (!ingredient.isEmpty() && ingredient.getItem() instanceof JetBootsItem) {
					output.getOrCreateTag().merge(ingredient.getOrCreateTag());
				}
			}
		}

		return output;
	}

	@Override
	public boolean matches(CraftingInventory inv, World worldIn) {
		for(String tag : getRecipeOutput().getOrCreateTag().keySet()){
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				final ItemStack ingredient = inv.getStackInSlot(i);

				if (!ingredient.isEmpty() && ingredient.getItem() instanceof JetBootsItem) {
					if(ingredient.getOrCreateTag().contains(tag))
						return false;
				}
			}
		}

		return super.matches(inv, worldIn);
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModCrafting.JETBOOTS_SHAPED_NBT.get();
	}

	public static Supplier<Serializer> factory() {
		return () -> new Serializer(JetBootsShapedNBTRecipe::new);
	}
}