package com.black_dog20.jetboots.common.items;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.upgrades.ArmorUpgradeItem;
import com.black_dog20.jetboots.common.items.upgrades.BatteryUpgradeItem;
import com.black_dog20.jetboots.common.items.upgrades.ConverterUpgradeItem;
import com.black_dog20.jetboots.common.items.upgrades.ForcefieldArmorUpgradeItem;
import com.black_dog20.jetboots.common.items.upgrades.ThrusterUpgradeItem;
import com.black_dog20.jetboots.common.items.upgrades.UpgradeItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.black_dog20.jetboots.common.items.upgrades.api.IUpgrade.*;
import static com.black_dog20.jetboots.common.util.TranslationHelper.*;

public class ModItems {
    public static final Item.Properties ITEM_GROUP = new Item.Properties().group(Jetboots.itemGroup);
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Jetboots.MOD_ID);
    
    public static final RegistryObject<Item> JET_BOOTS = ITEMS.register("jetboots", ()-> new JetBootsItem(ITEM_GROUP.maxStackSize(1)));
    public static final RegistryObject<Item> BASE_UPGRADE = ITEMS.register("base_upgrade", ()-> new BaseItem(ITEM_GROUP));
    public static final RegistryObject<Item> LEATHER_ARMOR_UPGRADE = ITEMS.register("leather_armor_upgrade", ()-> new ArmorUpgradeItem(2, 0, Tooltips.LEATHER_ARMOR));
    public static final RegistryObject<Item> IRON_ARMOR_UPGRADE = ITEMS.register("iron_armor_upgrade", ()-> new ArmorUpgradeItem(5, 0, Tooltips.IRON_ARMOR));
    public static final RegistryObject<Item> DIAMOND_ARMOR_UPGRADE = ITEMS.register("diamond_armor_upgrade", ()-> new ArmorUpgradeItem(6, 4, Tooltips.DIAMOND_ARMOR));
    public static final RegistryObject<Item> FORCEFIELD_ARMOR_UPGRADE = ITEMS.register("forcefield_armor_upgrade", ()-> new ForcefieldArmorUpgradeItem(0.1, 1000));
    public static final RegistryObject<Item> ADVANCED_BATTERY_UPGRADE = ITEMS.register("advanced_battery_upgrade", ()-> new BatteryUpgradeItem(3, Tooltips.ADVANCED_BATTERY_UPGRADE));
    public static final RegistryObject<Item> ELITE_BATTERY_UPGRADE = ITEMS.register("elite_battery_upgrade", ()-> new BatteryUpgradeItem(6, Tooltips.ELITE_BATTERY_UPGRADE));
    public static final RegistryObject<Item> ULTIMATE_BATTERY_UPGRADE = ITEMS.register("ultimate_battery_upgrade", ()-> new BatteryUpgradeItem(10, Tooltips.ULTIMATE_BATTERY_UPGRADE));
    public static final RegistryObject<Item> THRUSTER_UPGRADE = ITEMS.register("thruster_upgrade", ()-> new ThrusterUpgradeItem(3D, 1.5D, Tooltips.THRUSTER_UPGRADE));
    public static final RegistryObject<Item> BASIC_CONVERTER_UPGRADE = ITEMS.register("basic_converter_upgrade", ()-> new ConverterUpgradeItem(1.1D, Tooltips.BASIC_CONVERTER_UPGRADE));
    public static final RegistryObject<Item> ADVANCED_CONVERTER_UPGRADE = ITEMS.register("advanced_converter_upgrade", ()-> new ConverterUpgradeItem(1.2D, Tooltips.ADVANCED_CONVERTER_UPGRADE));
    public static final RegistryObject<Item> ELITE_CONVERTER_UPGRADE = ITEMS.register("elite_converter_upgrade", ()-> new ConverterUpgradeItem(1.35D, Tooltips.ELITE_CONVERTER_UPGRADE));
    public static final RegistryObject<Item> ULTIMATE_CONVERTER_UPGRADE = ITEMS.register("ultimate_converter_upgrade", ()-> new ConverterUpgradeItem(1.5D, Tooltips.ULTIMATE_CONVERTER_UPGRADE));
    public static final RegistryObject<Item> ENGINE_UPGRADE = ITEMS.register("engine_upgrade", ()-> new UpgradeItem(Type.ENGINE, Tooltips.ENGINE_UPGRADE, Tooltips.ENGINE_UPGRADE_INFO));
    public static final RegistryObject<Item> SHOCK_ABSORBER_UPGRADE = ITEMS.register("shock_absorber_upgrade", ()-> new UpgradeItem(Type.SHOCK_ABSORBER, Tooltips.SHOCK_ABSORBER_UPGRADE, Tooltips.SHOCK_ABSORBER_UPGRADE_INFO));
    public static final RegistryObject<Item> MUFFLED_UPGRADE = ITEMS.register("muffled_upgrade", ()-> new UpgradeItem(Type.MUFFLED, Tooltips.MUFFLED_UPGRADE, Tooltips.MUFFLED_UPGRADE_INFO));
    public static final RegistryObject<Item> UNDERWATER_UPGRADE = ITEMS.register("underwater_upgrade", ()-> new UpgradeItem(Type.UNDERWATER, Tooltips.UNDERWATER_UPGRADE, Tooltips.UNDERWATER_UPGRADE_INFO));
    public static final RegistryObject<Item> SOULBOUND_UPGRADE = ITEMS.register("soulbound_upgrade", ()-> new UpgradeItem(Type.SOULBOUND, Tooltips.SOULBOUND_UPGRADE, Tooltips.SOULBOUND_UPGRADE_INFO));
}
