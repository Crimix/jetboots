package com.black_dog20.jetboots.common.util;

import com.black_dog20.jetboots.common.items.upgrades.api.IUpgrade;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.stream.IntStream;

import static com.black_dog20.jetboots.common.items.upgrades.api.IUpgrade.*;
import static com.black_dog20.jetboots.common.items.upgrades.api.IUpgrade.Type.*;

public class JetBootsItemHandler extends ItemStackHandler {
    private static int SIZE = 9;
    private Type[] VALID_TYPES_SLOTS = {ARMOR, BATTERY, ENGINE, THRUSTER, SHOCK_ABSORBER, MUFFLED, UNDERWATER, CONVERTER, SOULBOUND};
    private final ItemStack jetboots;

    public JetBootsItemHandler(ItemStack stack) {
        super(SIZE);
        jetboots = stack;
    }

    @Override
    protected void onContentsChanged(int slot) {
        this.serializeNBT();
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        Item item = stack.getItem();
        Type type = VALID_TYPES_SLOTS[slot];
        boolean valid = item instanceof IUpgrade && ((IUpgrade) item).getUpgradeType() == type;
        return valid && super.isItemValid(slot, stack);
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        this.load();
        super.setStackInSlot(slot, stack);
        this.save();
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        this.load();
        return super.getStackInSlot(slot);
    }

    public ItemStack getStackInSlotByType(Type type) {
        this.load();
        int slot = IntStream.range(0, VALID_TYPES_SLOTS.length)
                .filter(t -> VALID_TYPES_SLOTS[t].equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Upgrade type not found in upgrade slots, something is very wrong."));
        return super.getStackInSlot(slot);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        this.load();
        ItemStack ret = super.insertItem(slot, stack, simulate);
        this.save();
        return ret;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        this.load();
        return super.extractItem(slot, amount, simulate);
    }

    private void load() {
        CompoundNBT compoundNBT = jetboots.getOrCreateTag();
        if (compoundNBT.contains("JetBootsInventory")) {
            super.deserializeNBT(compoundNBT.getCompound("JetBootsInventory"));
        }
    }

    private void save() {
        serializeNBT();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = super.serializeNBT();
        jetboots.getOrCreateTag().put("JetBootsInventory", nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        CompoundNBT compoundNBT = jetboots.getOrCreateTag();
        if (compoundNBT.contains("JetBootsInventory")) {
            super.deserializeNBT(compoundNBT.getCompound("JetBootsInventory"));
        }
    }
}
