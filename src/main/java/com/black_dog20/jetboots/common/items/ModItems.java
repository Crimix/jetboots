package com.black_dog20.jetboots.common.items;

import com.black_dog20.jetboots.Jetboots;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final Item.Properties ITEM_GROUP = new Item.Properties().group(Jetboots.itemGroup);
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Jetboots.MOD_ID);
    
    public static final RegistryObject<Item> JET_BOOTS = ITEMS.register("jetboots", ()-> new JetBootsItem(ITEM_GROUP.maxStackSize(1)));
    public static final RegistryObject<Item> TEST = ITEMS.register("test", BaseItem::new);
}
