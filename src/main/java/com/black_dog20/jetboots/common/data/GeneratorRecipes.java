package com.black_dog20.jetboots.common.data;

import static com.black_dog20.jetboots.common.items.ModItems.JET_BOOTS;

import java.util.function.Consumer;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.crafting.IngredientNBT;
import com.black_dog20.jetboots.common.data.crafting.ShapedNBTRecipeBuilder;
import com.black_dog20.jetboots.common.util.NBTItemBuilder;
import com.black_dog20.jetboots.common.util.NBTTags;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class GeneratorRecipes extends RecipeProvider {

	public GeneratorRecipes(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		ShapedRecipeBuilder.shapedRecipe(JET_BOOTS.get())
		.key('r', Tags.Items.DUSTS_REDSTONE)
		.key('g', Tags.Items.GLASS_PANES)
		.key('l', Tags.Items.GEMS_LAPIS)
		.key('d', Tags.Items.GEMS_DIAMOND)
		.patternLine("rlr")
		.patternLine("dgd")
		.patternLine("rlr")
		.addCriterion("has_diamonds", hasItem(Tags.Items.GEMS_DIAMOND))
		.build(consumer);


		ShapedNBTRecipeBuilder.shapedNBTRecipe(getJetBootWithTag(NBTTags.UPGRAE_ARMOR_LEATHER))
		.key('i', Tags.Items.LEATHER)
		.key('j', JET_BOOTS.get())
		.patternLine("iii")
		.patternLine("iji")
		.patternLine("iii")
		.addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
		.build(consumer, new ResourceLocation(Jetboots.MOD_ID, "leather_upgraded_jetboots"));


		ShapedNBTRecipeBuilder.shapedNBTRecipe(getJetBootWithTag(NBTTags.UPGRAE_ARMOR_IRON))
		.key('i', Tags.Items.INGOTS_IRON)
		.key('j', IngredientNBT.fromNBTStack(getJetBootWithTag(NBTTags.UPGRAE_ARMOR_LEATHER)))
		.patternLine("iii")
		.patternLine("iji")
		.patternLine("iii")
		.addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
		.build(consumer, new ResourceLocation(Jetboots.MOD_ID, "iron_upgraded_jetboots"));


		ShapedNBTRecipeBuilder.shapedNBTRecipe(
				NBTItemBuilder.init(JET_BOOTS.get())
				.addTag(NBTTags.UPGRAE_ADVANCED_BATTERY, true)
				.addTag("energy_mult", 3)
				.build())
		.key('i', Tags.Items.DUSTS_REDSTONE)
		.key('j', JET_BOOTS.get())
		.patternLine("iii")
		.patternLine("iji")
		.patternLine("iii")
		.addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
		.build(consumer, new ResourceLocation(Jetboots.MOD_ID, "adv_battery_upgraded_jetboots"));
		
		ShapedNBTRecipeBuilder.shapedNBTRecipe(
				NBTItemBuilder.init(JET_BOOTS.get())
				.addTag(NBTTags.UPGRAE_SUPER_BATTERY, true)
				.addTag("energy_mult", 6)
				.build())
		.key('i', Tags.Items.GEMS_LAPIS)
		.key('j', IngredientNBT.fromNBTStack(getJetBootWithTag(NBTTags.UPGRAE_ADVANCED_BATTERY)))
		.patternLine("iii")
		.patternLine("iji")
		.patternLine("iii")
		.addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
		.build(consumer, new ResourceLocation(Jetboots.MOD_ID, "super_battery_upgraded_jetboots"));
	}

	private ItemStack getJetBootWithTag(String key) {
		ItemStack boots = new ItemStack(JET_BOOTS.get());
		boots.getOrCreateTag().putBoolean(key, true);
		return boots;
	}



}
