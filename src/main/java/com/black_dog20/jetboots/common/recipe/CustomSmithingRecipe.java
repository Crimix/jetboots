package com.black_dog20.jetboots.common.recipe;

import com.black_dog20.jetboots.common.items.UpgradeItem;
import com.black_dog20.jetboots.common.items.equipment.GuardianHelmetItem;
import com.black_dog20.jetboots.common.items.equipment.GuardianJacketItem;
import com.black_dog20.jetboots.common.items.equipment.GuardianPantsItem;
import com.black_dog20.jetboots.common.items.equipment.GuardianSwordItem;
import com.black_dog20.jetboots.common.items.equipment.JetBootsItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class CustomSmithingRecipe extends SmithingRecipe {

    public CustomSmithingRecipe(ResourceLocation recipeId) {
        super(recipeId, Ingredient.EMPTY, Ingredient.EMPTY, ItemStack.EMPTY);
    }

    @Override
    public boolean matches(IInventory inv, World world) {
        ItemStack input = inv.getStackInSlot(0);
        ItemStack addition = inv.getStackInSlot(1);

        if(addition.getItem() instanceof UpgradeItem && addition.getCount() == 1) {
            UpgradeItem upgradeItem = (UpgradeItem) addition.getItem();

            if(upgradeItem.hasBeenAppliedAlready(input))
                return false;

            switch (upgradeItem.getType()) {
                case HELMET:
                    return input.getItem() instanceof GuardianHelmetItem && input.getCount() == 1;
                case JACKET:
                    return input.getItem() instanceof GuardianJacketItem && input.getCount() == 1;
                case PANTS:
                    return input.getItem() instanceof GuardianPantsItem && input.getCount() == 1;
                case BOOTS:
                    return input.getItem() instanceof JetBootsItem && input.getCount() == 1;
                case SWORD:
                    return input.getItem() instanceof GuardianSwordItem && input.getCount() == 1;
            }
        }

        return false;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        ItemStack stack = inv.getStackInSlot(0).copy();
        UpgradeItem addition = (UpgradeItem) inv.getStackInSlot(1).getItem();

        return addition.applyUpgrade(stack);
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isValidAdditionItem(ItemStack addition) {
        return addition.getItem() instanceof UpgradeItem;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CUSTOM_SMITHING.get();
    }

}
