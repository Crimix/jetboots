package com.black_dog20.jetboots.common.data;

import com.black_dog20.bml.datagen.BaseRecipeProvider;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.compat.refinedstorage.RefinedStorageCompat;
import com.black_dog20.jetboots.common.recipe.ModRecipeSerializers;
import com.refinedmods.refinedstorage.RSItems;
import com.refinedmods.refinedstorage.item.UpgradeItem;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.black_dog20.jetboots.common.items.ModItems.*;

public class GeneratorRecipes extends BaseRecipeProvider {

    public GeneratorRecipes(DataGenerator generator) {
        super(generator.getPackOutput(), Jetboots.MOD_ID);
    }

    public static SmithingTransformRecipeBuilder customSmithing(Ingredient pTemplate, Ingredient pBase, Ingredient pAddition, RecipeCategory pCategory, Item pResult) {
        return new SmithingTransformRecipeBuilder(ModRecipeSerializers.CUSTOM_SMITHING.get(), pTemplate, pBase, pAddition, pCategory, pResult);
    }
    
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(JET_BOOTS_TEMPLATE.get()), Ingredient.of(Tags.Items.INGOTS_GOLD), Ingredient.of(Tags.Items.OBSIDIAN), RecipeCategory.MISC, OBSIDIAN_INFUSED_GOLD.get())
                .unlocks("has_obsidian", has(Tags.Items.OBSIDIAN))
                .save(consumer, smithingLocation(JET_BOOTS_TEMPLATE));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, JET_BOOTS_TEMPLATE.get())
                .define('o', Tags.Items.OBSIDIAN)
                .define('d', Tags.Items.GEMS_DIAMOND)
                .pattern("ooo")
                .pattern("odo")
                .pattern("ooo")
                .unlockedBy("has_obsidian", has(Tags.Items.OBSIDIAN))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, JET_BOOTS_TEMPLATE.get(), 4)
                .requires(JET_BOOTS_TEMPLATE.get())
                .requires(OBSIDIAN_INFUSED_GOLD.get())
                .unlockedBy("has_template", has(JET_BOOTS_TEMPLATE.get()))
                .save(consumer, customRecipeId(JET_BOOTS_TEMPLATE,  "shapeless"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ARMOR_CORE.get())
                .define('o', OBSIDIAN_INFUSED_GOLD.get())
                .define('d', Tags.Items.GEMS_DIAMOND)
                .pattern("ooo")
                .pattern("odo")
                .pattern("ooo")
                .unlockedBy("has_obsidian_infused_gold", has(OBSIDIAN_INFUSED_GOLD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, GUARDIAN_HELMET.get())
                .define('o', OBSIDIAN_INFUSED_GOLD.get())
                .define('i', Tags.Items.INGOTS_IRON)
                .define('c', ARMOR_CORE.get())
                .define('d', Tags.Items.GEMS_DIAMOND)
                .pattern("ioi")
                .pattern("dcd")
                .pattern("ioi")
                .unlockedBy("has_obsidian_infused_gold", has(OBSIDIAN_INFUSED_GOLD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, GUARDIAN_JACKET.get())
                .define('o', OBSIDIAN_INFUSED_GOLD.get())
                .define('r', Tags.Items.DYES_RED)
                .define('c', ARMOR_CORE.get())
                .pattern("r r")
                .pattern("oco")
                .pattern("ror")
                .unlockedBy("has_obsidian_infused_gold", has(OBSIDIAN_INFUSED_GOLD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, GUARDIAN_PANTS.get())
                .define('o', OBSIDIAN_INFUSED_GOLD.get())
                .define('b', Tags.Items.DYES_BLUE)
                .define('c', ARMOR_CORE.get())
                .pattern("bob")
                .pattern("oco")
                .pattern("b b")
                .unlockedBy("has_obsidian_infused_gold", has(OBSIDIAN_INFUSED_GOLD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ROCKET_BOOTS.get())
                .define('i', Tags.Items.INGOTS_IRON)
                .define('g', OBSIDIAN_INFUSED_GOLD.get())
                .define('e', Tags.Items.GEMS_DIAMOND)
                .define('c', ARMOR_CORE.get())
                .define('b', ItemTags.COALS)
                .pattern("geg")
                .pattern("ici")
                .pattern("bgb")
                .unlockedBy("has_obsidian_infused_gold", has(OBSIDIAN_INFUSED_GOLD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, JET_BOOTS.get())
                .define('o', Tags.Items.OBSIDIAN)
                .define('g', OBSIDIAN_INFUSED_GOLD.get())
                .define('e', Tags.Items.GEMS_EMERALD)
                .define('c', ROCKET_BOOTS.get())
                .define('b', Tags.Items.RODS_BLAZE)
                .define('n', Tags.Items.INGOTS_NETHERITE)
                .pattern("geg")
                .pattern("oco")
                .pattern("bnb")
                .unlockedBy("has_obsidian_infused_gold", has(OBSIDIAN_INFUSED_GOLD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, GUARDIAN_SWORD.get())
                .define('o', Tags.Items.INGOTS_NETHERITE)
                .define('g', OBSIDIAN_INFUSED_GOLD.get())
                .define('e', Tags.Items.GEMS_EMERALD)
                .define('i', Items.IRON_SWORD)
                .define('b', Tags.Items.GEMS_DIAMOND)
                .pattern("geg")
                .pattern("oio")
                .pattern("bgb")
                .unlockedBy("has_obsidian_infused_gold", has(OBSIDIAN_INFUSED_GOLD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ENGINE_UPGRADE.get())
                .define('p', Items.PISTON)
                .define('j', ARMOR_CORE.get())
                .define('o', OBSIDIAN_INFUSED_GOLD.get())
                .define('d', Tags.Items.GEMS_DIAMOND)
                .pattern("opo")
                .pattern("djd")
                .pattern("opo")
                .unlockedBy("has_jetboots", has(JET_BOOTS.get()))
                .save(consumer);

        customSmithing(Ingredient.of(JET_BOOTS_TEMPLATE.get()), Ingredient.of(JET_BOOTS.get()), Ingredient.of(ENGINE_UPGRADE.get()), RecipeCategory.MISC, JET_BOOTS.get())
                .unlocks("has_jetboots", has(JET_BOOTS.get()))
                .save(consumer, smithingLocation(ENGINE_UPGRADE));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, THRUSTER_UPGRADE.get())
                .define('h', Items.HOPPER)
                .define('j', ARMOR_CORE.get())
                .define('o', OBSIDIAN_INFUSED_GOLD.get())
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('d', Tags.Items.GEMS_DIAMOND)
                .pattern("ogo")
                .pattern("djd")
                .pattern("hhh")
                .unlockedBy("has_jetboots", has(JET_BOOTS.get()))
                .save(consumer);

        customSmithing(Ingredient.of(JET_BOOTS_TEMPLATE.get()), Ingredient.of(JET_BOOTS.get()), Ingredient.of(THRUSTER_UPGRADE.get()), RecipeCategory.MISC, JET_BOOTS.get())
                .unlocks("has_jetboots", has(JET_BOOTS.get()))
                .save(consumer, smithingLocation(THRUSTER_UPGRADE));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SHOCK_ABSORBER_UPGRADE.get())
                .define('w', ItemTags.WOOL)
                .define('j', ARMOR_CORE.get())
                .define('o', OBSIDIAN_INFUSED_GOLD.get())
                .define('d', Tags.Items.GEMS_DIAMOND)
                .pattern("odo")
                .pattern("wjw")
                .pattern("www")
                .unlockedBy("has_rocketboots", has(ROCKET_BOOTS.get()))
                .unlockedBy("has_jetboots", has(JET_BOOTS.get()))
                .save(consumer);

        customSmithing(Ingredient.of(JET_BOOTS_TEMPLATE.get()), Ingredient.of(JET_BOOTS.get()), Ingredient.of(SHOCK_ABSORBER_UPGRADE.get()), RecipeCategory.MISC, JET_BOOTS.get())
                .unlocks("has_jetboots", has(JET_BOOTS.get()))
                .save(consumer, smithingLocation(SHOCK_ABSORBER_UPGRADE));

        customSmithing(Ingredient.of(JET_BOOTS_TEMPLATE.get()), Ingredient.of(ROCKET_BOOTS.get()), Ingredient.of(SHOCK_ABSORBER_UPGRADE.get()), RecipeCategory.MISC, ROCKET_BOOTS.get())
                .unlocks("has_rocketboots", has(ROCKET_BOOTS.get()))
                .save(consumer, smithingLocation(SHOCK_ABSORBER_UPGRADE, "rocket"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, UNDERWATER_UPGRADE.get())
                .define('l', Tags.Items.GEMS_LAPIS)
                .define('j', ARMOR_CORE.get())
                .define('i', Items.IRON_BARS)
                .define('d', Tags.Items.GEMS_DIAMOND)
                .pattern("djd")
                .pattern("iii")
                .pattern("lll")
                .unlockedBy("has_jetboots", has(JET_BOOTS.get()))
                .save(consumer);

        customSmithing(Ingredient.of(JET_BOOTS_TEMPLATE.get()), Ingredient.of(JET_BOOTS.get()), Ingredient.of(UNDERWATER_UPGRADE.get()), RecipeCategory.MISC, JET_BOOTS.get())
                .unlocks("has_jetboots", has(JET_BOOTS.get()))
                .save(consumer, smithingLocation(UNDERWATER_UPGRADE));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MUFFLED_UPGRADE.get())
                .define('i', ItemTags.WOOL)
                .define('j', ARMOR_CORE.get())
                .pattern("iii")
                .pattern("iji")
                .pattern("iii")
                .unlockedBy("has_jetboots", has(JET_BOOTS.get()))
                .save(consumer);

        customSmithing(Ingredient.of(JET_BOOTS_TEMPLATE.get()), Ingredient.of(JET_BOOTS.get()), Ingredient.of(MUFFLED_UPGRADE.get()), RecipeCategory.MISC, JET_BOOTS.get())
                .unlocks("has_jetboots", has(JET_BOOTS.get()))
                .save(consumer, smithingLocation(MUFFLED_UPGRADE));

        if (ModList.get().isLoaded(RefinedStorageCompat.MOD_ID)) {
            ItemLike[] transmitters = Stream.of(RSItems.WIRELESS_TRANSMITTER.values())
                    .flatMap(Collection::stream)
                    .map(RegistryObject::get)
                    .toArray(ItemLike[]::new);

            ResourceLocation wirelessCraftingId = getRecipeId(RefinedStorageCompat.WIRELESS_CRAFTING_UPGRADE.get());
            ShapedRecipeBuilder wirelessCraftingUpgradeBuilder = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RefinedStorageCompat.WIRELESS_CRAFTING_UPGRADE.get())
                    .define('d', Tags.Items.GEMS_DIAMOND)
                    .define('e', Tags.Items.GEMS_EMERALD)
                    .define('g', OBSIDIAN_INFUSED_GOLD.get())
                    .define('c', ARMOR_CORE.get())
                    .define('t', Ingredient.of(transmitters))
                    .pattern("dtd")
                    .pattern("gcg")
                    .pattern("ded")
                    .unlockedBy("has_guardianHelmet", has(GUARDIAN_HELMET.get()));
            Advancement.Builder wirelessCraftingAdvancement = Advancement.Builder.advancement()
                    .parent(new ResourceLocation("minecraft", "recipes/root"))
                    .rewards(AdvancementRewards.Builder.recipe(wirelessCraftingId))
                    .requirements(RequirementsStrategy.OR)
                    .addCriterion("has_guardianHelmet", has(GUARDIAN_HELMET.get()))
                    .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(wirelessCraftingId));
            registerCompatRecipe(RefinedStorageCompat.MOD_ID, wirelessCraftingId, wirelessCraftingUpgradeBuilder, wirelessCraftingAdvancement, consumer);

            customSmithing(Ingredient.of(JET_BOOTS_TEMPLATE.get()), Ingredient.of(GUARDIAN_HELMET.get()), Ingredient.of(RefinedStorageCompat.WIRELESS_CRAFTING_UPGRADE.get()), RecipeCategory.MISC, GUARDIAN_HELMET.get())
                    .unlocks("has_guardianHelmet", has(GUARDIAN_HELMET.get()))
                    .save(consumer, smithingLocation(RefinedStorageCompat.WIRELESS_CRAFTING_UPGRADE));

            ResourceLocation wirelessRangeID = getRecipeId(RefinedStorageCompat.WIRELESS_RANGE_UPGRADE.get());
            ShapedRecipeBuilder wirelessRangeUpgradeBuilder = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RefinedStorageCompat.WIRELESS_RANGE_UPGRADE.get())
                    .define('d', Tags.Items.GEMS_DIAMOND)
                    .define('e', Tags.Items.GEMS_EMERALD)
                    .define('g', OBSIDIAN_INFUSED_GOLD.get())
                    .define('c', ARMOR_CORE.get())
                    .define('u', RSItems.UPGRADE_ITEMS.get(UpgradeItem.Type.RANGE).get())
                    .pattern("dud")
                    .pattern("gcg")
                    .pattern("ded")
                    .unlockedBy("has_guardianHelmet", has(GUARDIAN_HELMET.get()));

            Advancement.Builder wirelessRangeAdvancement = Advancement.Builder.advancement()
                    .parent(new ResourceLocation("minecraft", "recipes/root"))
                    .rewards(AdvancementRewards.Builder.recipe(wirelessRangeID))
                    .requirements(RequirementsStrategy.OR)
                    .addCriterion("has_guardianHelmet", has(GUARDIAN_HELMET.get()))
                    .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(wirelessRangeID));
            registerCompatRecipe(RefinedStorageCompat.MOD_ID, wirelessRangeID, wirelessRangeUpgradeBuilder, wirelessRangeAdvancement, consumer);

            customSmithing(Ingredient.of(JET_BOOTS_TEMPLATE.get()), Ingredient.of(GUARDIAN_HELMET.get()), Ingredient.of(RefinedStorageCompat.WIRELESS_RANGE_UPGRADE.get()), RecipeCategory.MISC, GUARDIAN_HELMET.get())
                    .unlocks("has_guardianHelmet", has(GUARDIAN_HELMET.get()))
                    .save(consumer, smithingLocation(RefinedStorageCompat.WIRELESS_RANGE_UPGRADE));
        }
    }

    private void registerCompatRecipe(String modId, ResourceLocation recipeId, ShapedRecipeBuilder builder, Advancement.Builder advancement, Consumer<FinishedRecipe> consumer) {
        ConditionalRecipe.builder()
                .addCondition(new ModLoadedCondition(modId))
                .addRecipe(builder::save)
                .setAdvancement(RL("craft_" + recipeId.getPath()),
                        ConditionalAdvancement.builder()
                                .addCondition(new ModLoadedCondition(modId))
                                .addAdvancement(advancement)
                )
                .build(consumer, recipeId);
    }

    private ResourceLocation getRecipeId(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }

    private ResourceLocation smithingLocation(RegistryObject<Item> registryObject){
        return customRecipeId(registryObject,  "smithing");
    }

    private ResourceLocation smithingLocation(RegistryObject<Item> registryObject, String prefix){
        return customRecipeId(registryObject, prefix, "smithing");
    }

    private ResourceLocation customRecipeId(RegistryObject<Item> registryObject, String postfix){
        return customRecipeId(registryObject, null, postfix);
    }

    private ResourceLocation customRecipeId(RegistryObject<Item> registryObject, String prefix, String postfix){
        if (prefix != null && !prefix.isEmpty()) {
            return RL(String.format("%s_%s_%s", registryObject.getId().getPath(), prefix, postfix));
        } else {
            return RL(String.format("%s_%s", registryObject.getId().getPath(), postfix));
        }
    }

}
