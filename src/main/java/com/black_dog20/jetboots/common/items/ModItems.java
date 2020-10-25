package com.black_dog20.jetboots.common.items;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.upgrades.ArmorUpgradeItem;
import com.black_dog20.jetboots.common.items.upgrades.BatteryUpgradeItem;
import com.black_dog20.jetboots.common.items.upgrades.ConverterUpgradeItem;
import com.black_dog20.jetboots.common.items.upgrades.ThrusterUpgradeItem;
import com.black_dog20.jetboots.common.items.upgrades.UltimateConverterUpgradeItem;
import com.black_dog20.jetboots.common.items.upgrades.UpgradeItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.black_dog20.jetboots.common.items.upgrades.api.IUpgrade.*;
import static com.black_dog20.jetboots.common.util.TranslationHelper.*;

public class ModItems {
    public static final Item.Properties ITEM_GROUP = new Item.Properties().group(Jetboots.itemGroup);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Jetboots.MOD_ID);

    public static final RegistryObject<Item> JET_BOOTS = ITEMS.register("jetboots", () -> new JetBootsItem(ITEM_GROUP.maxStackSize(1)));
    public static final RegistryObject<Item> BASE_UPGRADE = ITEMS.register("base_upgrade", () -> new BaseItem(ITEM_GROUP));
    public static final RegistryObject<Item> LEATHER_ARMOR_UPGRADE = ITEMS.register("leather_armor_upgrade", () -> new ArmorUpgradeItem(2, 0, Translations.LEATHER_ARMOR));
    public static final RegistryObject<Item> IRON_ARMOR_UPGRADE = ITEMS.register("iron_armor_upgrade", () -> new ArmorUpgradeItem(5, 0, Translations.IRON_ARMOR));
    public static final RegistryObject<Item> DIAMOND_ARMOR_UPGRADE = ITEMS.register("diamond_armor_upgrade", () -> new ArmorUpgradeItem(6, 4, Translations.DIAMOND_ARMOR));
    public static final RegistryObject<Item> ADVANCED_BATTERY_UPGRADE = ITEMS.register("advanced_battery_upgrade", () -> new BatteryUpgradeItem(3, Translations.ADVANCED_BATTERY_UPGRADE));
    public static final RegistryObject<Item> ELITE_BATTERY_UPGRADE = ITEMS.register("elite_battery_upgrade", () -> new BatteryUpgradeItem(6, Translations.ELITE_BATTERY_UPGRADE));
    public static final RegistryObject<Item> ULTIMATE_BATTERY_UPGRADE = ITEMS.register("ultimate_battery_upgrade", () -> new BatteryUpgradeItem(10, Translations.ULTIMATE_BATTERY_UPGRADE));
    public static final RegistryObject<Item> THRUSTER_UPGRADE = ITEMS.register("thruster_upgrade", () -> new ThrusterUpgradeItem(3D, 1.5D, Translations.THRUSTER_UPGRADE));
    public static final RegistryObject<Item> BASIC_CONVERTER_UPGRADE = ITEMS.register("basic_converter_upgrade", () -> new ConverterUpgradeItem(0.8D, Translations.BASIC_CONVERTER_UPGRADE));
    public static final RegistryObject<Item> ADVANCED_CONVERTER_UPGRADE = ITEMS.register("advanced_converter_upgrade", () -> new ConverterUpgradeItem(0.7D, Translations.ADVANCED_CONVERTER_UPGRADE));
    public static final RegistryObject<Item> ELITE_CONVERTER_UPGRADE = ITEMS.register("elite_converter_upgrade", () -> new ConverterUpgradeItem(0.5D, Translations.ELITE_CONVERTER_UPGRADE));
    public static final RegistryObject<Item> ULTIMATE_CONVERTER_UPGRADE = ITEMS.register("ultimate_converter_upgrade", () -> new UltimateConverterUpgradeItem(0.4D, Translations.ULTIMATE_CONVERTER_UPGRADE));
    public static final RegistryObject<Item> ENGINE_UPGRADE = ITEMS.register("engine_upgrade", () -> new UpgradeItem(Type.ENGINE, Translations.ENGINE_UPGRADE, Translations.ENGINE_UPGRADE_INFO));
    public static final RegistryObject<Item> SHOCK_ABSORBER_UPGRADE = ITEMS.register("shock_absorber_upgrade", () -> new UpgradeItem(Type.SHOCK_ABSORBER, Translations.SHOCK_ABSORBER_UPGRADE, Translations.SHOCK_ABSORBER_UPGRADE_INFO));
    public static final RegistryObject<Item> MUFFLED_UPGRADE = ITEMS.register("muffled_upgrade", () -> new UpgradeItem(Type.MUFFLED, Translations.MUFFLED_UPGRADE, Translations.MUFFLED_UPGRADE_INFO));
    public static final RegistryObject<Item> UNDERWATER_UPGRADE = ITEMS.register("underwater_upgrade", () -> new UpgradeItem(Type.UNDERWATER, Translations.UNDERWATER_UPGRADE, Translations.UNDERWATER_UPGRADE_INFO));
    public static final RegistryObject<Item> SOULBOUND_UPGRADE = ITEMS.register("soulbound_upgrade", () -> new UpgradeItem(Type.SOULBOUND, Translations.SOULBOUND_UPGRADE, Translations.SOULBOUND_UPGRADE_INFO));
    public static final RegistryObject<Item> GUARDIAN_HELMET = ITEMS.register("guardian_helmet", () -> new GuardianHelmetItem(ITEM_GROUP.maxStackSize(1)));
}
