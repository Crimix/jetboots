package com.black_dog20.jetboots.common.items.upgrades.api;

public interface IFlatValueEnergyModifier {

    /**
     * These control how other upgrades effect power draw.
     *
     * {@link IFlatValueEnergyModifier.FlatModifierType#ON_USE} A power value to draw/generate to add to the general flying power draw. Is applied when flying only.
     * {@link IFlatValueEnergyModifier.FlatModifierType#ON_HIT} A power value to draw/generate whenever the wearer is hit.
     * {@link IFlatValueEnergyModifier.FlatModifierType#ON_HURT} A power value to draw/generate whenever the wearer is hurt.
     * {@link IFlatValueEnergyModifier.FlatModifierType#ON_WALK} A power value to draw/generate while walking on ground.
     */
    enum FlatModifierType {
        ON_USE,
        ON_HIT,
        ON_HURT,
        ON_WALK,
    }

    /**
     * The flat cost modifier.
     * @return the flat cost modifier.
     */
    int getFlatEnergyModifier();

    FlatModifierType getFlatModifierType();
}
