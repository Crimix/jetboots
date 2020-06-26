package com.black_dog20.jetboots.common.items.upgrades;

import com.black_dog20.bml.utils.translate.ITranslation;
import com.black_dog20.jetboots.common.items.upgrades.api.IConverterUpgrade;

import static com.black_dog20.jetboots.common.util.TranslationHelper.*;

public class ConverterUpgradeItem extends UpgradeItem implements IConverterUpgrade {

    protected final double energyModifier;

    public ConverterUpgradeItem(double energyModifier, ITranslation tooltip) {
        super(Type.CONVERTER, tooltip, Tooltips.CONVERTER_UPGRADE_INFO);
        this.energyModifier = energyModifier;
    }

    public ConverterUpgradeItem(double energyModifier, ITranslation tooltip, Properties builder) {
        super(Type.CONVERTER, tooltip, Tooltips.CONVERTER_UPGRADE_INFO, builder);
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
}
