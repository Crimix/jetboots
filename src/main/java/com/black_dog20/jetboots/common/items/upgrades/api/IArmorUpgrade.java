package com.black_dog20.jetboots.common.items.upgrades.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public interface IArmorUpgrade extends IUpgrade {

    /**
     * The different armor types that the upgrade can be one of.
     * Default is {@link ArmorType#NORMAL}.
     *
     * {@link ArmorType#NORMAL} Behaves like normal Minecraft armor.
     * {@link ArmorType#PERCENTAGE_REDUCTION} Provides a percentage damage reduction.
     */
    enum ArmorType {
        NORMAL,
        PERCENTAGE_REDUCTION,
    }

    /***
     * The armor rating of the upgrade.
     * Use when the upgrade type is {@link ArmorType#NORMAL}
     * @return the armor rating.
     */
    double getArmor();

    /***
     * The armor toughness of the upgrade.
     * Use when the upgrade type is {@link ArmorType#NORMAL}
     * @return the armor toughness.
     */
    double getToughness();

    /***
     * The reduction in knockback the upgrade.
     * Should be a value between 0.0 and 1.0 where 1 means no reduction.
     * @return the knockback reduction.
     */
    default double getKnockBackReduction() {
        return 1;
    }

    /***
     * Returns weather or not this upgrade provides protection based on the boots.
     * This can be used to make energy armor, where the protection is only applied if there is enough energy.
     * @param boots the jetboots
     * @return returns true if the upgrade provides protection.
     */
    default boolean providesProtection(ItemStack boots) {
        return true;
    }

    /**
     * Gets the armor type of the upgrade.
     * @return the armor type
     */
    default ArmorType getArmorUpgradeType() {
        return ArmorType.NORMAL;
    }

    /**
     * Gets the percentage damage reduction.
     * This value should be between 0.0 and 1.0.
     * Override when the upgrade type is {@link ArmorType#PERCENTAGE_REDUCTION}
     * @return the damage reduction.
     */
    default double getPercentageDamageReduction() {
        return 0;
    }

    /**
     * Does the armor upgrade protect against damage source.
     * Default is all but unblockable and magic damage.
     * Override when the upgrade type is {@link ArmorType#PERCENTAGE_REDUCTION}
     * @param source the damage source.
     * @return true if the upgrade protects against the source.
     */
    default boolean protectAgainst(DamageSource source) {
        return !(source.isUnblockable() || source.isMagicDamage());
    }
}
