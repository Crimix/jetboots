package com.black_dog20.jetboots.common.items;

import com.black_dog20.jetboots.client.containers.EnchantableItemContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class BaseGuardianArmorItem extends ArmorItem implements IBaseGuradianEquipment {

    public BaseGuardianArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {
        super(materialIn, slot, builder);
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
}
