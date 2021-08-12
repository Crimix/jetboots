package com.black_dog20.jetboots.common.data;

import com.black_dog20.bml.datagen.BaseRecipeProvider;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.recipe.ModRecipeSerializers;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

import static com.black_dog20.jetboots.common.items.ModItems.*;

public class GeneratorRecipes extends BaseRecipeProvider {

    public GeneratorRecipes(DataGenerator generator) {
        super(generator, Jetboots.MOD_ID);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(Tags.Items.INGOTS_GOLD), Ingredient.of(Tags.Items.OBSIDIAN), OBSIDIAN_INFUSED_GOLD.get())
                .unlocks("has_obsidian", has(Tags.Items.OBSIDIAN))
                .save(consumer, getRecipeId(OBSIDIAN_INFUSED_GOLD.get()));

        ShapedRecipeBuilder.shaped(ARMOR_CORE.get())
                .define('o', OBSIDIAN_INFUSED_GOLD.get())
                .define('d', Tags.Items.GEMS_DIAMOND)
                .pattern("ooo")
                .pattern("odo")
                .pattern("ooo")
                .unlockedBy("has_obsidian_infused_gold", has(OBSIDIAN_INFUSED_GOLD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(GUARDIAN_HELMET.get())
                .define('o', OBSIDIAN_INFUSED_GOLD.get())
                .define('i', Tags.Items.INGOTS_IRON)
                .define('c', ARMOR_CORE.get())
                .define('d', Tags.Items.GEMS_DIAMOND)
                .pattern("ioi")
                .pattern("dcd")
                .pattern("ioi")
                .unlockedBy("has_obsidian_infused_gold", has(OBSIDIAN_INFUSED_GOLD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(GUARDIAN_JACKET.get())
                .define('o', OBSIDIAN_INFUSED_GOLD.get())
                .define('r', Tags.Items.DYES_RED)
                .define('c', ARMOR_CORE.get())
                .pattern("r r")
                .pattern("oco")
                .pattern("ror")
                .unlockedBy("has_obsidian_infused_gold", has(OBSIDIAN_INFUSED_GOLD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(GUARDIAN_PANTS.get())
                .define('o', OBSIDIAN_INFUSED_GOLD.get())
                .define('b', Tags.Items.DYES_BLUE)
                .define('c', ARMOR_CORE.get())
                .pattern("bob")
                .pattern("oco")
                .pattern("b b")
                .unlockedBy("has_obsidian_infused_gold", has(OBSIDIAN_INFUSED_GOLD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ROCKET_BOOTS.get())
                .define('i', Tags.Items.INGOTS_IRON)
                .define('g', OBSIDIAN_INFUSED_GOLD.get())
                .define('e', Tags.Items.GEMS_EMERALD)
                .define('c', ARMOR_CORE.get())
                .define('b', ItemTags.COALS)
                .pattern("geg")
                .pattern("ici")
                .pattern("bgb")
                .unlockedBy("has_obsidian_infused_gold", has(OBSIDIAN_INFUSED_GOLD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(JET_BOOTS.get())
                .define('o', Tags.Items.OBSIDIAN)
                .define('g', OBSIDIAN_INFUSED_GOLD.get())
                .define('e', Tags.Items.INGOTS_NETHERITE)
                .define('c', ROCKET_BOOTS.get())
                .define('b', Tags.Items.RODS_BLAZE)
                .pattern("geg")
                .pattern("oco")
                .pattern("bgb")
                .unlockedBy("has_obsidian_infused_gold", has(OBSIDIAN_INFUSED_GOLD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(GUARDIAN_SWORD.get())
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

        ShapedRecipeBuilder.shaped(ENGINE_UPGRADE.get())
                .define('p', Items.PISTON)
                .define('j', ARMOR_CORE.get())
                .define('o', OBSIDIAN_INFUSED_GOLD.get())
                .define('d', Tags.Items.GEMS_DIAMOND)
                .pattern("opo")
                .pattern("djd")
                .pattern("opo")
                .unlockedBy("has_jetboots", has(JET_BOOTS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(THRUSTER_UPGRADE.get())
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

        ShapedRecipeBuilder.shaped(SHOCK_ABSORBER_UPGRADE.get())
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

        ShapedRecipeBuilder.shaped(UNDERWATER_UPGRADE.get())
                .define('l', Tags.Items.GEMS_LAPIS)
                .define('j', ARMOR_CORE.get())
                .define('i', Items.IRON_BARS)
                .define('d', Tags.Items.GEMS_DIAMOND)
                .pattern("djd")
                .pattern("iii")
                .pattern("lll")
                .unlockedBy("has_jetboots", has(JET_BOOTS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(MUFFLED_UPGRADE.get())
                .define('i', ItemTags.WOOL)
                .define('j', ARMOR_CORE.get())
                .pattern("iii")
                .pattern("iji")
                .pattern("iii")
                .unlockedBy("has_jetboots", has(JET_BOOTS.get()))
                .save(consumer);

        specialRecipe(ModRecipeSerializers.CUSTOM_SMITHING.get())
                .save(consumer, ID("custom_smithing"));

//        if(ModList.get().isLoaded(RefinedStorageCompat.MOD_ID)) { //TODO When RS is updated
//            ItemLike[] transmitters = Stream.of(RSItems.WIRELESS_TRANSMITTER.values())
//                    .flatMap(Collection::stream)
//                    .map(RegistryObject::get)
//                    .toArray(ItemLike[]::new);
//
//            ResourceLocation wirelessCraftingId = getRecipeId(RefinedStorageCompat.WIRELESS_CRAFTING_UPGRADE.get());
//            ShapedRecipeBuilder wirelessCraftingUpgradeBuilder = ShapedRecipeBuilder.shaped(RefinedStorageCompat.WIRELESS_CRAFTING_UPGRADE.get())
//                    .define('d', Tags.Items.GEMS_DIAMOND)
//                    .define('e', Tags.Items.GEMS_EMERALD)
//                    .define('g', OBSIDIAN_INFUSED_GOLD.get())
//                    .define('c', ARMOR_CORE.get())
//                    .define('t', Ingredient.of(transmitters))
//                    .pattern("dtd")
//                    .pattern("gcg")
//                    .pattern("ded")
//                    .unlockedBy("has_guardianHelmet", has(GUARDIAN_HELMET.get()));
//            Advancement.Builder wirelessCraftingAdvancement = Advancement.Builder.advancement()
//                    .parent(new ResourceLocation("minecraft", "recipes/root"))
//                    .rewards(AdvancementRewards.Builder.recipe(wirelessCraftingId))
//                    .requirements(RequirementsStrategy.OR)
//                    .addCriterion("has_guardianHelmet", has(GUARDIAN_HELMET.get()))
//                    .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(wirelessCraftingId));
//            registerCompatRecipe(RefinedStorageCompat.MOD_ID, wirelessCraftingId, wirelessCraftingUpgradeBuilder, wirelessCraftingAdvancement, consumer);
//
//            ResourceLocation wirelessRangeID = getRecipeId(RefinedStorageCompat.WIRELESS_RANGE_UPGRADE.get());
//            ShapedRecipeBuilder wirelessRangeUpgradeBuilder = ShapedRecipeBuilder.shaped(RefinedStorageCompat.WIRELESS_RANGE_UPGRADE.get())
//                    .define('d', Tags.Items.GEMS_DIAMOND)
//                    .define('e', Tags.Items.GEMS_EMERALD)
//                    .define('g', OBSIDIAN_INFUSED_GOLD.get())
//                    .define('c', ARMOR_CORE.get())
//                    .define('u', RSItems.UPGRADE_ITEMS.get(UpgradeItem.Type.RANGE).get())
//                    .pattern("dud")
//                    .pattern("gcg")
//                    .pattern("ded")
//                    .unlockedBy("has_guardianHelmet", has(GUARDIAN_HELMET.get()));
//
//            Advancement.Builder wirelessRangeAdvancement = Advancement.Builder.advancement()
//                    .parent(new ResourceLocation("minecraft", "recipes/root"))
//                    .rewards(AdvancementRewards.Builder.recipe(wirelessRangeID))
//                    .requirements(RequirementsStrategy.OR)
//                    .addCriterion("has_guardianHelmet", has(GUARDIAN_HELMET.get()))
//                    .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(wirelessRangeID));
//            registerCompatRecipe(RefinedStorageCompat.MOD_ID, wirelessRangeID, wirelessRangeUpgradeBuilder, wirelessRangeAdvancement, consumer);
//        }
    }

    private void registerCompatRecipe(String modId, ResourceLocation recipeId, ShapedRecipeBuilder builder, Advancement.Builder advancement, Consumer<FinishedRecipe> consumer) {
        ConditionalRecipe.builder()
                .addCondition(new ModLoadedCondition(modId))
                .addRecipe(builder::save)
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
