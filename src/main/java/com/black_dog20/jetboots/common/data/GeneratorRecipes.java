package com.black_dog20.jetboots.common.data;

import com.black_dog20.bml.datagen.BaseRecipeProvider;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.compat.refinedstorage.RefinedStorageCompat;
import com.black_dog20.jetboots.common.recipe.ModRecipeSerializers;
import com.refinedmods.refinedstorage.RSItems;
import com.refinedmods.refinedstorage.item.UpgradeItem;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.SmithingRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.black_dog20.jetboots.common.items.ModItems.*;

public class GeneratorRecipes extends BaseRecipeProvider {

    public GeneratorRecipes(DataGenerator generator) {
        super(generator, Jetboots.MOD_ID);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromTag(Tags.Items.INGOTS_GOLD), Ingredient.fromTag(Tags.Items.OBSIDIAN), OBSIDIAN_INFUSED_GOLD.get())
                .addCriterion("has_obsidian", hasItem(Tags.Items.OBSIDIAN))
                .build(consumer, getRecipeId(OBSIDIAN_INFUSED_GOLD.get()));

        ShapedRecipeBuilder.shapedRecipe(ARMOR_CORE.get())
                .key('o', OBSIDIAN_INFUSED_GOLD.get())
                .key('d', Tags.Items.GEMS_DIAMOND)
                .patternLine("ooo")
                .patternLine("odo")
                .patternLine("ooo")
                .addCriterion("has_obsidian_infused_gold", hasItem(OBSIDIAN_INFUSED_GOLD.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(GUARDIAN_HELMET.get())
                .key('o', OBSIDIAN_INFUSED_GOLD.get())
                .key('i', Tags.Items.INGOTS_IRON)
                .key('c', ARMOR_CORE.get())
                .key('d', Tags.Items.GEMS_DIAMOND)
                .patternLine("ioi")
                .patternLine("dcd")
                .patternLine("ioi")
                .addCriterion("has_obsidian_infused_gold", hasItem(OBSIDIAN_INFUSED_GOLD.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(GUARDIAN_JACKET.get())
                .key('o', OBSIDIAN_INFUSED_GOLD.get())
                .key('r', Tags.Items.DYES_RED)
                .key('c', ARMOR_CORE.get())
                .patternLine("r r")
                .patternLine("oco")
                .patternLine("ror")
                .addCriterion("has_obsidian_infused_gold", hasItem(OBSIDIAN_INFUSED_GOLD.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(GUARDIAN_PANTS.get())
                .key('o', OBSIDIAN_INFUSED_GOLD.get())
                .key('b', Tags.Items.DYES_BLUE)
                .key('c', ARMOR_CORE.get())
                .patternLine("bob")
                .patternLine("oco")
                .patternLine("b b")
                .addCriterion("has_obsidian_infused_gold", hasItem(OBSIDIAN_INFUSED_GOLD.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(JET_BOOTS.get())
                .key('o', Tags.Items.OBSIDIAN)
                .key('g', OBSIDIAN_INFUSED_GOLD.get())
                .key('e', Tags.Items.GEMS_EMERALD)
                .key('i', ARMOR_CORE.get())
                .key('b', Tags.Items.RODS_BLAZE)
                .patternLine("geg")
                .patternLine("oio")
                .patternLine("bgb")
                .addCriterion("has_obsidian_infused_gold", hasItem(OBSIDIAN_INFUSED_GOLD.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(GUARDIAN_SWORD.get())
                .key('o', Tags.Items.INGOTS_NETHERITE)
                .key('g', OBSIDIAN_INFUSED_GOLD.get())
                .key('e', Tags.Items.GEMS_EMERALD)
                .key('i', Items.IRON_SWORD)
                .key('b', Tags.Items.GEMS_DIAMOND)
                .patternLine("geg")
                .patternLine("oio")
                .patternLine("bgb")
                .addCriterion("has_obsidian_infused_gold", hasItem(OBSIDIAN_INFUSED_GOLD.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ENGINE_UPGRADE.get())
                .key('p', Items.PISTON)
                .key('j', ARMOR_CORE.get())
                .key('o', OBSIDIAN_INFUSED_GOLD.get())
                .key('d', Tags.Items.GEMS_DIAMOND)
                .patternLine("opo")
                .patternLine("djd")
                .patternLine("opo")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(THRUSTER_UPGRADE.get())
                .key('h', Items.HOPPER)
                .key('j', ARMOR_CORE.get())
                .key('o', OBSIDIAN_INFUSED_GOLD.get())
                .key('g', Tags.Items.INGOTS_GOLD)
                .key('d', Tags.Items.GEMS_DIAMOND)
                .patternLine("ogo")
                .patternLine("djd")
                .patternLine("hhh")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(SHOCK_ABSORBER_UPGRADE.get())
                .key('w', ItemTags.WOOL)
                .key('j', ARMOR_CORE.get())
                .key('o', OBSIDIAN_INFUSED_GOLD.get())
                .key('d', Tags.Items.GEMS_DIAMOND)
                .patternLine("odo")
                .patternLine("wjw")
                .patternLine("www")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(UNDERWATER_UPGRADE.get())
                .key('l', Tags.Items.GEMS_LAPIS)
                .key('j', ARMOR_CORE.get())
                .key('i', Items.IRON_BARS)
                .key('d', Tags.Items.GEMS_DIAMOND)
                .patternLine("djd")
                .patternLine("iii")
                .patternLine("lll")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MUFFLED_UPGRADE.get())
                .key('i', ItemTags.WOOL)
                .key('j', ARMOR_CORE.get())
                .patternLine("iii")
                .patternLine("iji")
                .patternLine("iii")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        specialRecipe(ModRecipeSerializers.CUSTOM_SMITHING.get())
                .build(consumer, ID("custom_smithing"));

        if(ModList.get().isLoaded(RefinedStorageCompat.MOD_ID)) {
            IItemProvider[] transmitters = Stream.of(RSItems.WIRELESS_TRANSMITTER.values())
                    .flatMap(Collection::stream)
                    .map(RegistryObject::get)
                    .toArray(IItemProvider[]::new);

            ResourceLocation wirelessCraftingId = getRecipeId(RefinedStorageCompat.WIRELESS_CRAFTING_UPGRADE.get());
            ShapedRecipeBuilder wirelessCraftingUpgradeBuilder = ShapedRecipeBuilder.shapedRecipe(RefinedStorageCompat.WIRELESS_CRAFTING_UPGRADE.get())
                    .key('d', Tags.Items.GEMS_DIAMOND)
                    .key('e', Tags.Items.GEMS_EMERALD)
                    .key('g', OBSIDIAN_INFUSED_GOLD.get())
                    .key('c', ARMOR_CORE.get())
                    .key('t', Ingredient.fromItems(transmitters))
                    .patternLine("dtd")
                    .patternLine("gcg")
                    .patternLine("ded")
                    .addCriterion("has_guardianHelmet", hasItem(GUARDIAN_HELMET.get()));
            Advancement.Builder wirelessCraftingAdvancement = Advancement.Builder.builder()
                    .withParentId(new ResourceLocation("minecraft", "recipes/root"))
                    .withRewards(AdvancementRewards.Builder.recipe(wirelessCraftingId))
                    .withRequirementsStrategy(IRequirementsStrategy.OR)
                    .withCriterion("has_guardianHelmet", hasItem(GUARDIAN_HELMET.get()))
                    .withCriterion("has_the_recipe", RecipeUnlockedTrigger.create(wirelessCraftingId));
            registerCompatRecipe(RefinedStorageCompat.MOD_ID, wirelessCraftingId, wirelessCraftingUpgradeBuilder, wirelessCraftingAdvancement, consumer);

            ResourceLocation wirelessRangeID = getRecipeId(RefinedStorageCompat.WIRELESS_RANGE_UPGRADE.get());
            ShapedRecipeBuilder wirelessRangeUpgradeBuilder = ShapedRecipeBuilder.shapedRecipe(RefinedStorageCompat.WIRELESS_RANGE_UPGRADE.get())
                    .key('d', Tags.Items.GEMS_DIAMOND)
                    .key('e', Tags.Items.GEMS_EMERALD)
                    .key('g', OBSIDIAN_INFUSED_GOLD.get())
                    .key('c', ARMOR_CORE.get())
                    .key('u', RSItems.UPGRADE_ITEMS.get(UpgradeItem.Type.RANGE).get())
                    .patternLine("dud")
                    .patternLine("gcg")
                    .patternLine("ded")
                    .addCriterion("has_guardianHelmet", hasItem(GUARDIAN_HELMET.get()));

            Advancement.Builder wirelessRangeAdvancement = Advancement.Builder.builder()
                    .withParentId(new ResourceLocation("minecraft", "recipes/root"))
                    .withRewards(AdvancementRewards.Builder.recipe(wirelessRangeID))
                    .withRequirementsStrategy(IRequirementsStrategy.OR)
                    .withCriterion("has_guardianHelmet", hasItem(GUARDIAN_HELMET.get()))
                    .withCriterion("has_the_recipe", RecipeUnlockedTrigger.create(wirelessRangeID));
            registerCompatRecipe(RefinedStorageCompat.MOD_ID, wirelessRangeID, wirelessRangeUpgradeBuilder, wirelessRangeAdvancement, consumer);
        }
    }

    private void registerCompatRecipe(String modId, ResourceLocation recipeId, ShapedRecipeBuilder builder, Advancement.Builder advancement, Consumer<IFinishedRecipe> consumer) {
        ConditionalRecipe.builder()
                .addCondition(new ModLoadedCondition(modId))
                .addRecipe(builder::build)
                .setAdvancement(location("craft_" + recipeId.getPath()),
                        ConditionalAdvancement.builder()
                                .addCondition(new ModLoadedCondition(modId))
                                .addAdvancement(advancement)
                )
                .build(consumer, recipeId);
    }

    private ResourceLocation location(String key) {
        return new ResourceLocation(Jetboots.MOD_ID, key);
    }

    private ResourceLocation getRecipeId(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }

    @Override
    public String getName() {
        return "Jet Boots: Recipes";
    }
}
