package com.black_dog20.jetboots.common.compat;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModCompat {

    public static ItemGroup compatItemGroup = new ItemGroup(Jetboots.MOD_ID+"_compat") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.BASE_UPGRADE.get());
        }
    };

    public static final Item.Properties ITEM_GROUP = new Item.Properties().group(compatItemGroup);

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Jetboots.MOD_ID);

    public static void register(IEventBus bus) {
        Cyclic.registerItems(ITEMS);
        MekanismTools.registerItems(ITEMS);

        ITEMS.register(bus);
    }
}
