package com.black_dog20.jetboots.common.crafting;

import com.black_dog20.jetboots.common.items.JetBootsItem;
import com.google.gson.JsonObject;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ShapedNBTRecipe extends ShapedRecipe {
	private ShapedNBTRecipe(final ResourceLocation id, final String group, final int recipeWidth, final int recipeHeight, final NonNullList<Ingredient> ingredients, final ItemStack recipeOutput) {
		super(id, group, recipeWidth, recipeHeight, ingredients, recipeOutput);
	}

	@Override
	public ItemStack getCraftingResult(final CraftingInventory inv) {
		final ItemStack output = super.getCraftingResult(inv);

		if (!output.isEmpty()) {
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				final ItemStack ingredient = inv.getStackInSlot(i);

				if (!ingredient.isEmpty() && ingredient.getItem() instanceof JetBootsItem) {
					output.getOrCreateTag().merge(ingredient.copy().getOrCreateTag());
				}
			}
		}

		return output;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModCrafting.SHAPED_NBT.get();
	}

	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ShapedNBTRecipe> {
		@Override
		public ShapedNBTRecipe read(final ResourceLocation recipeID, final JsonObject json) {
			final String group = JSONUtils.getString(json, "group", "");
			final RecipeUtil.ShapedPrimer primer = RecipeUtil.parseShaped(json);
			final ItemStack result = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "result"), true);

			return new ShapedNBTRecipe(recipeID, group, primer.getRecipeWidth(), primer.getRecipeHeight(), primer.getIngredients(), result);
		}

		@Override
		public ShapedNBTRecipe read(final ResourceLocation recipeID, final PacketBuffer buffer) {
			final int width = buffer.readVarInt();
			final int height = buffer.readVarInt();
			final String group = buffer.readString(Short.MAX_VALUE);
			final NonNullList<Ingredient> ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);

			for (int i = 0; i < ingredients.size(); ++i) {
				ingredients.set(i, Ingredient.read(buffer));
			}

			final ItemStack result = buffer.readItemStack();

			return new ShapedNBTRecipe(recipeID, group, width, height, ingredients, result);
		}

		@Override
		public void write(final PacketBuffer buffer, final ShapedNBTRecipe recipe) {
			buffer.writeVarInt(recipe.getRecipeWidth());
			buffer.writeVarInt(recipe.getRecipeHeight());
			buffer.writeString(recipe.getGroup());

			for (final Ingredient ingredient : recipe.getIngredients()) {
				ingredient.write(buffer);
			}

			buffer.writeItemStack(recipe.getRecipeOutput());
		}
	}
}