package com.black_dog20.jetboots.common.recipe;

import com.black_dog20.jetboots.common.items.UpgradeItem;
import com.black_dog20.jetboots.common.items.equipment.GuardianHelmetItem;
import com.black_dog20.jetboots.common.items.equipment.GuardianJacketItem;
import com.black_dog20.jetboots.common.items.equipment.GuardianPantsItem;
import com.black_dog20.jetboots.common.items.equipment.GuardianSwordItem;
import com.black_dog20.jetboots.common.items.equipment.JetBootsItem;
import com.black_dog20.jetboots.common.items.equipment.RocketBootsItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.function.Predicate;

public class CustomSmithingRecipe extends UpgradeRecipe {

    private static final Map<UpgradeItem.Type, Predicate<ItemStack>> MATCHER = Map.of(
            UpgradeItem.Type.HELMET, createTypeMatcher(GuardianHelmetItem.class),
            UpgradeItem.Type.JACKET, createTypeMatcher(GuardianJacketItem.class),
            UpgradeItem.Type.PANTS, createTypeMatcher(GuardianPantsItem.class),
            UpgradeItem.Type.BOOTS, createTypeMatcher(JetBootsItem.class),
            UpgradeItem.Type.ROCKET_BOOTS, createTypeMatcher(RocketBootsItem.class),
            UpgradeItem.Type.SWORD, createTypeMatcher(GuardianSwordItem.class)
    );

    public CustomSmithingRecipe(ResourceLocation recipeId) {
        super(recipeId, Ingredient.EMPTY, Ingredient.EMPTY, ItemStack.EMPTY);
    }

    @Override
    public boolean matches(Container inv, Level world) {
        ItemStack input = inv.getItem(0);
        ItemStack addition = inv.getItem(1);

        if (addition.getItem() instanceof UpgradeItem upgradeItem && addition.getCount() == 1) {
            if (upgradeItem.hasBeenAppliedAlready(input))
                return false;

            for (UpgradeItem.Type type : upgradeItem.getTypes()) {
                if (MATCHER.getOrDefault(type, (i) -> false).test(input)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public ItemStack assemble(Container inv) {
        ItemStack stack = inv.getItem(0).copy();
        UpgradeItem addition = (UpgradeItem) inv.getItem(1).getItem();

        return addition.applyUpgrade(stack);
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isAdditionIngredient(ItemStack addition) {
        return addition.getItem() instanceof UpgradeItem;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CUSTOM_SMITHING.get();
    }

    private static Predicate<ItemStack> createTypeMatcher(Class<?> clazz) {
        return stack -> clazz.isInstance(stack.getItem()) && stack.getCount() == 1;
    }

}
