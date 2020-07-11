package com.black_dog20.jetboots.common.items.upgrades;

import com.black_dog20.bml.utils.translate.ITranslation;
import com.black_dog20.jetboots.common.items.upgrades.api.IThrusterUpgrade;

import static com.black_dog20.jetboots.common.util.TranslationHelper.*;

public class ThrusterUpgradeItem extends UpgradeItem implements IThrusterUpgrade {

    protected final double speed;
    protected final double speedUnderWater;

    public ThrusterUpgradeItem(double speed, double speedUnderWater, ITranslation tooltip) {
        super(Type.THRUSTER, tooltip, Translations.THRUSTER_UPGRADE_INFO);
        this.speed = speed;
        this.speedUnderWater = speedUnderWater;
    }

    public ThrusterUpgradeItem(double speed, double speedUnderWater, ITranslation tooltip, Properties builder) {
        super(Type.THRUSTER, tooltip, Translations.THRUSTER_UPGRADE_INFO, builder);
        this.speed = speed;
        this.speedUnderWater = speedUnderWater;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public double getSpeedUnderWater() {
        return speedUnderWater;
    }
}
