package com.black_dog20.jetboots.common.compat;

import com.black_dog20.jetboots.Jetboots;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModCompat {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Jetboots.MOD_ID);

    public static void register(IEventBus bus) {
//        RefinedStorageCompat.registerItems(ITEMS); //TODO When RS is updated
//        RefinedStorageCompat.registerEvents(bus);
        ITEMS.register(bus);
    }
}
