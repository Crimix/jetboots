package com.black_dog20.jetboots.common.items.upgrades.api;

import net.minecraft.util.text.ITextComponent;

public interface IUpgrade {

    /**
     * The different types upgrades can be.
     * Not all have implementation details.
     * Such as {@link Type#SOULBOUND} which when presents keeps the boots from being dropped upon death.
     */
    enum Type {
        ARMOR,
        BATTERY,
        ENGINE,
        THRUSTER,
        SHOCK_ABSORBER,
        MUFFLED,
        UNDERWATER,
        CONVERTER,
        SOULBOUND;
    }

    /**
     * Gets this upgrade's type.
     *
     * @return the upgrade type.
     */
    Type getUpgradeType();

    /**
     * Gets this upgrade's tooltip.
     *
     * @return the tooltip.
     */
    ITextComponent getTooltip();

}
