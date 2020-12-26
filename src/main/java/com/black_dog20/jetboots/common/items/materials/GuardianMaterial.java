package com.black_dog20.jetboots.common.items.materials;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import javax.annotation.Nonnull;

public class GuardianMaterial implements IArmorMaterial {

    private static final GuardianMaterial instance = new GuardianMaterial();

    @Override
    public int getDurability(@Nonnull EquipmentSlotType slotIn) {
        return -1;
    }

    @Override
    public int getDamageReductionAmount(@Nonnull EquipmentSlotType slotIn) {
        return 0;
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
    }

    @Override
    public Ingredient getRepairMaterial() {
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
