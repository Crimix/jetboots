package com.black_dog20.jetboots.common.util;

import com.black_dog20.bml.utils.translate.ITranslation;
import com.black_dog20.bml.utils.translate.TranslationUtil;
import com.black_dog20.jetboots.Jetboots;

public class TranslationHelper extends TranslationUtil {
    public enum Tooltips implements ITranslation {
        SHOW_UPGRADES(Jetboots.MOD_ID,"tooltip.item.show_upgrades"),
        UPGRADES(Jetboots.MOD_ID,"tooltip.item.upgrades"),
        CUSTOM_ARMOR(Jetboots.MOD_ID,"tooltip.item.custom_armor_upgrade"),
        LEATHER_ARMOR(Jetboots.MOD_ID,"tooltip.item.leather_armor_upgrade"),
        IRON_ARMOR(Jetboots.MOD_ID,"tooltip.item.iron_armor_upgrade"),
        DIAMOND_ARMOR(Jetboots.MOD_ID,"tooltip.item.diamond_armor_upgrade"),
        ENGINE_UPGRADE(Jetboots.MOD_ID,"tooltip.item.engine_upgrade"),
        THRUSTER_UPGRADE(Jetboots.MOD_ID,"tooltip.item.thruster_upgrade"),
        SHOCK_ABSORBER_UPGRADE(Jetboots.MOD_ID,"tooltip.item.shock_absorber_upgrade"),
        UNDERWATER_UPGRADE(Jetboots.MOD_ID,"tooltip.item.underwater_upgrade"),
        SOULBOUND_UPGRADE(Jetboots.MOD_ID,"tooltip.item.soulbound_upgrade"),
        MUFFLED_UPGRADE(Jetboots.MOD_ID,"tooltip.item.muffled_upgrade"),
        BATTERY_UPGRADE(Jetboots.MOD_ID,"tooltip.item.battery_upgrade"),

        CHANGE_FLIGHT_MODE(Jetboots.MOD_ID,"tooltip.item.change_flight"),
        CHANGE_SPEED_MODE(Jetboots.MOD_ID,"tooltip.item.change_speed"),
        FLIGHT_MODE(Jetboots.MOD_ID,"tooltip.item.flight_mode"),
        STORED_ENERGY(Jetboots.MOD_ID,"tooltip.item.stored_energy"),
        SPEED_MODE(Jetboots.MOD_ID,"tooltip.item.speed_mode"),
        NORMAL(Jetboots.MOD_ID,"tooltip.item.normal"),
        BASIC(Jetboots.MOD_ID,"tooltip.item.basic"),
        ADVANCED(Jetboots.MOD_ID,"tooltip.item.advanced"),
        SUPER(Jetboots.MOD_ID,"tooltip.item.super"),
        ELYTRA(Jetboots.MOD_ID,"tooltip.item.elytra");

        Tooltips(String modId, String key) {
            this.modId = modId;
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
