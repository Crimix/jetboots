package com.black_dog20.jetboots.common.util;

import com.black_dog20.bml.utils.text.TextComponentBuilder;
import com.black_dog20.bml.utils.translate.ITranslation;
import com.black_dog20.bml.utils.translate.TranslationUtil;
import com.black_dog20.jetboots.Jetboots;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public class TranslationHelper extends TranslationUtil {
    public enum Translations implements ITranslation {
        SHOW_MORE_INFO("tooltip.item.show_upgrades"),
        OPEN_CONTAINER("tooltip.item.open_upgrades"),
        ENGINE_UPGRADE("tooltip.item.engine_upgrade"),
        ENGINE_UPGRADE_INFO("tooltip.item.engine_upgrade.info"),
        THRUSTER_UPGRADE("tooltip.item.thruster_upgrade"),
        THRUSTER_UPGRADE_INFO("tooltip.item.thruster_upgrade.info"),
        SHOCK_ABSORBER_UPGRADE("tooltip.item.shock_absorber_upgrade"),
        SHOCK_ABSORBER_UPGRADE_INFO("tooltip.item.shock_absorber_upgrade.info"),
        UNDERWATER_UPGRADE("tooltip.item.underwater_upgrade"),
        UNDERWATER_UPGRADE_INFO("tooltip.item.underwater_upgrade.info"),
        MUFFLED_UPGRADE("tooltip.item.muffled_upgrade"),
        MUFFLED_UPGRADE_INFO("tooltip.item.muffled_upgrade.info"),

        CHANGE_FLIGHT_MODE("tooltip.item.change_flight_mode"),
        CHANGE_SPEED_MODE("tooltip.item.change_speed_mode"),
        FLIGHT_MODE("tooltip.item.flight_mode"),
        STORED_ENERGY("tooltip.item.stored_energy"),
        SPEED_MODE("tooltip.item.speed_mode"),
        NORMAL("tooltip.item.normal"),
        SUPER("tooltip.item.super"),
        ELYTRA("tooltip.item.elytra"),
        SOULBOUND("tooltip.item.soulbound"),
        FLYING_ENERGY("tooltip.item.energy_flying"),
        JETBOOTS_UPGRADES("tooltip.item.jetboots_upgrade_screen"),
        CHANGE_HELMET_MODE("tooltip.item.change_helmet_mode"),
        CHANGE_HELMET_NIGHT_VISION("tooltip.item.change_helmet_night_vision"),
        HELMET_INFO("tooltip.item.helmet_info"),

        NOT_LEVELABLE("tooltip.item.not_able_to_level"),
        MAX_LEVEL("tooltip.item.max_level"),
        LEVEL("tooltip.item.level"),
        PROGRESS("tooltip.item.level_progress"),
        SOULBOUND_ACHIEVED("tooltip.item.soulbound_achieved"),

        HELMET_MODE("msg.helmet_mode"),
        HELMET_NIGHT_VISION("msg.helmet_night_vision"),
        POWER_LOW("msg.power_low"),
        ON("msg.on"),
        OFF("msg.off"),
        MATERIALIZED("msg.materialized"),
        DEMATERIALIZED("msg.dematerialized"),

        KEY_CATEGORY("key.category"),
        ;

        Translations(String key) {
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
        WIRELESS_CRAFTING_USE("tooltip.item.refinedstorage_use_wireless_crafting"),
        WIRELESS_CRAFTING_UPGRADE("tooltip.item.refinedstorage_wireless_crafting_upgrade"),
        WIRELESS_CRAFTING_UPGRADE_INFO("tooltip.item.refinedstorage_wireless_crafting_upgrade_info"),
        WIRELESS_CRAFTING_UPGRADE_INFO_2("tooltip.item.refinedstorage_wireless_crafting_upgrade_info_2"),
        WIRELESS_RANGE_UPGRADE("tooltip.item.refinedstorage_wireless_range_upgrade"),
        WIRELESS_RANGE_UPGRADE_INFO("tooltip.item.refinedstorage_wireless_range_upgrade_info"),
        WIRELESS_RANGE_UPGRADE_INFO_2("tooltip.item.refinedstorage_wireless_range_upgrade_info_2"),
        WIRELESS_CRAFTING_UPGRADE_NOT_INSTALLED("tooltip.item.refinedstorage_wireless_crafting_upgrade_not_installed"),
        NETWORK_LINKED("tooltip.item.refinedstorage_network_linked"),
        NOT_LINKED_TO_NETWORK("tooltip.item.refinedstorage_not_linked"),

        RS_OUT_OF_RANGE("misc.refinedstorage", "network_item.out_of_range"),
        RS_NETWORK_NOT_FOUND("misc.refinedstorage","network_item.not_found"),

        ;

        Compat(String key) {
            this.modId = Jetboots.MOD_ID;
            this.key = key;
        }

        Compat(String modId, String key) {
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

    public static ITextComponent getLevelProgress(ItemStack stack) {
        return getLevelProgress(stack, TextFormatting.GRAY);
    }

    public static ITextComponent getLevelProgress(ItemStack stack, TextFormatting baseColor) {
        return TextComponentBuilder.of(Translations.LEVEL)
                .format(baseColor)
                .with(":")
                .format(baseColor)
                .space()
                .with(LevelProperties.getXpToNextLevel(stack))
                .build();
    }
}
