package com.black_dog20.jetboots.common.items.equipment;

import com.black_dog20.bml.utils.item.MultiMapHelper;
import com.black_dog20.bml.utils.keybinds.KeybindsUtil;
import com.black_dog20.bml.utils.leveling.ItemLevelProperties;
import com.black_dog20.bml.utils.translate.TranslationUtil;
import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.api.events.ExtraTooltipEvent;
import com.black_dog20.jetboots.common.capabilities.GuardianCapabilities;
import com.black_dog20.jetboots.common.items.BaseGuardianArmorItem;
import com.black_dog20.jetboots.common.items.materials.GuardianMaterial;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.List;

import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.OPEN_CONTAINER;
import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.SHOW_MORE_INFO;

public class GuardianJacketItem extends BaseGuardianArmorItem {

    public GuardianJacketItem(Properties builder) {
        super(GuardianMaterial.getInstance(), EquipmentSlot.CHEST, builder.durability(-1).setNoRepair());
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create(super.getDefaultAttributeModifiers(slot));
        if (slot == this.slot) {
            MultiMapHelper.removeValues(multimap, Attributes.ARMOR, ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()]);
            MultiMapHelper.removeValues(multimap, Attributes.ARMOR_TOUGHNESS, ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()]);
            MultiMapHelper.removeValues(multimap, Attributes.KNOCKBACK_RESISTANCE, ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()]);
            multimap.put(Attributes.ARMOR, new AttributeModifier(ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()], "Armor modifier", getCustomDamageReduceAmount(stack), AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()], "Armor toughness", getCustomToughness(stack), AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()], "Armor knockback resistance", getCustomKnockbackResistance(stack), AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    private double getCustomDamageReduceAmount(ItemStack stack) {
        return ItemLevelProperties.calculateValue(Config.JACKET_BASE_DAMAGE_REDUCE_AMOUNT.get(), Config.JACKET_MAX_DAMAGE_REDUCE_AMOUNT.get(), stack);
    }

    private double getCustomToughness(ItemStack stack) {
        return ItemLevelProperties.calculateValue(Config.JACKET_BASE_TOUGHNESS_AMOUNT.get(), Config.JACKET_MAX_TOUGHNESS_AMOUNT.get(), stack);
    }

    private double getCustomKnockbackResistance(ItemStack stack) {
        return ItemLevelProperties.calculateValue(Config.JACKET_BASE_KNOCKBACK_RESISTANCE_AMOUNT.get(), Config.JACKET_MAX_KNOCKBACK_RESISTANCE_AMOUNT.get(), stack);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "jetboots:textures/armor/guardian_armor.png";
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        KeyMapping sneak = Minecraft.getInstance().options.keyShift;

        ExtraTooltipEvent normalExtraTooltips = new ExtraTooltipEvent(stack, ExtraTooltipEvent.Type.NORMAL);
        if (!MinecraftForge.EVENT_BUS.post(normalExtraTooltips)) {
            tooltip.addAll(normalExtraTooltips.getExtraTooltips());
        }

        if (KeybindsUtil.isKeyDownIgnoreConflicts(sneak)) {
            tooltip.addAll(getLevelTooltips(stack));

            ExtraTooltipEvent sneakExtraTooltips = new ExtraTooltipEvent(stack, ExtraTooltipEvent.Type.SNEAKING);
            if (!MinecraftForge.EVENT_BUS.post(sneakExtraTooltips)) {
                tooltip.addAll(sneakExtraTooltips.getExtraTooltips());
            }
        } else {
            ExtraTooltipEvent notSneakExtraTooltips = new ExtraTooltipEvent(stack, ExtraTooltipEvent.Type.NOT_SNEAKING);
            if (!MinecraftForge.EVENT_BUS.post(notSneakExtraTooltips)) {
                tooltip.addAll(notSneakExtraTooltips.getExtraTooltips());
            }

            tooltip.add(TranslationUtil.translate(OPEN_CONTAINER, ChatFormatting.GRAY));
            tooltip.add(TranslationUtil.translate(SHOW_MORE_INFO, ChatFormatting.GRAY, KeybindsUtil.getKeyBindText(sneak)));
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new GuardianCapabilities(stack);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return Config.JACKET_MAX_LEVEL.get();
    }
}
