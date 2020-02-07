package com.black_dog20.jetboots.common.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;

public class SoulBoundInventory {

	public final NonNullList<ItemStack> mainInventory;
	public final NonNullList<ItemStack> armorInventory;
	public final NonNullList<ItemStack> offHandInventory;
	public final PlayerEntity player;

	private SoulBoundInventory(PlayerEntity player, boolean load) {
		this.player = player;
		this.mainInventory = NonNullList.<ItemStack>withSize(player.inventory.mainInventory.size(), ItemStack.EMPTY);
		this.armorInventory = NonNullList.<ItemStack>withSize(player.inventory.armorInventory.size(), ItemStack.EMPTY);
		this.offHandInventory = NonNullList.<ItemStack>withSize(player.inventory.offHandInventory.size(), ItemStack.EMPTY);
		
		if(load) {
			readFromNBT();
		}
		else {
			copyMain();
			copyArmor();
			copyOffHand();
		}
	}

	public SoulBoundInventory(PlayerEntity player) {
		this(player, false);
	}

	public static SoulBoundInventory GetForPlayer(PlayerEntity player) {
		return new SoulBoundInventory(player, true);
	}

	private void copyMain() {
		NonNullList<ItemStack> old = player.inventory.mainInventory;
		for(int i = 0; i < old.size(); i++) {
			if(NBTTags.doesItemStackHaveTag(old.get(i), NBTTags.UPGRAE_SOULBOUND)) {
				this.mainInventory.set(i, old.get(i).copy());
				old.get(i).setCount(0);
			}
		}
	}
	
	private void copyArmor() {
		NonNullList<ItemStack> old = player.inventory.armorInventory;
		for(int i = 0; i < old.size(); i++) {
			if(NBTTags.doesItemStackHaveTag(old.get(i), NBTTags.UPGRAE_SOULBOUND)) {
				this.armorInventory.set(i, old.get(i).copy());
				old.get(i).setCount(0);
			}
		}
	}

	private void copyOffHand() {
		NonNullList<ItemStack> old = player.inventory.offHandInventory;
		for(int i = 0; i < old.size(); i++) {
			if(NBTTags.doesItemStackHaveTag(old.get(i), NBTTags.UPGRAE_SOULBOUND)) {
				this.offHandInventory.set(i, old.get(i));
				old.get(i).setCount(0);
			}
		}
	}
	
	public void restoreMain(PlayerEntity player) {
		NonNullList<ItemStack> old = this.mainInventory;
		for(int i = 0; i < old.size(); i++) {
			if(NBTTags.doesItemStackHaveTag(old.get(i), NBTTags.UPGRAE_SOULBOUND)) {
				player.inventory.mainInventory.set(i, old.get(i).copy());
				old.get(i).setCount(0);
			}
		}
	}
	
	
	public void restoreArmor(PlayerEntity player) {
		NonNullList<ItemStack> old = this.armorInventory;
		for(int i = 0; i < old.size(); i++) {
			if(NBTTags.doesItemStackHaveTag(old.get(i), NBTTags.UPGRAE_SOULBOUND)) {
				player.inventory.armorInventory.set(i, old.get(i).copy());
				old.get(i).setCount(0);
			}
		}
	}
	
	public void restoreHand(PlayerEntity player) {
		NonNullList<ItemStack> old = this.offHandInventory;
		for(int i = 0; i < old.size(); i++) {
			if(NBTTags.doesItemStackHaveTag(old.get(i), NBTTags.UPGRAE_SOULBOUND)) {
				player.inventory.offHandInventory.set(i, old.get(i));
				old.get(i).setCount(0);
			}
		}
	}

	public void writeToNBT()
	{
		ListNBT nbtTagListIn = new ListNBT();
		for (int i = 0; i < this.mainInventory.size(); ++i)
		{
			if (!((ItemStack)this.mainInventory.get(i)).isEmpty())
			{
				CompoundNBT nbttagcompound = new CompoundNBT();
				nbttagcompound.putByte("Slot", (byte)i);
				((ItemStack)this.mainInventory.get(i)).write(nbttagcompound);
				nbtTagListIn.add(nbttagcompound);
			}
		}

		for (int j = 0; j < this.armorInventory.size(); ++j)
		{
			if (!((ItemStack)this.armorInventory.get(j)).isEmpty())
			{
				CompoundNBT nbttagcompound1 = new CompoundNBT();
				nbttagcompound1.putByte("Slot", (byte)(j + 100));
				((ItemStack)this.armorInventory.get(j)).write(nbttagcompound1);
				nbtTagListIn.add(nbttagcompound1);
			}
		}

		for (int k = 0; k < this.offHandInventory.size(); ++k)
		{
			if (!((ItemStack)this.offHandInventory.get(k)).isEmpty())
			{
				CompoundNBT nbttagcompound2 = new CompoundNBT();
				nbttagcompound2.putByte("Slot", (byte)(k + 150));
				((ItemStack)this.offHandInventory.get(k)).write(nbttagcompound2);
				nbtTagListIn.add(nbttagcompound2);
			}
		}
		System.out.println(nbtTagListIn);
		player.getPersistentData().put(NBTTags.SOULBOUND_INVENTORY, nbtTagListIn);
		System.out.println(player.getPersistentData());
	}

	public void readFromNBT()
	{
		this.mainInventory.clear();
		this.armorInventory.clear();
		this.offHandInventory.clear();

		ListNBT nbtTagListIn = (ListNBT) player.getPersistentData().get(NBTTags.SOULBOUND_INVENTORY);
		System.out.println(nbtTagListIn);
		if(nbtTagListIn != null) {

			for (int i = 0; i < nbtTagListIn.size(); ++i)
			{
				CompoundNBT nbttagcompound = nbtTagListIn.getCompound(i);
				int j = nbttagcompound.getByte("Slot") & 255;
				ItemStack itemstack = ItemStack.read(nbttagcompound);

				if (!itemstack.isEmpty())
				{
					System.out.println(itemstack);
					if (j >= 0 && j < this.mainInventory.size())
					{
						this.mainInventory.set(j, itemstack);
					}
					else if (j >= 100 && j < this.armorInventory.size() + 100)
					{
						this.armorInventory.set(j - 100, itemstack);
					}
					else if (j >= 150 && j < this.offHandInventory.size() + 150)
					{
						this.offHandInventory.set(j - 150, itemstack);
					}
				}
			}
		}

	}


	public void clear() {
		player.getPersistentData().remove(NBTTags.SOULBOUND_INVENTORY);
	}

	public boolean addItemStackToInventory(ItemStack stack)
	{
		int i = getFirstStackWhichCanMerge(stack);
		if(i == -1) {
			i = getFirstEmptyStack();
			if(i == -1)
				return false;
			else {
				mainInventory.set(i, stack);
				return true;
			}
		} else {
			mainInventory.set(i, stack);
			return true;
		}
	}

	public int getFirstEmptyStack()
	{
		for (int i = 0; i < this.mainInventory.size(); ++i)
		{
			if (((ItemStack)this.mainInventory.get(i)).isEmpty())
			{
				return i;
			}
		}

		return -1;
	}

	public int getFirstStackWhichCanMerge(ItemStack stack)
	{
		for (int i = 0; i < this.mainInventory.size(); ++i)
		{
			if (this.mainInventory.get(i).getItem() == stack.getItem() && this.mainInventory.get(i).getMaxStackSize() >= (this.mainInventory.get(i).getCount() + stack.getCount()))
			{
				return i;
			}
		}

		return -1;
	}

}
