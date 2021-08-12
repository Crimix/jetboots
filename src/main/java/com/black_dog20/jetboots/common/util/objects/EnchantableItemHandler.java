package com.black_dog20.jetboots.common.util.objects;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.black_dog20.jetboots.common.util.NBTTags.TAG_ENCHANTABLE_CONTAINER;

public class EnchantableItemHandler extends ItemStackHandler {
    private static int SIZE = 3;
    private final ItemStack container;

    public EnchantableItemHandler(ItemStack container) {
        super(SIZE);
        this.container = container;
    }

    @Override
    protected void onContentsChanged(int slot) {
        this.serializeNBT();
        EnchantmentHelper.setEnchantments(getEnchantmentMap(), container);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        Item item = stack.getItem();
        boolean valid = super.isItemValid(slot, stack);
        if (!valid || item != Items.ENCHANTED_BOOK) {
            return false;
        }

        Set<Enchantment> newEnchantments = EnchantmentHelper.getEnchantments(stack).keySet();
        boolean canBeAppliedToItem = newEnchantments.stream()
                .allMatch(e -> e.category.canEnchant(container.getItem()));

        if (!canBeAppliedToItem) {
            return false;
        }

        Set<Enchantment> currentEnchantments = getEnchantmentMap().keySet();

        return newEnchantments.stream()
                .allMatch(e -> EnchantmentHelper.isEnchantmentCompatible(currentEnchantments, e));
    }

    private Map<Enchantment, Integer> getEnchantmentMap() {
        return stacks.stream()
                .filter(s -> s.getItem() == Items.ENCHANTED_BOOK)
                .map(EnchantmentHelper::getEnchantments)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Math::max));
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
        CompoundTag compoundNBT = container.getOrCreateTag();
        if (compoundNBT.contains(TAG_ENCHANTABLE_CONTAINER)) {
            super.deserializeNBT(compoundNBT.getCompound(TAG_ENCHANTABLE_CONTAINER));
        }
    }

    private void save() {
        serializeNBT();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = super.serializeNBT();
        container.getOrCreateTag().put(TAG_ENCHANTABLE_CONTAINER, nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        CompoundTag compoundNBT = container.getOrCreateTag();
        if (compoundNBT.contains(TAG_ENCHANTABLE_CONTAINER)) {
            super.deserializeNBT(compoundNBT.getCompound(TAG_ENCHANTABLE_CONTAINER));
        }
    }
}
