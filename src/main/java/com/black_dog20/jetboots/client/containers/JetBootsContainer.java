package com.black_dog20.jetboots.client.containers;

import com.black_dog20.jetboots.common.util.JetBootsItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class JetBootsContainer extends Container {
    protected LazyOptional<IItemHandler> inventory;
    public ItemStack jetboots;

    public JetBootsContainer(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        super(ModContainers.JETBOOTS_CONTAINER.get(), windowId);

        jetboots = player.getHeldItemMainhand();

        inventory = jetboots.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);

        inventory.ifPresent(i -> {
            addSlots(i);
            addPlayerSlots(playerInventory);
        });

        if (!inventory.isPresent())
            player.closeScreen();

    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if(inventory.isPresent())
        {
            IItemHandler handler = inventory
                    .orElseGet(null);
            if (handler instanceof JetBootsItemHandler) {
                ((JetBootsItemHandler) handler).serializeNBT();
            }
        }

    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();

            if (index < containerSlots) {
                if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        // this will prevent the player from interacting with the item that opened the inventory
        if (slotId >= 0 && getSlot(slotId) != null && getSlot(slotId).getStack() == player.getHeldItemMainhand()) {
            return ItemStack.EMPTY;
        }
        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

    private int addSlotRange(PlayerInventory playerInventory, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new Slot(playerInventory, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(PlayerInventory playerInventory, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(playerInventory, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void addPlayerSlots(PlayerInventory playerInventory) {
        // Player inventory
        addSlotBox(playerInventory, 9, 10, 70, 9, 18, 3, 18);

        // Hotbar
        addSlotRange(playerInventory, 0, 10, 128, 9, 18);
    }

    private void addSlots(IItemHandler handler) {
        this.addSlot(new SlotUpgradeItem(handler, 0,10,35));
        this.addSlot(new SlotUpgradeItem(handler, 1,28,35));
        this.addSlot(new SlotUpgradeItem(handler, 2,46,35));
        this.addSlot(new SlotUpgradeItem(handler, 3,64,35));
        this.addSlot(new SlotUpgradeItem(handler, 4,82,35));
        this.addSlot(new SlotUpgradeItem(handler, 5,100,35));
        this.addSlot(new SlotUpgradeItem(handler, 6,118,35));
        this.addSlot(new SlotUpgradeItem(handler, 7,136,35));
        this.addSlot(new SlotUpgradeItem(handler, 8,154,35));
    }


}
