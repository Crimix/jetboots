package com.black_dog20.jetboots.common.compat;

import com.black_dog20.jetboots.common.items.upgrades.ArmorUpgradeItem;
import com.black_dog20.jetboots.common.util.TranslationHelper;
import net.minecraft.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ObjectHolder;

public class Cyclic {

    public static String MOD_ID = "cyclic";

    @ObjectHolder("cyclic:crystal_boots")
    public static Item CRYSTAL_BOOTS;
    @ObjectHolder("cyclic:emerald_boots")
    public static Item EMERALD_BOOTS;
    public static RegistryObject<Item> EMERALD_ARMOR_UPGRADE_CYCLIC;
    public static RegistryObject<Item> CRYSTAL_ARMOR_UPGRADE_CYCLIC;

    public static void registerItems(DeferredRegister<Item> items) {
        if(ModList.get().isLoaded(MOD_ID)){
            EMERALD_ARMOR_UPGRADE_CYCLIC = items.register("emerald_armor_upgrade_cyclic", ()-> new ArmorUpgradeItem(2, 0, TranslationHelper.Compat.EMERALD_ARMOR_CYCLIC, ModCompat.ITEM_GROUP));
            CRYSTAL_ARMOR_UPGRADE_CYCLIC = items.register("crystal_armor_upgrade_cyclic", ()-> new ArmorUpgradeItem(2, 0, TranslationHelper.Compat.CRYSTAL_ARMOR_CYCLIC, ModCompat.ITEM_GROUP));
        }
    }
}
