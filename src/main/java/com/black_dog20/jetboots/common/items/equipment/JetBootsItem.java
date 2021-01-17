package com.black_dog20.jetboots.common.items.equipment;

import com.black_dog20.bml.utils.item.MultiMapHelper;
import com.black_dog20.bml.utils.keybinds.KeybindsUtil;
import com.black_dog20.bml.utils.math.MathUtil;
import com.black_dog20.bml.utils.translate.TranslationUtil;
import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.api.events.ExtraTooltipEvent;
import com.black_dog20.jetboots.client.keybinds.Keybinds;
import com.black_dog20.jetboots.common.capabilities.JetbootsCapabilities;
import com.black_dog20.jetboots.common.items.BaseGuardianArmorItem;
import com.black_dog20.jetboots.common.items.materials.GuardianMaterial;
import com.black_dog20.jetboots.common.util.EnergyUtil;
import com.black_dog20.jetboots.common.util.JetBootsProperties;
import com.black_dog20.jetboots.common.util.LevelProperties;
import com.black_dog20.jetboots.common.util.ModUtils;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.List;

import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.*;

public class JetBootsItem extends BaseGuardianArmorItem {

    public JetBootsItem(Properties builder) {
        super(GuardianMaterial.getInstance(), EquipmentSlotType.FEET, builder.maxDamage(-1).setNoRepair());
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
        return LevelProperties.calculateValue(2, 6, stack);
    }

    private double getCustomToughness(ItemStack stack) {
        return LevelProperties.calculateValue(0, 4, stack);
    }

    private double getCustomKnockbackResistance(ItemStack stack) {
        return LevelProperties.calculateValue(0, 2, stack);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "jetboots:textures/armor/jetboots.png";
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);

        KeyBinding sneak = Minecraft.getInstance().gameSettings.keyBindSneak;

        stack.getCapability(CapabilityEnergy.ENERGY, null)
                .ifPresent(energy -> tooltip.add(
                        TranslationUtil.translate(STORED_ENERGY, TextFormatting.GREEN,
                                MathUtil.formatThousands(energy.getEnergyStored()),
                                MathUtil.formatThousands(energy.getMaxEnergyStored()))));
        EnergyUtil.getFormattedEnergyCost(stack).ifPresent(tooltip::add);

        if (JetBootsProperties.hasEngineUpgrade(stack) || JetBootsProperties.hasThrusterUpgrade(stack)) {
            if (JetBootsProperties.hasEngineUpgrade(stack)) {
                tooltip.add(TranslationUtil.translate(CHANGE_FLIGHT_MODE, TextFormatting.GRAY, KeybindsUtil.getKeyBindText(Keybinds.keyMode)));
            }
            if (JetBootsProperties.hasThrusterUpgrade(stack)) {
                tooltip.add(TranslationUtil.translate(CHANGE_SPEED_MODE, TextFormatting.GRAY, KeybindsUtil.getKeyBindText(Keybinds.keySpeed)));
            }
            if (JetBootsProperties.hasEngineUpgrade(stack)) {
                tooltip.add(ModUtils.getFlightModeText(stack, TextFormatting.GRAY));
            }
            if (JetBootsProperties.hasThrusterUpgrade(stack)) {
                tooltip.add(ModUtils.getFlightSpeedText(stack, TextFormatting.GRAY));
            }
        }

        ExtraTooltipEvent normalExtraTooltips = new ExtraTooltipEvent(stack, ExtraTooltipEvent.Type.NORMAL);
        if (!MinecraftForge.EVENT_BUS.post(normalExtraTooltips)) {
            tooltip.addAll(normalExtraTooltips.getExtraTooltips());
        }

        if (KeybindsUtil.isKeyDownIgnoreConflicts(sneak)) {
            tooltip.add(TranslationHelper.getLevelProgress(stack));

            if (JetBootsProperties.hasEngineUpgrade(stack)) {
                tooltip.add(ENGINE_UPGRADE.get());
            }
            if (JetBootsProperties.hasThrusterUpgrade(stack)) {
                tooltip.add(THRUSTER_UPGRADE.get());
            }
            if (JetBootsProperties.hasShockUpgrade(stack)) {
                tooltip.add(SHOCK_ABSORBER_UPGRADE.get());
            }
            if (JetBootsProperties.hasMuffledUpgrade(stack)) {
                tooltip.add(MUFFLED_UPGRADE.get());
            }
            if (JetBootsProperties.hasUnderWaterUpgrade(stack)) {
                tooltip.add(UNDERWATER_UPGRADE.get());
            }
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

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null)
                .orElse(null);
        return (energy.getEnergyStored() < energy.getMaxEnergyStored());
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new JetbootsCapabilities(stack, Config.DEFAULT_MAX_POWER.get());
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null)
                .orElse(null);
        if (energy == null)
            return 0;

        return 1D - (energy.getEnergyStored() / (double) energy.getMaxEnergyStored());
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {

        IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null)
                .orElse(null);
        if (energy == null)
            return super.getRGBDurabilityForDisplay(stack);

        return MathHelper.hsvToRGB(Math.max(0.0F, (float) energy.getEnergyStored() / (float) energy.getMaxEnergyStored()) / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return false;
    }
}
