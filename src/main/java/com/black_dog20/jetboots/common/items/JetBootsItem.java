package com.black_dog20.jetboots.common.items;

import java.util.UUID;

import com.google.common.collect.Multimap;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

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
