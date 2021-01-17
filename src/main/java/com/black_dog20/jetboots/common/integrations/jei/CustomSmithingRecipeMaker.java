package com.black_dog20.jetboots.common.integrations.jei;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.items.UpgradeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.util.ResourceLocation;
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
        ItemStack sword = new ItemStack(ModItems.GUARDIAN_SWORD.get());

        ForgeRegistries.ITEMS.getValues().stream()
                .filter(UpgradeItem.class::isInstance)
                .map(UpgradeItem.class::cast)
                .filter(u -> u.getType().equals(UpgradeItem.Type.HELMET))
                .map(upgradeItem -> create(helmet, upgradeItem))
                .forEach(recipes::add);

        ForgeRegistries.ITEMS.getValues().stream()
                .filter(UpgradeItem.class::isInstance)
                .map(UpgradeItem.class::cast)
                .filter(u -> u.getType().equals(UpgradeItem.Type.JACKET))
                .map(upgradeItem -> create(jacket, upgradeItem))
                .forEach(recipes::add);

        ForgeRegistries.ITEMS.getValues().stream()
                .filter(UpgradeItem.class::isInstance)
                .map(UpgradeItem.class::cast)
                .filter(u -> u.getType().equals(UpgradeItem.Type.PANTS))
                .map(upgradeItem -> create(pants, upgradeItem))
                .forEach(recipes::add);

        ForgeRegistries.ITEMS.getValues().stream()
                .filter(UpgradeItem.class::isInstance)
                .map(UpgradeItem.class::cast)
                .filter(u -> u.getType().equals(UpgradeItem.Type.BOOTS))
                .map(upgradeItem -> create(jetboots, upgradeItem))
                .forEach(recipes::add);

        ForgeRegistries.ITEMS.getValues().stream()
                .filter(UpgradeItem.class::isInstance)
                .map(UpgradeItem.class::cast)
                .filter(u -> u.getType().equals(UpgradeItem.Type.SWORD))
                .map(upgradeItem -> create(sword, upgradeItem))
                .forEach(recipes::add);

        return recipes;
    }

    private static SmithingRecipe create(ItemStack stack, UpgradeItem addition) {
        ResourceLocation id = new ResourceLocation(Jetboots.MOD_ID, "jei.jetboots_upgrade." + addition.getTranslationKey());

        return new SmithingRecipe(id, Ingredient.fromStacks(stack), Ingredient.fromItems(addition), getOutput(stack, addition));
    }

    private static ItemStack getOutput(ItemStack stack, UpgradeItem addition) {
        return addition.applyUpgrade(stack);
    }
}
