package com.black_dog20.jetboots.common.items.upgrades.api;

public interface IPercentageValueEnergyModifier {

    /**
     * These control how other upgrades effect power draw.
     * <p>
     * {@link IPercentageValueEnergyModifier.PercentageModifierType#ON_USE} A multiplier to add to the general flying power draw. Is applied when flying only.
     */
    enum PercentageModifierType {
        ON_USE,
    }

    /**
     * The cost multiplier.
     *
     * @return the cost multiplier.
     */
    double getPercentageEnergyModifier();

    /**
     * The cost multiplier as a display value.
     *
     * @return the cost multiplier as a display value.
     */
    default double getPercentageEnergyModifierDisplayValue() {
        double value = Math.max(getPercentageEnergyModifier(), 0);
        if (value < 1.0)
            return 1.0 - value;
        else
            return value;
    }

    PercentageModifierType getPercentageModifierType();
}
