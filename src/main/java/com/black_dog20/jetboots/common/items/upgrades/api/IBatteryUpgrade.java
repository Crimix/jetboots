package com.black_dog20.jetboots.common.items.upgrades.api;

public interface IBatteryUpgrade extends IUpgrade {

    /**
     * The power capacity multiplier of this upgrade.
     *
     * @return the multiplier.
     */
    int getCapacityMultiplier();

}
