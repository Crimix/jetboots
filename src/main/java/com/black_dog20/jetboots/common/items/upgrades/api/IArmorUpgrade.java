package com.black_dog20.jetboots.common.items.upgrades.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public interface IArmorUpgrade extends IUpgrade {

    /***
     * The armor rating of the upgrade.
     * @return the armor rating.
     */
    double getArmor();

    /***
     * The armor toughness of the upgrade.
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
     * Does the armor upgrade protect against damage source.
     * Default is all but unblockable and magic damage.
     *
     * @param source the damage source.
     * @return true if the upgrade protects against the source.
     */
    default boolean protectAgainst(DamageSource source) {
        return !(source.isUnblockable() || source.isMagicDamage());
    }
}
