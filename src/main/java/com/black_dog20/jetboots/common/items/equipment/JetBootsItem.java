package com.black_dog20.jetboots.common.items.equipment;

import com.black_dog20.bml.utils.item.MultiMapHelper;
import com.black_dog20.bml.utils.keybinds.KeybindsUtil;
import com.black_dog20.bml.utils.leveling.ItemLevelProperties;
import com.black_dog20.bml.utils.math.MathUtil;
import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.api.events.ExtraTooltipEvent;
import com.black_dog20.jetboots.client.keybinds.Keybinds;
import com.black_dog20.jetboots.common.capabilities.JetbootsCapabilities;
import com.black_dog20.jetboots.common.items.BaseGuardianArmorItem;
import com.black_dog20.jetboots.common.items.materials.GuardianMaterial;
import com.black_dog20.jetboots.common.util.EnergyUtil;
import com.black_dog20.jetboots.common.util.ModUtils;
import com.black_dog20.jetboots.common.util.properties.JetBootsProperties;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
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
        super(GuardianMaterial.getInstance(), EquipmentSlot.FEET, builder.durability(-1).setNoRepair());
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
        return ItemLevelProperties.calculateValue(Config.JETBOOTS_BASE_DAMAGE_REDUCE_AMOUNT.get(), Config.JETBOOTS_MAX_DAMAGE_REDUCE_AMOUNT.get(), stack);
    }

    private double getCustomToughness(ItemStack stack) {
        return ItemLevelProperties.calculateValue(Config.JETBOOTS_BASE_TOUGHNESS_AMOUNT.get(), Config.JETBOOTS_MAX_TOUGHNESS_AMOUNT.get(), stack);
    }

    private double getCustomKnockbackResistance(ItemStack stack) {
        return ItemLevelProperties.calculateValue(Config.JETBOOTS_BASE_KNOCKBACK_RESISTANCE_AMOUNT.get(), Config.JETBOOTS_MAX_KNOCKBACK_RESISTANCE_AMOUNT.get(), stack);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "jetboots:textures/armor/jetboots.png";
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);

        KeyMapping sneak = Minecraft.getInstance().options.keyShift;

        stack.getCapability(CapabilityEnergy.ENERGY, null)
                .ifPresent(energy -> tooltip.add(STORED_ENERGY.get(
                        ChatFormatting.GREEN,
                        MathUtil.formatThousands(energy.getEnergyStored()),
                        MathUtil.formatThousands(energy.getMaxEnergyStored()))));
        EnergyUtil.getFormattedEnergyCost(stack).ifPresent(tooltip::add);

        if (JetBootsProperties.hasEngineUpgrade(stack) || JetBootsProperties.hasThrusterUpgrade(stack)) {
            if (JetBootsProperties.hasEngineUpgrade(stack)) {
                tooltip.add(CHANGE_FLIGHT_MODE.get(ChatFormatting.GRAY, KeybindsUtil.getKeyBindText(Keybinds.keyMode)));
            }
            if (JetBootsProperties.hasThrusterUpgrade(stack)) {
                tooltip.add(CHANGE_SPEED_MODE.get(ChatFormatting.GRAY, KeybindsUtil.getKeyBindText(Keybinds.keySpeed)));
            }
            if (JetBootsProperties.hasEngineUpgrade(stack)) {
                tooltip.add(ModUtils.getFlightModeText(stack, ChatFormatting.GRAY));
            }
            if (JetBootsProperties.hasThrusterUpgrade(stack)) {
                tooltip.add(ModUtils.getFlightSpeedText(stack, ChatFormatting.GRAY));
            }
        }

        ExtraTooltipEvent normalExtraTooltips = new ExtraTooltipEvent(stack, ExtraTooltipEvent.Type.NORMAL);
        if (!MinecraftForge.EVENT_BUS.post(normalExtraTooltips)) {
            tooltip.addAll(normalExtraTooltips.getExtraTooltips());
        }

        if (KeybindsUtil.isKeyDownIgnoreConflicts(sneak)) {
            if (ItemLevelProperties.getMaxLevel(stack) > 0) {
                tooltip.add(ItemLevelProperties.getLevelProgress(stack));
            }

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
                tooltip.add(SOULBOUND.get(ChatFormatting.LIGHT_PURPLE));
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

            tooltip.add(OPEN_CONTAINER.get(ChatFormatting.GRAY));
            tooltip.add(SHOW_MORE_INFO.get(ChatFormatting.GRAY, KeybindsUtil.getKeyBindText(sneak)));
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null)
                .orElse(null);
        return (energy.getEnergyStored() < energy.getMaxEnergyStored());
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new JetbootsCapabilities(stack, Config.DEFAULT_MAX_POWER.get());
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null)
                .orElse(null);
        if (energy == null)
            return 0;

        double widthPercentage = (1D - (energy.getEnergyStored() / (double) energy.getMaxEnergyStored()));
        return Math.round(13.0F - (float)widthPercentage * 13.0F) ;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null)
                .orElse(null);
        if (energy == null)
            return super.getBarColor(stack);

        return Mth.hsvToRgb(Math.max(0.0F, (float) energy.getEnergyStored() / (float) energy.getMaxEnergyStored()) / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return Config.JETBOOTS_MAX_LEVEL.get();
    }
}
