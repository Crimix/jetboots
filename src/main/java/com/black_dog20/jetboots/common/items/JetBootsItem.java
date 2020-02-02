package com.black_dog20.jetboots.common.items;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.black_dog20.jetboots.common.util.JetBootsProperties;
import com.google.common.collect.Multimap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
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
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

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
		return 0;
	}
	
	private double getJetBootsToughness(ItemStack stack) {
		return 0;
	}
	
	@Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);

        Minecraft mc = Minecraft.getInstance();

        if(JetBootsProperties.getEngineUpgrade(stack) || JetBootsProperties.getThrusterUpgrade(stack)) {
        	
        	if(JetBootsProperties.getEngineUpgrade(stack)) {
        		String mode = "";
        		if(JetBootsProperties.getMode(stack)) {
        			mode = new TranslationTextComponent("jetboots.tooltip.item.elytra").applyTextStyle(TextFormatting.LIGHT_PURPLE).getFormattedText();
        		} else {
        			mode = new TranslationTextComponent("jetboots.tooltip.item.normal").applyTextStyle(TextFormatting.GREEN).getFormattedText();
        		}
        		tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.flight_mode", mode).applyTextStyle(TextFormatting.WHITE));
        	}
        	
        	if(JetBootsProperties.getThrusterUpgrade(stack)) {
        		String mode = "";
        		if(JetBootsProperties.getSpeed(stack)) {
        			mode = new TranslationTextComponent("jetboots.tooltip.item.super").applyTextStyle(TextFormatting.RED).getFormattedText();
        		} else {
        			mode = new TranslationTextComponent("jetboots.tooltip.item.normal").applyTextStyle(TextFormatting.GREEN).getFormattedText();
        		}
        		tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.speed_mode", mode).applyTextStyle(TextFormatting.WHITE));
        	}
        	
            tooltip.add(new TranslationTextComponent(""));
        }
        
        if (!InputMappings.isKeyDown(mc.getMainWindow().getHandle(), mc.gameSettings.keyBindSneak.getKey().getKeyCode())) {
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
           
            if(JetBootsProperties.getEngineUpgrade(stack))
                tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.engine_upgrade").applyTextStyle(TextFormatting.GREEN));
            if(JetBootsProperties.getThrusterUpgrade(stack))
                tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.thruster_upgrade").applyTextStyle(TextFormatting.GREEN));
            if(JetBootsProperties.getShockUpgrade(stack))
                tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.shock_absorber_upgrade").applyTextStyle(TextFormatting.GREEN));
            if(JetBootsProperties.getUnderWaterUpgrade(stack))
                tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.underwater_upgrade").applyTextStyle(TextFormatting.AQUA));
            if(JetBootsProperties.getSoulboundUpgrade(stack))
                tooltip.add(new TranslationTextComponent("jetboots.tooltip.item.soulbound_upgrade").applyTextStyle(TextFormatting.LIGHT_PURPLE));
        }
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
