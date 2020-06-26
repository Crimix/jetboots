package com.black_dog20.jetboots.common.data;

import com.black_dog20.bml.datagen.BaseRecipeProvider;
import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.compat.Cyclic;
import com.black_dog20.jetboots.common.compat.MekanismTools;
import mekanism.tools.common.registries.ToolsItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.fml.ModList;

import java.util.function.Consumer;

import static com.black_dog20.jetboots.common.items.ModItems.*;

public class GeneratorRecipes extends BaseRecipeProvider {

    public GeneratorRecipes(DataGenerator generator) {
        super(generator, Jetboots.MOD_ID);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(JET_BOOTS.get())
                .key('o', Tags.Items.OBSIDIAN)
                .key('e', Tags.Items.GEMS_EMERALD)
                .key('i', Items.IRON_BOOTS)
                .key('b', Tags.Items.RODS_BLAZE)
                .key('g', Tags.Items.INGOTS_GOLD)
                .patternLine("oeo")
                .patternLine("oio")
                .patternLine("bgb")
                .addCriterion("has_diamonds", hasItem(Tags.Items.GEMS_DIAMOND))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(BASE_UPGRADE.get())
                .key('o', Tags.Items.OBSIDIAN)
                .key('d', Tags.Items.GEMS_DIAMOND)
                .key('b', Tags.Items.RODS_BLAZE)
                .key('g', Tags.Items.INGOTS_GOLD)
                .patternLine("ogo")
                .patternLine("gdg")
                .patternLine("bob")
                .addCriterion("has_diamonds", hasItem(Tags.Items.GEMS_DIAMOND))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(LEATHER_ARMOR_UPGRADE.get())
                .key('i', Tags.Items.LEATHER)
                .key('j', BASE_UPGRADE.get())
                .key('d', Tags.Items.GEMS_DIAMOND)
                .patternLine("idi")
                .patternLine("iji")
                .patternLine("iii")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(IRON_ARMOR_UPGRADE.get())
                .key('i', Tags.Items.INGOTS_IRON)
                .key('j', LEATHER_ARMOR_UPGRADE.get())
                .key('d', Tags.Items.GEMS_DIAMOND)
                .patternLine("idi")
                .patternLine("iji")
                .patternLine("iii")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(DIAMOND_ARMOR_UPGRADE.get())
                .key('i', Tags.Items.GEMS_DIAMOND)
                .key('j', IRON_ARMOR_UPGRADE.get())
                .key('d', Tags.Items.GEMS_EMERALD)
                .patternLine("idi")
                .patternLine("iji")
                .patternLine("iii")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ENGINE_UPGRADE.get())
                .key('p', Items.PISTON)
                .key('j', BASE_UPGRADE.get())
                .key('o', Tags.Items.OBSIDIAN)
                .key('d', Tags.Items.GEMS_DIAMOND)
                .patternLine("opo")
                .patternLine("djd")
                .patternLine("opo")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(THRUSTER_UPGRADE.get())
                .key('h', Items.HOPPER)
                .key('j', BASE_UPGRADE.get())
                .key('o', Tags.Items.OBSIDIAN)
                .key('g', Tags.Items.INGOTS_GOLD)
                .key('d', Tags.Items.GEMS_DIAMOND)
                .patternLine("ogo")
                .patternLine("djd")
                .patternLine("hhh")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .addCriterion("has_engine_upgrade", hasItem(ENGINE_UPGRADE.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(SHOCK_ABSORBER_UPGRADE.get())
                .key('w', ItemTags.WOOL)
                .key('j', BASE_UPGRADE.get())
                .key('o', Tags.Items.OBSIDIAN)
                .key('d', Tags.Items.GEMS_DIAMOND)
                .patternLine("odo")
                .patternLine("wjw")
                .patternLine("www")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(UNDERWATER_UPGRADE.get())
                .key('l', Tags.Items.GEMS_LAPIS)
                .key('j', BASE_UPGRADE.get())
                .key('i', Items.IRON_BARS)
                .key('d', Tags.Items.GEMS_DIAMOND)
                .patternLine("djd")
                .patternLine("iii")
                .patternLine("lll")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(SOULBOUND_UPGRADE.get())
                .key('i', Tags.Items.NETHER_STARS)
                .key('j', BASE_UPGRADE.get())
                .patternLine("iii")
                .patternLine("iji")
                .patternLine("iii")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(MUFFLED_UPGRADE.get())
                .key('i', ItemTags.WOOL)
                .key('j', BASE_UPGRADE.get())
                .patternLine("iii")
                .patternLine("iji")
                .patternLine("iii")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ADVANCED_BATTERY_UPGRADE.get())
                .key('i', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .key('j', BASE_UPGRADE.get())
                .patternLine("iii")
                .patternLine("iji")
                .patternLine("iii")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ELITE_BATTERY_UPGRADE.get())
                .key('i', Tags.Items.STORAGE_BLOCKS_LAPIS)
                .key('j', ADVANCED_BATTERY_UPGRADE.get())
                .key('d', Tags.Items.STORAGE_BLOCKS_DIAMOND)
                .patternLine("idi")
                .patternLine("iji")
                .patternLine("idi")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ULTIMATE_BATTERY_UPGRADE.get())
                .key('i', Tags.Items.STORAGE_BLOCKS_DIAMOND)
                .key('j', ELITE_BATTERY_UPGRADE.get())
                .patternLine("iii")
                .patternLine("iji")
                .patternLine("iii")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(BASIC_CONVERTER_UPGRADE.get())
                .key('i', Tags.Items.GUNPOWDER)
                .key('j', BASE_UPGRADE.get())
                .patternLine("iii")
                .patternLine("iji")
                .patternLine("iii")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ADVANCED_CONVERTER_UPGRADE.get())
                .key('i', Tags.Items.DUSTS_REDSTONE)
                .key('j', BASIC_CONVERTER_UPGRADE.get())
                .patternLine("iii")
                .patternLine("iji")
                .patternLine("iii")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ELITE_CONVERTER_UPGRADE.get())
                .key('i', Tags.Items.GEMS_LAPIS)
                .key('j', ADVANCED_CONVERTER_UPGRADE.get())
                .key('d', Tags.Items.GEMS_DIAMOND)
                .patternLine("idi")
                .patternLine("iji")
                .patternLine("idi")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ULTIMATE_CONVERTER_UPGRADE.get())
                .key('i', Tags.Items.GEMS_EMERALD)
                .key('j', ELITE_CONVERTER_UPGRADE.get())
                .patternLine("iii")
                .patternLine("iji")
                .patternLine("iii")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FORCEFIELD_ARMOR_UPGRADE.get())
                .key('g', Tags.Items.GLASS)
                .key('f', FORCEFIELD_GENERATOR.get())
                .key('p', FORCEFIELD_PROJECTOR.get())
                .key('u', BASE_UPGRADE.get())
                .key('n', Items.NETHER_STAR)
                .patternLine("ggg")
                .patternLine("fup")
                .patternLine("gng")
                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                .build(consumer);

        //Compat
        if (ModList.get().isLoaded(Cyclic.MOD_ID)) {
            registerCompatArmorUpgradeRecipe(Cyclic.MOD_ID, Cyclic.CRYSTAL_ARMOR_UPGRADE_CYCLIC.get(), Cyclic.CRYSTAL_BOOTS, consumer);
            registerCompatArmorUpgradeRecipe(Cyclic.MOD_ID, Cyclic.EMERALD_ARMOR_UPGRADE_CYCLIC.get(), Cyclic.EMERALD_BOOTS, consumer);
        }
        if (ModList.get().isLoaded(MekanismTools.MOD_ID)) {
            registerCompatArmorUpgradeRecipe(MekanismTools.MOD_ID, MekanismTools.BRONZE_ARMOR_UPGRADE_MEKANISM.get(), ToolsItems.BRONZE_BOOTS.get(), consumer);
            registerCompatArmorUpgradeRecipe(MekanismTools.MOD_ID, MekanismTools.LAPIS_ARMOR_UPGRADE_MEKANISM.get(), ToolsItems.LAPIS_LAZULI_BOOTS.get(), consumer);
            registerCompatArmorUpgradeRecipe(MekanismTools.MOD_ID, MekanismTools.OSMIUM_ARMOR_UPGRADE_MEKANISM.get(), ToolsItems.OSMIUM_BOOTS.get(), consumer);
            registerCompatArmorUpgradeRecipe(MekanismTools.MOD_ID, MekanismTools.REFINED_GLOWSTONE_ARMOR_UPGRADE_MEKANISM.get(), ToolsItems.REFINED_GLOWSTONE_BOOTS.get(), consumer);
            registerCompatArmorUpgradeRecipe(MekanismTools.MOD_ID, MekanismTools.REFINED_OBSIDIAN_ARMOR_UPGRADE_MEKANISM.get(), ToolsItems.REFINED_OBSIDIAN_BOOTS.get(), consumer);
            registerCompatArmorUpgradeRecipe(MekanismTools.MOD_ID, MekanismTools.STEEL_ARMOR_UPGRADE_MEKANISM.get(), ToolsItems.STEEL_BOOTS.get(), consumer);
        }
    }

    private ResourceLocation location(String key) {
        return new ResourceLocation(Jetboots.MOD_ID, key);
    }


    private void registerCompatArmorUpgradeRecipe(String modId, Item ouput, Item compatBoots, Consumer<IFinishedRecipe> consumer) {
        ResourceLocation recipeId = Registry.ITEM.getKey(ouput);
        ConditionalRecipe.builder()
                .addCondition(new ModLoadedCondition(modId))
                .addRecipe(
                        ShapelessRecipeBuilder.shapelessRecipe(ouput)
                                .addIngredient(compatBoots)
                                .addIngredient(BASE_UPGRADE.get())
                                .addCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                                .addCriterion("has_armor_boots", hasItem(compatBoots))
                                ::build)
                .setAdvancement(location("craft_"+recipeId.getPath()),
                        ConditionalAdvancement.builder()
                                .addCondition(new ModLoadedCondition(modId))
                                .addAdvancement(
                                        Advancement.Builder.builder()
                                                .withParentId(new ResourceLocation("minecraft", "recipes/root"))
                                                .withRewards(AdvancementRewards.Builder.recipe(recipeId))
                                                .withRequirementsStrategy(IRequirementsStrategy.OR)
                                                .withCriterion("has_jetboots", hasItem(JET_BOOTS.get()))
                                                .withCriterion("has_the_recipe", new RecipeUnlockedTrigger.Instance(recipeId))
                                )
                )
                .build(consumer, recipeId);
    }
}
