package com.black_dog20.jetboots.common.items.equipment;

import com.black_dog20.bml.utils.item.MultiMapHelper;
import com.black_dog20.bml.utils.keybinds.KeybindsUtil;
import com.black_dog20.bml.utils.translate.TranslationUtil;
import com.black_dog20.jetboots.api.events.ExtraTooltipEvent;
import com.black_dog20.jetboots.common.capabilities.GuardianCapabilities;
import com.black_dog20.jetboots.common.items.BaseGuardianArmorItem;
import com.black_dog20.jetboots.common.items.materials.GuardianMaterial;
import com.black_dog20.jetboots.common.util.LevelProperties;
import com.black_dog20.jetboots.common.util.TranslationHelper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.List;

import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.*;

public class GuardianJacketItem extends BaseGuardianArmorItem {

    public GuardianJacketItem(Properties builder) {
        super(GuardianMaterial.getInstance(), EquipmentSlotType.CHEST, builder.maxDamage(-1).setNoRepair());
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create(super.getAttributeModifiers(slot));
        if (slot == this.slot) {
            MultiMapHelper.removeValues(multimap, Attributes.ARMOR, ARMOR_MODIFIERS[slot.getIndex()]);
            MultiMapHelper.removeValues(multimap, Attributes.ARMOR_TOUGHNESS, ARMOR_MODIFIERS[slot.getIndex()]);
            MultiMapHelper.removeValues(multimap, Attributes.KNOCKBACK_RESISTANCE, ARMOR_MODIFIERS[slot.getIndex()]);
            multimap.put(Attributes.ARMOR, new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor modifier", getCustomDamageReduceAmount(stack), AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor toughness", getCustomToughness(stack), AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor knockback resistance", getCustomKnockbackResistance(stack), AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    private double getCustomDamageReduceAmount(ItemStack stack) {
        return LevelProperties.calculateValue(6, 10, stack);
    }

    private double getCustomToughness(ItemStack stack) {
        return LevelProperties.calculateValue(0, 4, stack);
    }

    private double getCustomKnockbackResistance(ItemStack stack) {
        return LevelProperties.calculateValue(0, 2, stack);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "jetboots:textures/armor/guardian_armor.png";
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        KeyBinding sneak = Minecraft.getInstance().gameSettings.keyBindSneak;

        ExtraTooltipEvent normalExtraTooltips = new ExtraTooltipEvent(stack, ExtraTooltipEvent.Type.NORMAL);
        if (!MinecraftForge.EVENT_BUS.post(normalExtraTooltips)) {
            tooltip.addAll(normalExtraTooltips.getExtraTooltips());
        }

        if (KeybindsUtil.isKeyDownIgnoreConflicts(sneak)) {
            tooltip.add(TranslationHelper.getLevelProgress(stack));
            if (isSoulbound(stack)) {
                tooltip.add(TranslationUtil.translate(SOULBOUND, TextFormatting.LIGHT_PURPLE));
            }

            ExtraTooltipEvent sneakExtraTooltips = new ExtraTooltipEvent(stack, ExtraTooltipEvent.Type.SNEAKING);
            if (!MinecraftForge.EVENT_BUS.post(sneakExtraTooltips)) {
                tooltip.addAll(sneakExtraTooltips.getExtraTooltips());
            }
        } else {
            ExtraTooltipEvent notSneakExtraTooltips = new ExtraTooltipEvent(stack, ExtraTooltipEvent.Type.NOT_SNEAKING);
            if (!MinecraftForge.EVENT_BUS.post(notSneakExtraTooltips)) {
                tooltip.addAll(notSneakExtraTooltips.getExtraTooltips());
            }

            tooltip.add(TranslationUtil.translate(OPEN_CONTAINER, TextFormatting.GRAY));
            tooltip.add(TranslationUtil.translate(SHOW_MORE_INFO, TextFormatting.GRAY, KeybindsUtil.getKeyBindText(sneak)));
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new GuardianCapabilities(stack);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return false;
    }
}
