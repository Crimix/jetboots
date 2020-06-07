package com.black_dog20.jetboots.common.items.upgrades.api;

import com.black_dog20.jetboots.Config;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public interface IEnergyCostModifier {

    /**
     * These control how other upgrades effect power draw.
     *
     * {@link IEnergyCostModifier.ModifierType#ON_USE} A multiplier to add to the general flying power draw. Is applied when flying only.
     * {@link IEnergyCostModifier.ModifierType#ON_HIT} A power value to draw whenever the wearer is hit.
     * {@link IEnergyCostModifier.ModifierType#ON_HURT} A power value to draw whenever the wearer is hurt.
     * {@link IEnergyCostModifier.ModifierType#ON_TICK} A power value to draw every tick while the boots are worn.
     */
    enum ModifierType {
        ON_USE,
        ON_HIT,
        ON_HURT,
        ON_TICK,
    }

    /**
     * The cost modifier, can be both a multiple or a flat value.
     * See {@link IEnergyCostModifier.ModifierType} for which value to put with the different types.
     * @return the cost modifier.
     */
    double getEnergyCostModifier();

    /**
     * The modifier type which determines when and how the energy cost should be drawn for this upgrade.
     * @return the modifer type.
     */
    ModifierType getType();

    /**
     * Default draw power method please do not override this or you will probably break your own upgrade.
     * @param boots the jetboots to draw power from.
     */
    default void drawPower(ItemStack boots) {
        if(!getType().equals(ModifierType.ON_HURT) || getEnergyCostModifier() == 0)
            return;

        if (Config.USE_POWER.get()) {
            IEnergyStorage energy = boots.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
            energy.extractEnergy((int) getEnergyCostModifier(), false);
        }
    }
}
