package com.black_dog20.jetboots.common.items.upgrades.api;

public interface IThrusterUpgrade extends IUpgrade {

    /**
     * The speed of this thruster upgrade.
     * Be careful not to use a too large value or you will lag or crash the player.
     * @return the speed in air.
     */
    double getSpeed();

    /**
     * The speed of this thruster upgrade when under water.
     * Be careful not to use a too large value or you will lag or crash the player.
     * @return the speed when under water.
     */
    double getSpeedUnderWater();
}
