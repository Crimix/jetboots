package com.black_dog20.jetboots.common.items.upgrades;

import com.black_dog20.bml.utils.translate.ITranslation;
import com.black_dog20.jetboots.common.items.upgrades.api.IConverterUpgrade;
import com.black_dog20.jetboots.common.items.upgrades.api.IFlatValueEnergyModifier;

import static com.black_dog20.jetboots.common.util.TranslationHelper.*;

public class UltimateConverterUpgradeItem extends UpgradeItem implements IConverterUpgrade, IFlatValueEnergyModifier {

    protected final double energyModifier;

    public UltimateConverterUpgradeItem(double energyModifier, ITranslation tooltip) {
        super(Type.CONVERTER, tooltip, Translations.CONVERTER_UPGRADE_INFO);
        this.energyModifier = energyModifier;
    }

    public UltimateConverterUpgradeItem(double energyModifier, ITranslation tooltip, Properties builder) {
        super(Type.CONVERTER, tooltip, Translations.CONVERTER_UPGRADE_INFO, builder);
        this.energyModifier = energyModifier;
    }

    @Override
    public double getPercentageEnergyModifier() {
        return energyModifier;
    }

    @Override
    public PercentageModifierType getPercentageModifierType() {
        return PercentageModifierType.ON_USE;
    }

    @Override
    public int getFlatEnergyModifier() {
        return 10;
    }

    @Override
    public FlatModifierType getFlatModifierType() {
        return FlatModifierType.ON_WALK;
    }
}
