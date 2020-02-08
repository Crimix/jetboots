package com.black_dog20.jetboots.common.items;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.client.keybinds.Keybinds;
import com.black_dog20.jetboots.common.capabilities.CapabilityEnergyProvider;
import com.black_dog20.jetboots.common.util.JetBootsProperties;
import com.black_dog20.jetboots.common.util.JetbootsUtil;
import com.black_dog20.jetboots.common.util.Utils;
import com.google.common.collect.Multimap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class JetBootsItem extends BaseArmorItem {

	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("7c2b2167-ab05-448b-9f6f-e27e62467f3b");
	private static JetBootsMaterial JET_BOOTS_MATERIAL = new JetBootsMaterial();

	public JetBootsItem(Properties builder) {
		super(JET_BOOTS_MATERIAL, EquipmentSlotType.FEET, builder.defaultMaxDamage(-1));
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot);
		if (slot == EquipmentSlotType.FEET) {
			multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIER_UUID, "Armor modifier", getJetBootsDamageReduceAmount(stack), AttributeModifier.Operation.ADDITION));
			multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(ARMOR_MODIFIER_UUID, "Armor toughness", getJetBootsToughness(stack), AttributeModifier.Operation.ADDITION));
		}

		return multimap;
	}

	private double getJetBootsDamageReduceAmount(ItemStack stack) {

		if(JetBootsProperties.getCustomArmorUpgrade(stack) >= 0)
			return JetBootsProperties.getCustomArmorUpgrade(stack);
		else if(JetBootsProperties.getDiamondArmorUpgrade(stack))
			return 6;
		else if(JetBootsProperties.getIronArmorUpgrade(stack))
			return 5;
		else if(JetBootsProperties.getLeatherArmorUpgrade(stack))
			return 2;
		else
			return 0;
	}

	private double getJetBootsToughness(ItemStack stack) {
		if(JetBootsProperties.getCustomToughnessUpgrade(stack) >= 0)
			return JetBootsProperties.getCustomToughnessUpgrade(stack);
		else if(JetBootsProperties.getDiamondArmorUpgrade(stack))
			return 4;
		else
			return 0;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return "jetboots:textures/armor/jetboots.png";
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);

		Minecraft mc = Minecraft.getInstance();
		
		if(Config.USE_POWER.get()) {
			stack.getCapability(CapabilityEnergy.ENERGY, null)
			.ifPresent(energy -> tooltip.add(
					new TranslationTextComponent("jetboots.tooltip.item.stored_energy",
							Utils.format(energy.getEnergyStored()),
							Utils.format(energy.getMaxEnergyStored())).applyTextStyles(TextFormatting.GREEN)));
		}

		if(JetBootsProperties.getEngineUpgrade(stack) || JetBootsProperties.getThrusterUpgrade(stack)) {
			if(JetBootsProperties.getEngineUpgrade(stack)) {
				tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.change_flight",
						Keybinds.keyMode.getLocalizedName().toUpperCase())
						.applyTextStyle(TextFormatting.GRAY));
			}
			if(JetBootsProperties.getThrusterUpgrade(stack)) {
				tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.change_speed",
						Keybinds.keySpeed.getLocalizedName().toUpperCase())
						.applyTextStyle(TextFormatting.GRAY));
			}

			if(JetBootsProperties.getEngineUpgrade(stack)) {
				tooltip.add(JetbootsUtil.getFlightModeText(stack));
			}

			if(JetBootsProperties.getThrusterUpgrade(stack)) {
				tooltip.add(JetbootsUtil.getFlightSpeedText(stack));
			}
			tooltip.add(new TranslationTextComponent(""));
		}

		if (!InputMappings.isKeyDown(mc.mainWindow.getHandle(), mc.gameSettings.keyBindSneak.getKey().getKeyCode())) {
			tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.show_upgrades",
					mc.gameSettings.keyBindSneak.getLocalizedName().toLowerCase())
					.applyTextStyle(TextFormatting.GRAY));
		} else {
			tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.upgrades").applyTextStyle(TextFormatting.WHITE));

			if(JetBootsProperties.getCustomArmorUpgrade(stack) >= 0) {
				String name = new TranslationTextComponent(JetBootsProperties.getCustomArmorUpgradeName(stack)).getFormattedText();
				tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.custom_armor_upgrade", name).applyTextStyle(TextFormatting.LIGHT_PURPLE));
			}
			else if(JetBootsProperties.getDiamondArmorUpgrade(stack))
				tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.diamond_armor_upgrade").applyTextStyle(TextFormatting.BLUE));
			else if(JetBootsProperties.getIronArmorUpgrade(stack))
				tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.iron_armor_upgrade").applyTextStyle(TextFormatting.GREEN));
			else if(JetBootsProperties.getLeatherArmorUpgrade(stack))
				tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.leather_armor_upgrade").applyTextStyle(TextFormatting.GREEN));

			if(Config.USE_POWER.get()) {
				String mode = "";
				if(JetBootsProperties.getSuperBattery(stack)) {
					mode = new TranslationTextComponent("jetboots.tooltip.item.super").applyTextStyle(TextFormatting.LIGHT_PURPLE).getFormattedText();
				} else if(JetBootsProperties.getAdvancedBattery(stack)) {
					mode = new TranslationTextComponent("jetboots.tooltip.item.advanced").applyTextStyle(TextFormatting.RED).getFormattedText();
				} else {
					mode = new TranslationTextComponent("jetboots.tooltip.item.basic").applyTextStyle(TextFormatting.GREEN).getFormattedText();
				}
				tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.battery_upgrade", mode).applyTextStyle(TextFormatting.WHITE));
			}

			if(JetBootsProperties.getEngineUpgrade(stack))
				tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.engine_upgrade").applyTextStyle(TextFormatting.GREEN));
			if(JetBootsProperties.getThrusterUpgrade(stack))
				tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.thruster_upgrade").applyTextStyle(TextFormatting.GREEN));
			if(JetBootsProperties.getShockUpgrade(stack))
				tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.shock_absorber_upgrade").applyTextStyle(TextFormatting.GREEN));
			if(JetBootsProperties.getMuffledUpgrade(stack))
				tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.muffled_upgrade").applyTextStyle(TextFormatting.GREEN));
			if(JetBootsProperties.getUnderWaterUpgrade(stack))
				tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.underwater_upgrade").applyTextStyle(TextFormatting.AQUA));
			if(JetBootsProperties.getSoulboundUpgrade(stack))
				tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.soulbound_upgrade").applyTextStyle(TextFormatting.LIGHT_PURPLE));
		}
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		if(Config.USE_POWER.get()) {
			IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
			return (energy.getEnergyStored() < energy.getMaxEnergyStored());
		} else 
			return false;
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
		return new CapabilityEnergyProvider(stack, Config.DEFAULT_MAX_POWER.get());
	}


	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		if(Config.USE_POWER.get()) {
			IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
			if (energy == null)
				return 0;

			return 1D - (energy.getEnergyStored() / (double) energy.getMaxEnergyStored());
		} else 
			return super.getDurabilityForDisplay(stack);
	}

	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		if(Config.USE_POWER.get()) {
			IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
			if (energy == null)
				return super.getRGBDurabilityForDisplay(stack);

			return MathHelper.hsvToRGB(Math.max(0.0F, (float) energy.getEnergyStored() / (float) energy.getMaxEnergyStored()) / 3.0F, 1.0F, 1.0F);
		} else 
			return super.getRGBDurabilityForDisplay(stack);
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

	}
}
