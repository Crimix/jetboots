package com.black_dog20.jetboots.common.items;

import com.black_dog20.bml.api.ISoulbindable;
import com.black_dog20.bml.utils.item.MultiMapHelper;
import com.black_dog20.bml.utils.keybinds.KeybindsUtil;
import com.black_dog20.bml.utils.math.MathUtil;
import com.black_dog20.bml.utils.translate.TranslationUtil;
import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.client.containers.JetBootsContainer;
import com.black_dog20.jetboots.client.keybinds.Keybinds;
import com.black_dog20.jetboots.common.items.upgrades.api.IArmorUpgrade;
import com.black_dog20.jetboots.common.util.EnergyItem;
import com.black_dog20.jetboots.common.util.EnergyUtil;
import com.black_dog20.jetboots.common.util.JetBootsItemHandler;
import com.black_dog20.jetboots.common.util.JetBootsProperties;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.black_dog20.jetboots.common.util.TranslationHelper.Translations.*;

public class JetBootsItem extends BaseArmorItem implements ISoulbindable {

    private static JetBootsMaterial JET_BOOTS_MATERIAL = new JetBootsMaterial();

    public JetBootsItem(Properties builder) {
        super(JET_BOOTS_MATERIAL, EquipmentSlotType.FEET, builder.defaultMaxDamage(-1));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create(super.getAttributeModifiers(slot));
        if (slot == this.slot) {
            MultiMapHelper.removeValues(multimap, Attributes.ARMOR, ARMOR_MODIFIERS[slot.getIndex()]);
            MultiMapHelper.removeValues(multimap, Attributes.ARMOR_TOUGHNESS, ARMOR_MODIFIERS[slot.getIndex()]);
            MultiMapHelper.removeValues(multimap, Attributes.KNOCKBACK_RESISTANCE, ARMOR_MODIFIERS[slot.getIndex()]);
            multimap.put(Attributes.ARMOR, new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor modifier", getJetBootsDamageReduceAmount(stack), AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor toughness", getJetBootsToughness(stack), AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(ARMOR_MODIFIERS[slot.getIndex()], "Armor knockback resistance", getKnockbackResistance(stack), AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    private double getJetBootsDamageReduceAmount(ItemStack stack) {
        return JetBootsProperties.getArmorUpgrade(stack)
                .filter(upgrade -> upgrade.providesProtection(stack))
                .map(IArmorUpgrade::getArmor)
                .orElse(0.0);
    }

    private double getJetBootsToughness(ItemStack stack) {
        return JetBootsProperties.getArmorUpgrade(stack)
                .filter(upgrade -> upgrade.providesProtection(stack))
                .map(IArmorUpgrade::getToughness)
                .orElse(0.0);
    }

    private double getKnockbackResistance(ItemStack stack) {
        return JetBootsProperties.getArmorUpgrade(stack)
                .filter(upgrade -> upgrade.providesProtection(stack))
                .map(IArmorUpgrade::getKnockbackResistance)
                .orElse(0.0);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "jetboots:textures/armor/jetboots.png";
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);

        Minecraft mc = Minecraft.getInstance();
        KeyBinding sneak = mc.gameSettings.keyBindSneak;
        int keyModifier = GLFW.GLFW_KEY_LEFT_CONTROL;

        stack.getCapability(CapabilityEnergy.ENERGY, null)
                .ifPresent(energy -> tooltip.add(
                        TranslationUtil.translate(STORED_ENERGY, TextFormatting.GREEN,
                                MathUtil.formatThousands(energy.getEnergyStored()),
                                MathUtil.formatThousands(energy.getMaxEnergyStored()))));

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

            tooltip.add(new TranslationTextComponent(""));
        }

        if (KeybindsUtil.isKeyDownIgnoreConflicts(sneak)
                && !KeybindsUtil.isKeyDown(keyModifier)) {
            tooltip.add(TranslationUtil.translate(UPGRADES, TextFormatting.WHITE));

            JetBootsProperties.getArmorUpgrade(stack)
                    .ifPresent(armor -> tooltip.add(armor.getTooltip()));

            JetBootsProperties.getBatteryUpgrade(stack)
                    .ifPresent(battert -> tooltip.add(battert.getTooltip()));
            JetBootsProperties.getConverterUpgrade(stack)
                    .ifPresent(converter -> tooltip.add(converter.getTooltip()));

            JetBootsProperties.getEngineUpgrade(stack)
                    .ifPresent(upgrade -> tooltip.add(upgrade.getTooltip()));
            JetBootsProperties.getThrusterUpgrade(stack)
                    .ifPresent(upgrade -> tooltip.add(upgrade.getTooltip()));
            JetBootsProperties.getShockUpgrade(stack)
                    .ifPresent(upgrade -> tooltip.add(upgrade.getTooltip()));
            JetBootsProperties.getMuffledUpgrade(stack)
                    .ifPresent(upgrade -> tooltip.add(upgrade.getTooltip()));
            JetBootsProperties.getUnderWaterUpgrade(stack)
                    .ifPresent(upgrade -> tooltip.add(upgrade.getTooltip()));
            JetBootsProperties.getSoulboundUpgrade(stack)
                    .ifPresent(upgrade -> tooltip.add(upgrade.getTooltip()));
        } else if (KeybindsUtil.isKeyDownIgnoreConflicts(sneak)
                && KeybindsUtil.isKeyDown(keyModifier)) {
            EnergyUtil.getFormattedEnergyValue(EnergyUtil.getEnergyWhileFlying(stack)).ifPresent(
                    t -> tooltip.add(TranslationUtil.translate(FLYING_ENERGY, t))
            );
            EnergyUtil.getFormattedEnergyValue(EnergyUtil.getEnergyWhileWalking(stack)).ifPresent(
                    t -> tooltip.add(TranslationUtil.translate(WALKING_ENERGY, t))
            );
            EnergyUtil.getFormattedEnergyValue(EnergyUtil.getEnergyOnHit(stack)).ifPresent(
                    t -> tooltip.add(TranslationUtil.translate(HIT_ENERGY, t))
            );
            EnergyUtil.getFormattedEnergyValue(EnergyUtil.getEnergyOnHurt(stack)).ifPresent(
                    t -> tooltip.add(TranslationUtil.translate(HURT_ENERGY, t))
            );
        } else {
            tooltip.add(TranslationUtil.translate(OPEN_UPGRADES, TextFormatting.GRAY));
            tooltip.add(TranslationUtil.translate(SHOW_UPGRADES, TextFormatting.GRAY, KeybindsUtil.getKeyBindText(sneak)));
            tooltip.add(TranslationUtil.translate(SHOW_ENERGY, TextFormatting.GRAY, KeybindsUtil.getKeyBindText(sneak), KeybindsUtil.getKeyBindText(keyModifier)));
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
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if (player.isCrouching()) {
            if (!world.isRemote) {
                player.openContainer(new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return TranslationHelper.translate(JETBOOTS_UPGRADES);
                    }

                    @Nullable
                    @Override
                    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
                        return new JetBootsContainer(windowId, playerInventory, player);
                    }
                });
            }
            return ActionResult.resultPass(player.getHeldItem(hand));
        }
        return super.onItemRightClick(world, player, hand);
    }


    private static class JetBootsMaterial implements IArmorMaterial {

        @Override
        public int getDurability(EquipmentSlotType slotIn) {
            return -1;
        }

        @Override
        public int getDamageReductionAmount(EquipmentSlotType slotIn) {
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
            return "jetboots-boots";
        }

        @Override
        public float getToughness() {
            return 0;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }

    }

    class JetbootsCapabilities implements ICapabilityProvider {
        private ItemStack jetboots;
        private int energyCapacity;
        private LazyOptional<IEnergyStorage> capability = LazyOptional.of(() -> new EnergyItem(jetboots, energyCapacity));
        private LazyOptional<IItemHandler> optional = LazyOptional.of(() -> new JetBootsItemHandler(jetboots));

        public JetbootsCapabilities(ItemStack stack, int energyCapacity) {
            jetboots = stack;
            this.energyCapacity = energyCapacity;
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == null)
                return LazyOptional.empty();

            if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                return optional.cast();
            else if (cap == CapabilityEnergy.ENERGY)
                return capability.cast();
            else
                return LazyOptional.empty();
        }
    }

    @Override
    public boolean isSoulbound(ItemStack stack) {
        return JetBootsProperties.hasSoulboundUpgrade(stack);
    }
}
