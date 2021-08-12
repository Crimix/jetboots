package com.black_dog20.jetboots.common.items.materials;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class GuardianTier implements Tier {

    private static final GuardianTier instance = new GuardianTier();

    @Override
    public int getUses() {
        return -1;
    }

    @Override
    public float getSpeed() {
        return 9.0F;
    }

    @Override
    public float getAttackDamageBonus() {
        return 0;
    }

    @Override
    public int getLevel() {
        return 4;
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }

    public static GuardianTier getInstance() {
        return instance;
    }
}
