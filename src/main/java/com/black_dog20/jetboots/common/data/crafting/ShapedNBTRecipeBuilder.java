package com.black_dog20.jetboots.common.data.crafting;

import com.black_dog20.jetboots.common.crafting.ModCrafting;
import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ShapedNBTRecipeBuilder extends ShapedRecipeBuilder {
	private static final Method VALIDATE = ObfuscationReflectionHelper.findMethod(ShapedRecipeBuilder.class, "func_200463_a" /* validate */, ResourceLocation.class);
	private static final Field ADVANCEMENT_BUILDER = ObfuscationReflectionHelper.findField(ShapedRecipeBuilder.class, "field_200479_f" /* advancementBuilder */);
	private static final Field GROUP = ObfuscationReflectionHelper.findField(ShapedRecipeBuilder.class, "field_200480_g" /* group */);
	private static final Field PATTERN = ObfuscationReflectionHelper.findField(ShapedRecipeBuilder.class, "field_200477_d" /* pattern */);
	private static final Field KEY = ObfuscationReflectionHelper.findField(ShapedRecipeBuilder.class, "field_200478_e" /* key */);

	
	private final ItemStack result;
	private String itemGroup;

	private ShapedNBTRecipeBuilder(final ItemStack result) {
		super(result.getItem(), result.getCount());
		this.result = result;
	}

	public static ShapedNBTRecipeBuilder shapedNBTRecipe(final ItemStack result) {
		return new ShapedNBTRecipeBuilder(result);
	}

	public ShapedNBTRecipeBuilder setItemGroup(final String group) {
		itemGroup = group;
		return this;
	}
	
	@Override
	public ShapedNBTRecipeBuilder key(final Character symbol, final Tag<Item> tagIn) {
		return (ShapedNBTRecipeBuilder) super.key(symbol, tagIn);
	}

	@Override
	public ShapedNBTRecipeBuilder key(final Character symbol, final IItemProvider itemIn) {
		return (ShapedNBTRecipeBuilder) super.key(symbol, itemIn);
	}

	@Override
	public ShapedNBTRecipeBuilder key(final Character symbol, final Ingredient ingredientIn) {
		return (ShapedNBTRecipeBuilder) super.key(symbol, ingredientIn);
	}

	@Override
	public ShapedNBTRecipeBuilder patternLine(final String pattern) {
		return (ShapedNBTRecipeBuilder) super.patternLine(pattern);
	}

	@Override
	public ShapedNBTRecipeBuilder addCriterion(final String name, final ICriterionInstance criterion) {
		return (ShapedNBTRecipeBuilder) super.addCriterion(name, criterion);
	}

	@Override
	public ShapedNBTRecipeBuilder setGroup(final String group) {
		return (ShapedNBTRecipeBuilder) super.setGroup(group);
	}


	@Override
	public void build(final Consumer<IFinishedRecipe> consumer) {
		build(consumer, result.getItem().getRegistryName());
	}

	@Override
	public void build(final Consumer<IFinishedRecipe> consumer, final String save) {
		final ResourceLocation registryName = result.getItem().getRegistryName();
		if (new ResourceLocation(save).equals(registryName)) {
			throw new IllegalStateException("Shaped Recipe " + save + " should remove its 'save' argument");
		} else {
			build(consumer, new ResourceLocation(save));
		}
	}

	private void validate(final ResourceLocation id) {
		if (!result.hasTag() && itemGroup == null) {
			throw new IllegalStateException("Enhanced Shaped Recipe " + id + " has no NBT and no custom item group - use ShapedRecipeBuilder instead");
		}

		if (itemGroup == null && result.getItem().getGroup() == null) {
			throw new IllegalStateException("Enhanced Shaped Recipe " + id + " has result " + result + " with no item group - use ShapedNBTRecipeBuilder.itemGroup to specify one");
		}
	}


	@Override
	public void build(final Consumer<IFinishedRecipe> consumer, final ResourceLocation id) {
		try {
			// Perform the super class's validation
			VALIDATE.invoke(this, id);

			// Perform our validation
			validate(id);

			final Advancement.Builder advancementBuilder = ((Advancement.Builder) ADVANCEMENT_BUILDER.get(this))
					.withParentId(new ResourceLocation("minecraft", "recipes/root"))
					.withCriterion("has_the_recipe", new RecipeUnlockedTrigger.Instance(id))
					.withRewards(AdvancementRewards.Builder.recipe(id))
					.withRequirementsStrategy(IRequirementsStrategy.OR);

			String group = (String) GROUP.get(this);
			if (group == null) {
				group = "";
			}

			@SuppressWarnings("unchecked")
			final List<String> pattern = (List<String>) PATTERN.get(this);

			@SuppressWarnings("unchecked")
			final Map<Character, Ingredient> key = (Map<Character, Ingredient>) KEY.get(this);

			String itemGroupName = itemGroup;
			if (itemGroupName == null) {
				final ItemGroup itemGroup = Preconditions.checkNotNull(result.getItem().getGroup());
				itemGroupName = itemGroup.getPath();
			}

			final ResourceLocation advancementID = new ResourceLocation(id.getNamespace(), "recipes/" + itemGroupName + "/" + id.getPath());

			consumer.accept(new Result(id, result, group, pattern, key, advancementBuilder, advancementID));
		} catch (final IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("Failed to build Enhanced Shaped Recipe " + id, e);
		}
	}

	public class Result extends ShapedRecipeBuilder.Result {
		private final CompoundNBT resultNBT;

		private Result(final ResourceLocation id, final ItemStack result, final String group, final List<String> pattern, final Map<Character, Ingredient> key, final Advancement.Builder advancementBuilder, final ResourceLocation advancementID) {
			super(id, result.getItem(), result.getCount(), group, pattern, key, advancementBuilder, advancementID);
			resultNBT = result.getTag();
		}
		
		@Override
		public IRecipeSerializer<?> getSerializer() {
			return ModCrafting.SHAPED_NBT.get();
		}

		@Override
		public void serialize(final JsonObject json) {
			super.serialize(json);

			if (resultNBT != null) {
				json.getAsJsonObject("result")
						.addProperty("nbt", resultNBT.toString());
			}
		}
	}
}
