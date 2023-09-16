package com.black_dog20.jetboots.common.compat;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.compat.refinedstorage.RefinedStorageCompat;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.util.TranslationHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModCompat {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Jetboots.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Jetboots.MOD_ID);

    public static void register(IEventBus bus) {
        RefinedStorageCompat.registerItems(ITEMS);
        RefinedStorageCompat.registerEvents(bus);
        ITEMS.register(bus);
        CREATIVE_MODE_TABS.register(bus);
    }

    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("jetboots_compat_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(ModItems.CREATIVE_TAB.getKey())
            .icon(() -> ModItems.JET_BOOTS.get().getDefaultInstance())
            .title(TranslationHelper.Translations.ITEM_CATEGORY_EXTRAS.get(ChatFormatting.RESET))
            .displayItems((parameters, output) -> {
                for (RegistryObject<Item> item : ITEMS.getEntries()) {
                    output.accept(item.get());
                }
            }).build());
}
