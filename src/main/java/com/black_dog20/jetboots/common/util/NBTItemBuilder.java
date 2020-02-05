package com.black_dog20.jetboots.common.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class NBTItemBuilder {
	
	private final ItemStack stack;
	
	private NBTItemBuilder(ItemStack stack) {
		this.stack = stack;
	}
	
	public static NBTItemBuilder init(ItemStack stack) {
		return new NBTItemBuilder(stack);
	}
	
	public static NBTItemBuilder init(Item item) {
		return new NBTItemBuilder(new ItemStack(item));
	}
	
	public NBTItemBuilder addTag(String key, boolean value) {
		stack.getOrCreateTag().putBoolean(key, value);
		return this;
	}
	
	public NBTItemBuilder addTag(String key, int value) {
		stack.getOrCreateTag().putInt(key, value);
		return this;
	}
	
	public NBTItemBuilder addTag(String key, String value) {
		stack.getOrCreateTag().putString(key, value);
		return this;
	}
	
	public ItemStack build() {
		return stack;
	}
}
