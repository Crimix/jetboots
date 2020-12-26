package com.black_dog20.jetboots.common.items;

import com.black_dog20.jetboots.Jetboots;
import com.black_dog20.jetboots.common.items.equipment.GuardianHelmetItem;
import com.black_dog20.jetboots.common.items.equipment.GuardianJacketItem;
import com.black_dog20.jetboots.common.items.equipment.GuardianPantsItem;
import com.black_dog20.jetboots.common.items.equipment.GuardianSwordItem;
import com.black_dog20.jetboots.common.items.equipment.JetBootsItem;
import com.black_dog20.jetboots.common.util.NBTTags;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.black_dog20.jetboots.common.util.TranslationHelper.*;
import static com.black_dog20.jetboots.common.util.UpgradeFunctionUtil.*;

public class ModItems {
    public static final Item.Properties ITEM_GROUP = new Item.Properties().group(Jetboots.itemGroup);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Jetboots.MOD_ID);

    public static final RegistryObject<Item> JET_BOOTS = ITEMS.register("jetboots", () -> new JetBootsItem(ITEM_GROUP.maxStackSize(1)));
    public static final RegistryObject<Item> OBSIDIAN_INFUSED_GOLD = ITEMS.register("obsidian_infused_gold", () -> new BaseItem(ITEM_GROUP));
    public static final RegistryObject<Item> ARMOR_CORE = ITEMS.register("armor_core", () -> new BaseItem(ITEM_GROUP));
    public static final RegistryObject<Item> THRUSTER_UPGRADE = ITEMS.register("thruster_upgrade", () -> new UpgradeItem(UpgradeItem.Type.BOOTS, Translations.THRUSTER_UPGRADE_INFO, createApplyUpgradeBoolean(NBTTags.TAG_HAS_THRUSTER_UPGRADE), createValidateUpgradeBoolean(NBTTags.TAG_HAS_THRUSTER_UPGRADE)));
    public static final RegistryObject<Item> ENGINE_UPGRADE = ITEMS.register("engine_upgrade", () -> new UpgradeItem(UpgradeItem.Type.BOOTS, Translations.ENGINE_UPGRADE_INFO, createApplyUpgradeBoolean(NBTTags.TAG_HAS_ENGINE_UPGRADE), createValidateUpgradeBoolean(NBTTags.TAG_HAS_ENGINE_UPGRADE)));
    public static final RegistryObject<Item> SHOCK_ABSORBER_UPGRADE = ITEMS.register("shock_absorber_upgrade", () -> new UpgradeItem(UpgradeItem.Type.BOOTS, Translations.SHOCK_ABSORBER_UPGRADE_INFO, createApplyUpgradeBoolean(NBTTags.TAG_HAS_SHOCK_ABSORBER_UPGRADE), createValidateUpgradeBoolean(NBTTags.TAG_HAS_SHOCK_ABSORBER_UPGRADE)));
    public static final RegistryObject<Item> MUFFLED_UPGRADE = ITEMS.register("muffled_upgrade", () -> new UpgradeItem(UpgradeItem.Type.BOOTS, Translations.MUFFLED_UPGRADE_INFO, createMuffledApplyUpgradeFunc(), createValidateUpgradeBoolean(NBTTags.TAG_HAS_MUFFLED_UPGRADE)));
    public static final RegistryObject<Item> UNDERWATER_UPGRADE = ITEMS.register("underwater_upgrade", () -> new UpgradeItem(UpgradeItem.Type.BOOTS, Translations.UNDERWATER_UPGRADE_INFO, createApplyUpgradeBoolean(NBTTags.TAG_HAS_UNDERWATER_UPGRADE), createValidateUpgradeBoolean(NBTTags.TAG_HAS_UNDERWATER_UPGRADE)));
    public static final RegistryObject<Item> GUARDIAN_HELMET = ITEMS.register("guardian_helmet", () -> new GuardianHelmetItem(ITEM_GROUP.maxStackSize(1)));
    public static final RegistryObject<Item> GUARDIAN_JACKET = ITEMS.register("guardian_jacket", () -> new GuardianJacketItem(ITEM_GROUP.maxStackSize(1)));
    public static final RegistryObject<Item> GUARDIAN_PANTS = ITEMS.register("guardian_pants", () -> new GuardianPantsItem(ITEM_GROUP.maxStackSize(1)));
    public static final RegistryObject<Item> GUARDIAN_SWORD = ITEMS.register("guardian_sword", () -> new GuardianSwordItem(ITEM_GROUP.maxStackSize(1)));
}
