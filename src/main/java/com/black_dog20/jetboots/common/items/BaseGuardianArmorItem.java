package com.black_dog20.jetboots.common.items;

import com.black_dog20.bml.api.ISoulbindable;
import com.black_dog20.jetboots.api.ILevelableItem;
import com.black_dog20.jetboots.client.containers.EnchantableItemContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BaseGuardianArmorItem extends ArmorItem implements ISoulbindable, ILevelableItem {

    public BaseGuardianArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if (player.isCrouching()) {
            if (!world.isRemote) {
                player.openContainer(new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return player.getHeldItem(hand).getDisplayName();
                    }

                    @Nullable
                    @Override
                    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
                        return new EnchantableItemContainer(windowId, playerInventory, player);
                    }
                });
            }
            return ActionResult.resultPass(player.getHeldItem(hand));
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public boolean isSoulbound(ItemStack stack) {
        return isSoulboundByLevel(stack);
    }

    @Override
    public boolean isDamageable() {
        return false;
    }
}
