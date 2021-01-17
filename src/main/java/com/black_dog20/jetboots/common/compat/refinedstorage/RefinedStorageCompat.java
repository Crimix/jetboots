package com.black_dog20.jetboots.common.compat.refinedstorage;

import com.black_dog20.bml.utils.item.NBTUtil;
import com.black_dog20.jetboots.common.compat.refinedstorage.events.RSCompatModEventHandler;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.items.UpgradeItem;
import com.black_dog20.jetboots.common.util.TranslationHelper;
import com.refinedmods.refinedstorage.api.IRSAPI;
import com.refinedmods.refinedstorage.api.RSAPIInject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Function;

import static com.black_dog20.jetboots.common.compat.refinedstorage.util.RefinedStorageNBTTags.*;
import static com.black_dog20.jetboots.common.util.UpgradeFunctionUtil.*;

public class RefinedStorageCompat {

    public static String MOD_ID = "refinedstorage";

    @RSAPIInject
    public static IRSAPI RSAPI;

    public static RegistryObject<Item> WIRELESS_CRAFTING_UPGRADE;
    public static RegistryObject<Item> WIRELESS_RANGE_UPGRADE;

    public static void registerItems(DeferredRegister<Item> items) {
        if (ModList.get().isLoaded(MOD_ID)) {
            WIRELESS_CRAFTING_UPGRADE = items.register("wireless_crafting_upgrade_rs", () -> new UpgradeItem(UpgradeItem.Type.HELMET, TranslationHelper.Compat.WIRELESS_CRAFTING_UPGRADE_INFO, createApplyUpgradeBoolean(TAG_HAS_WIRELESS_CRAFTING_UPGRADE), createValidateUpgradeBoolean(TAG_HAS_WIRELESS_CRAFTING_UPGRADE)));
            WIRELESS_RANGE_UPGRADE = items.register("wireless_range_upgrade_rs", () -> new UpgradeItem(UpgradeItem.Type.HELMET, TranslationHelper.Compat.WIRELESS_RANGE_UPGRADE_INFO, createRangeUpgradeFunction(), createRangeUpgradeValidateFunction(), ModItems.ITEM_GROUP.maxStackSize(4)));
        }
    }

    public static void registerEvents(IEventBus event) {
        if (ModList.get().isLoaded(MOD_ID)) {
            event.register(new RSCompatModEventHandler());
        }
    }

    private static Function<ItemStack, ItemStack> createRangeUpgradeFunction() {
        return stack -> {
            ItemStack copy = stack.copy();
            int currentValue = NBTUtil.getInt(copy, TAG_WIRELESS_RANGE_UPGRADE);
            NBTUtil.putInt(copy, TAG_WIRELESS_RANGE_UPGRADE, Math.min(currentValue + 1, 4));
            return copy;
        };
    }

    private static Function<ItemStack, Boolean> createRangeUpgradeValidateFunction() {
        return stack -> {
            ItemStack copy = stack.copy();
            int currentValue = NBTUtil.getInt(copy, TAG_WIRELESS_RANGE_UPGRADE);
            return Math.min(currentValue, 4) >= 4;
        };
    }

}
