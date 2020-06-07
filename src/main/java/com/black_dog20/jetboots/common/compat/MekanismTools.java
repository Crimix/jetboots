package com.black_dog20.jetboots.common.compat;

import com.black_dog20.jetboots.common.items.upgrades.ArmorUpgradeItem;
import com.black_dog20.jetboots.common.util.TranslationHelper;
import net.minecraft.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class MekanismTools {

    public static String MOD_ID = "mekanismtools";

    public static RegistryObject<Item> BRONZE_ARMOR_UPGRADE_MEKANISM;
    public static RegistryObject<Item> LAPIS_ARMOR_UPGRADE_MEKANISM;
    public static RegistryObject<Item> OSMIUM_ARMOR_UPGRADE_MEKANISM;
    public static RegistryObject<Item> REFINED_GLOWSTONE_ARMOR_UPGRADE_MEKANISM;
    public static RegistryObject<Item> REFINED_OBSIDIAN_ARMOR_UPGRADE_MEKANISM;
    public static RegistryObject<Item> STEEL_ARMOR_UPGRADE_MEKANISM;

    public static void registerItems(DeferredRegister<Item> items) {
        if(ModList.get().isLoaded(MOD_ID)){
            BRONZE_ARMOR_UPGRADE_MEKANISM = items.register("bronze_armor_upgrade_mekanism", ()-> new ArmorUpgradeItem(2, 0, TranslationHelper.Compat.BRONZE_ARMOR_MEKANISM, ModCompat.ITEM_GROUP));
            LAPIS_ARMOR_UPGRADE_MEKANISM = items.register("lapis_armor_upgrade_mekanism", ()-> new ArmorUpgradeItem(2, 0, TranslationHelper.Compat.LAPIS_ARMOR_MEKANISM, ModCompat.ITEM_GROUP));
            OSMIUM_ARMOR_UPGRADE_MEKANISM = items.register("osmium_armor_upgrade_mekanism", ()-> new ArmorUpgradeItem(2, 0, TranslationHelper.Compat.OSMIUM_ARMOR_MEKANISM, ModCompat.ITEM_GROUP));
            REFINED_GLOWSTONE_ARMOR_UPGRADE_MEKANISM = items.register("refined_glowstone_armor_upgrade_mekanism", ()-> new ArmorUpgradeItem(2, 0, TranslationHelper.Compat.REFINED_GLOWSTONE_ARMOR_MEKANISM, ModCompat.ITEM_GROUP));
            REFINED_OBSIDIAN_ARMOR_UPGRADE_MEKANISM = items.register("refined_obsidian_armor_upgrade_mekanism", ()-> new ArmorUpgradeItem(2, 0, TranslationHelper.Compat.REFINED_OBSIDIAN_ARMOR_MEKANISM, ModCompat.ITEM_GROUP));
            STEEL_ARMOR_UPGRADE_MEKANISM = items.register("steel_armor_upgrade_mekanism", ()-> new ArmorUpgradeItem(2, 0, TranslationHelper.Compat.STEEL_ARMOR_MEKANISM, ModCompat.ITEM_GROUP));
        }
    }
}
