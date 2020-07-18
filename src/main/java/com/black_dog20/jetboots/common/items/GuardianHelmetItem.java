package com.black_dog20.jetboots.common.items;

import com.black_dog20.bml.api.ISoulbindable;
import com.black_dog20.bml.utils.item.MultiMapHelper;
import com.black_dog20.bml.utils.keybinds.KeybindsUtil;
import com.black_dog20.bml.utils.translate.TranslationUtil;
import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.client.keybinds.Keybinds;
import com.black_dog20.jetboots.common.util.GuardinanHelmetProperties;
import com.black_dog20.jetboots.common.util.TranslationHelper;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.*;

public class GuardianHelmetItem extends BaseArmorItem implements ISoulbindable {

    private static final GuardianHelmetItem.Material MATERIAL = new GuardianHelmetItem.Material();

    public GuardianHelmetItem(Properties builder) {
        super(MATERIAL, EquipmentSlotType.HEAD, builder.defaultMaxDamage(-1));
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot);
        if (slot == this.slot) {
            MultiMapHelper.removeValues(multimap, SharedMonsterAttributes.ARMOR.getName(), ARMOR_MODIFIERS[slot.getIndex()]);
            MultiMapHelper.removeValues(multimap, SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), ARMOR_MODIFIERS[slot.getIndex()]);
            multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor modifier", getHelmetDamageReduceAmount(stack), AttributeModifier.Operation.ADDITION));
            multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor toughness", getHelmetToughness(stack), AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    private double getHelmetDamageReduceAmount(ItemStack stack) {
        return GuardinanHelmetProperties.getMode(stack) ? 6 : 0;
    }

    private double getHelmetToughness(ItemStack stack) {
        return GuardinanHelmetProperties.getMode(stack) ? 4 : 0;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "jetboots:textures/armor/guardianhelmet.png";
    }

    @Override
    public boolean isSoulbound(ItemStack stack) {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        tooltip.add(TranslationUtil.translate(CHANGE_HELMET_MODE, TextFormatting.GRAY, KeybindsUtil.getKeyBindText(Keybinds.keyHelmetMode)));
        tooltip.add(TranslationUtil.translate(CHANGE_HELMET_NIGHT_VISION, TextFormatting.GRAY, KeybindsUtil.getKeyBindText(Keybinds.keyHelmetVision)));
        tooltip.add(TranslationUtil.translate(HELMET_INFO, TextFormatting.GRAY));
        if (!Config.EAT_WITH_HELMET.get())
            tooltip.add(TranslationUtil.translate(HELMET_INFO2));
        if(Config.HELMET_PROVIDES_FOOD.get())
            tooltip.add(TranslationUtil.translate(HELMET_INFO3));
        tooltip.add(TranslationHelper.translate(SOULBOUND_UPGRADE));
    }

    private static class Material implements IArmorMaterial {

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
            return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
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

    }
}
