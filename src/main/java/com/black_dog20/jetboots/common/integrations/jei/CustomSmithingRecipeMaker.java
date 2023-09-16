package com.black_dog20.jetboots.common.integrations.jei;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.items.UpgradeItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class CustomSmithingRecipeMaker {

    public static List<SmithingRecipe> getRecipes() {
        List<SmithingRecipe> recipes = new ArrayList<>();
        ItemStack helmet = new ItemStack(ModItems.GUARDIAN_HELMET.get());
        ItemStack jacket = new ItemStack(ModItems.GUARDIAN_JACKET.get());
        ItemStack pants = new ItemStack(ModItems.GUARDIAN_PANTS.get());
        ItemStack jetboots = new ItemStack(ModItems.JET_BOOTS.get());
        ItemStack rocketboots = new ItemStack(ModItems.ROCKET_BOOTS.get());
        ItemStack sword = new ItemStack(ModItems.GUARDIAN_SWORD.get());

        ForgeRegistries.ITEMS.getValues().stream()
                .filter(UpgradeItem.class::isInstance)
                .map(UpgradeItem.class::cast)
                .filter(u -> u.getTypes().contains(UpgradeItem.Type.HELMET))
                .map(upgradeItem -> create(helmet, upgradeItem))
                .forEach(recipes::add);

        ForgeRegistries.ITEMS.getValues().stream()
                .filter(UpgradeItem.class::isInstance)
                .map(UpgradeItem.class::cast)
                .filter(u -> u.getTypes().contains(UpgradeItem.Type.JACKET))
                .map(upgradeItem -> create(jacket, upgradeItem))
                .forEach(recipes::add);

        ForgeRegistries.ITEMS.getValues().stream()
                .filter(UpgradeItem.class::isInstance)
                .map(UpgradeItem.class::cast)
                .filter(u -> u.getTypes().contains(UpgradeItem.Type.PANTS))
                .map(upgradeItem -> create(pants, upgradeItem))
                .forEach(recipes::add);

        ForgeRegistries.ITEMS.getValues().stream()
                .filter(UpgradeItem.class::isInstance)
                .map(UpgradeItem.class::cast)
                .filter(u -> u.getTypes().contains(UpgradeItem.Type.BOOTS))
                .map(upgradeItem -> create(jetboots, upgradeItem))
                .forEach(recipes::add);

        ForgeRegistries.ITEMS.getValues().stream()
                .filter(UpgradeItem.class::isInstance)
                .map(UpgradeItem.class::cast)
                .filter(u -> u.getTypes().contains(UpgradeItem.Type.ROCKET_BOOTS))
                .map(upgradeItem -> create(rocketboots, upgradeItem))
                .forEach(recipes::add);

        ForgeRegistries.ITEMS.getValues().stream()
                .filter(UpgradeItem.class::isInstance)
                .map(UpgradeItem.class::cast)
                .filter(u -> u.getTypes().contains(UpgradeItem.Type.SWORD))
                .map(upgradeItem -> create(sword, upgradeItem))
                .forEach(recipes::add);

        return recipes;
    }

    private static SmithingTransformRecipe create(ItemStack stack, UpgradeItem addition) {
        ResourceLocation id = new ResourceLocation(Jetboots.MOD_ID, "jei.jetboots_upgrade." + addition.getDescriptionId());

        return new SmithingTransformRecipe(id, Ingredient.of(ModItems.JET_BOOTS_TEMPLATE.get()), Ingredient.of(stack), Ingredient.of(addition), getOutput(stack, addition));
    }

    private static ItemStack getOutput(ItemStack stack, UpgradeItem addition) {
        return addition.applyUpgrade(stack);
    }
}
