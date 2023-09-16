package com.black_dog20.jetboots.common.items.materials;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;

public class GuardianMaterial implements ArmorMaterial {

    private static final GuardianMaterial instance = new GuardianMaterial();

    @Override
    public int getDurabilityForType(@Nonnull ArmorItem.Type pType) {
        return -1;
    }

    @Override
    public int getDefenseForType(@Nonnull ArmorItem.Type pType) {
        return 0;
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_NETHERITE;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }

    @Override
    public String getName() {
        return "jetboots-guardian";
    }

    @Override
    public float getToughness() {
        return 0;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }

    public static GuardianMaterial getInstance() {
        return instance;
    }
}
