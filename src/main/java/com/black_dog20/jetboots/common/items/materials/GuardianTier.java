package com.black_dog20.jetboots.common.items.materials;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public class GuardianTier implements IItemTier {

    private static final GuardianTier instance = new GuardianTier();

    @Override
    public int getMaxUses() {
        return -1;
    }

    @Override
    public float getEfficiency() {
        return 9.0F;
    }

    @Override
    public float getAttackDamage() {
        return 0;
    }

    @Override
    public int getHarvestLevel() {
        return 4;
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return null;
    }

    public static GuardianTier getInstance() {
        return instance;
    }
}
