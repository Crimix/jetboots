package com.black_dog20.jetboots.common.util;

import com.black_dog20.bml.utils.translate.ITranslation;
import com.black_dog20.bml.utils.translate.TranslationUtil;
import com.black_dog20.jetboots.Jetboots;

public class TranslationHelper extends TranslationUtil {
    public enum Tooltips implements ITranslation {
        SHOW_UPGRADES("tooltip.item.show_upgrades"),
        OPEN_UPGRADES("tooltip.item.open_upgrades"),
        UPGRADES("tooltip.item.upgrades"),
        ARMOR_INFO("tooltip.item.armor_upgrade_info"),
        LEATHER_ARMOR("tooltip.item.leather_armor_upgrade"),
        IRON_ARMOR("tooltip.item.iron_armor_upgrade"),
        DIAMOND_ARMOR("tooltip.item.diamond_armor_upgrade"),
        FORCEFIELD_ARMOR("tooltip.item.forcefield_armor_upgrade"),
        FORCEFIELD_ARMOR_INFO("tooltip.item.forcefield_armor_upgrade_info"),
        ENGINE_UPGRADE("tooltip.item.engine_upgrade"),
        ENGINE_UPGRADE_INFO("tooltip.item.engine_upgrade.info"),
        THRUSTER_UPGRADE("tooltip.item.thruster_upgrade"),
        THRUSTER_UPGRADE_INFO("tooltip.item.thruster_upgrade.info"),
        SHOCK_ABSORBER_UPGRADE("tooltip.item.shock_absorber_upgrade"),
        SHOCK_ABSORBER_UPGRADE_INFO("tooltip.item.shock_absorber_upgrade.info"),
        UNDERWATER_UPGRADE("tooltip.item.underwater_upgrade"),
        UNDERWATER_UPGRADE_INFO("tooltip.item.underwater_upgrade.info"),
        SOULBOUND_UPGRADE("tooltip.item.soulbound_upgrade"),
        SOULBOUND_UPGRADE_INFO("tooltip.item.soulbound_upgrade.info"),
        MUFFLED_UPGRADE("tooltip.item.muffled_upgrade"),
        MUFFLED_UPGRADE_INFO("tooltip.item.muffled_upgrade.info"),
        BATTERY_UPGRADE_INFO("tooltip.item.battery_upgrade.info"),
        BASIC_BATTERY_UPGRADE("tooltip.item.basic_battery_upgrade"),
        ADVANCED_BATTERY_UPGRADE("tooltip.item.advanced_battery_upgrade"),
        ELITE_BATTERY_UPGRADE("tooltip.item.elite_battery_upgrade"),
        ULTIMATE_BATTERY_UPGRADE("tooltip.item.ultimate_battery_upgrade"),
        CONVERTER_UPGRADE_INFO("tooltip.item.converter_upgrade_info"),
        BASIC_CONVERTER_UPGRADE("tooltip.item.basic_converter_upgrade"),
        ADVANCED_CONVERTER_UPGRADE("tooltip.item.advanced_converter_upgrade"),
        ELITE_CONVERTER_UPGRADE("tooltip.item.elite_converter_upgrade"),
        ULTIMATE_CONVERTER_UPGRADE("tooltip.item.ultimate_converter_upgrade"),

        CHANGE_FLIGHT_MODE("tooltip.item.change_flight"),
        CHANGE_SPEED_MODE("tooltip.item.change_speed"),
        FLIGHT_MODE("tooltip.item.flight_mode"),
        STORED_ENERGY("tooltip.item.stored_energy"),
        SPEED_MODE("tooltip.item.speed_mode"),
        NORMAL("tooltip.item.normal"),
        BASIC("tooltip.item.basic"),
        ADVANCED("tooltip.item.advanced"),
        SUPER("tooltip.item.super"),
        ELYTRA("tooltip.item.elytra"),
        ENERGY_COST_USE("tooltip.item.energy_cost_use_info"),
        ENERGY_COST_HIT("tooltip.item.energy_cost_hit_info"),
        ENERGY_COST_HURT("tooltip.item.energy_cost_hurt_info"),
        ENERGY_COST_TIC("tooltip.item.energy_cost_tick_info"),
        FORCEFIELD_ARMOR_INFO_2("tooltip.item.forcefield_protection_info"),
        JETBOOTS_UPGRADES("tooltip.item.jetboots_upgrade_screen");

        Tooltips(String key) {
            this.modId = Jetboots.MOD_ID;
            this.key = key;
        }

        private final String modId;
        private final String key;

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public String getModId() {
            return modId;
        }
    }

    public enum Compat implements ITranslation {
        EMERALD_ARMOR_CYCLIC("tooltip.item.emerald_armor_upgrade_cyclic"),
        CRYSTAL_ARMOR_CYCLIC("tooltip.item.crystal_armor_upgrade_cyclic"),

        BRONZE_ARMOR_MEKANISM("tooltip.item.bronze_armor_upgrade_mekanism"),
        LAPIS_ARMOR_MEKANISM("tooltip.item.lapis_armor_upgrade_mekanism"),
        OSMIUM_ARMOR_MEKANISM("tooltip.item.osmium_armor_upgrade_mekanism"),
        REFINED_GLOWSTONE_ARMOR_MEKANISM("tooltip.item.refined_glowstone_armor_upgrade_mekanism"),
        REFINED_OBSIDIAN_ARMOR_MEKANISM("tooltip.item.refined_obsidian_armor_upgrade_mekanism"),
        STEEL_ARMOR_MEKANISM("tooltip.item.steel_armor_upgrade_mekanism");

        Compat(String key) {
            this.modId = Jetboots.MOD_ID;
            this.key = key;
        }

        private final String modId;
        private final String key;

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public String getModId() {
            return modId;
        }
    }
}
