package com.black_dog20.jetboots.common.data;

import static com.black_dog20.jetboots.common.items.ModItems.JET_BOOTS;

import java.util.function.Consumer;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.crafting.BatteryOnCondition;
import com.black_dog20.jetboots.common.crafting.IngredientNBT;
import com.black_dog20.jetboots.common.data.crafting.ShapedNBTRecipeBuilder;
import com.black_dog20.jetboots.common.util.NBTItemBuilder;
import com.black_dog20.jetboots.common.util.NBTTags;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;

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


		ResourceLocation adv_battery_recipe = location("adv_battery_upgraded_jetboots");
		ConditionalRecipe.builder()
		.addCondition(new BatteryOnCondition())
		.addRecipe(
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
				::build)
		.setAdvancement(location("craft_adv_battery_upgraded_jetboots"),
				ConditionalAdvancement.builder()
				.addCondition(new BatteryOnCondition())
				.addAdvancement(
						Advancement.Builder.builder()
						.withParentId(new ResourceLocation("minecraft", "recipes/root"))
						.withRewards(AdvancementRewards.Builder.recipe(adv_battery_recipe))
						.withRequirementsStrategy(IRequirementsStrategy.OR)
						.withCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
						.withCriterion("has_the_recipe", new RecipeUnlockedTrigger.Instance(adv_battery_recipe))
						)
				)
		.build(consumer, adv_battery_recipe);

		ResourceLocation super_battery_recipe = location("super_battery_upgraded_jetboots");
		ConditionalRecipe.builder()
		.addCondition(new BatteryOnCondition())
		.addRecipe(
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
				::build)
		.setAdvancement(location("craft_super_battery_upgraded_jetboots"),
				ConditionalAdvancement.builder()
				.addCondition(new BatteryOnCondition())
				.addAdvancement(
						Advancement.Builder.builder()
						.withParentId(new ResourceLocation("minecraft", "recipes/root"))
						.withRewards(AdvancementRewards.Builder.recipe(super_battery_recipe))
						.withRequirementsStrategy(IRequirementsStrategy.OR)
						.withCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
						.withCriterion("has_the_recipe", new RecipeUnlockedTrigger.Instance(super_battery_recipe))
						)
				)
		.build(consumer, super_battery_recipe);
	}

	private ItemStack getJetBootWithTag(String key) {
		ItemStack boots = new ItemStack(JET_BOOTS.get());
		boots.getOrCreateTag().putBoolean(key, true);
		return boots;
	}

	private ResourceLocation location(String key) {
		return new ResourceLocation(Jetboots.MOD_ID, key);
	}

}
