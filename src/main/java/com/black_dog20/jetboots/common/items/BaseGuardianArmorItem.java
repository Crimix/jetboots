package com.black_dog20.jetboots.common.items;

import com.black_dog20.bml.utils.item.MultiMapHelper;
import com.black_dog20.jetboots.client.containers.EnchantableItemContainer;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class BaseGuardianArmorItem extends ArmorItem implements IBaseGuradianEquipment {

    public BaseGuardianArmorItem(ArmorMaterial materialIn, ArmorItem.Type pType, Properties builder) {
        super(materialIn, pType, builder);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (player.isCrouching()) {
            if (!world.isClientSide) {
                player.openMenu(new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return player.getItemInHand(hand).getHoverName();
                    }

                    @Nullable
                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
                        return new EnchantableItemContainer(windowId, playerInventory, player);
                    }
                });
            }
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }
        return super.use(world, player, hand);
    }

    @Override
    public boolean canBeDepleted() {
        return false;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create(super.getDefaultAttributeModifiers(slot));
        if (slot == this.getEquipmentSlot()) {
            UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(getType());
            MultiMapHelper.removeValues(multimap, Attributes.ARMOR, uuid);
            MultiMapHelper.removeValues(multimap, Attributes.ARMOR_TOUGHNESS, uuid);
            MultiMapHelper.removeValues(multimap, Attributes.KNOCKBACK_RESISTANCE, uuid);
            multimap.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", getCustomDamageReduceAmount(stack), AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", getCustomToughness(stack), AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", getCustomKnockbackResistance(stack), AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    abstract protected double getCustomDamageReduceAmount(ItemStack stack);

    abstract protected double getCustomToughness(ItemStack stack);

    abstract protected double getCustomKnockbackResistance(ItemStack stack);
}
