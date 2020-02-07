package com.black_dog20.jetboots.common.crafting;

import java.util.Map;

import com.black_dog20.jetboots.Jetboots;
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
					output.getOrCreateTag().merge(ingredient.getOrCreateTag());
				}
			}
		}

		return output;
	}
	
	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModCrafting.SHAPED_NBT.get();
	}
	
	public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>>  implements IRecipeSerializer<ShapedRecipe> {
      
	      public ShapedRecipe read(ResourceLocation recipeId, JsonObject json) {
	    	  final String group = JSONUtils.getString(json, "group", "");
				final RecipeUtil.ShapedPrimer primer = RecipeUtil.parseShaped(json);
				final ItemStack result = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "result"), true);

				return new ShapedNBTRecipe(recipeId, group, primer.getRecipeWidth(), primer.getRecipeHeight(), primer.getIngredients(), result);

	      }

	      public ShapedRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
	         int i = buffer.readVarInt();
	         int j = buffer.readVarInt();
	         String s = buffer.readString(32767);
	         NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);

	         for(int k = 0; k < nonnulllist.size(); ++k) {
	            nonnulllist.set(k, Ingredient.read(buffer));
	         }

	         ItemStack itemstack = buffer.readItemStack();
	         return new ShapedNBTRecipe(recipeId, s, i, j, nonnulllist, itemstack);
	      }

	      public void write(PacketBuffer buffer, ShapedRecipe recipe) {
	         buffer.writeVarInt(recipe.getRecipeWidth());
	         buffer.writeVarInt(recipe.getRecipeHeight());
	         buffer.writeString(recipe.getGroup());

	         for(Ingredient ingredient : recipe.getIngredients()) {
	            ingredient.write(buffer);
	         }

	         buffer.writeItemStack(recipe.getRecipeOutput());
	      }
	   }
}