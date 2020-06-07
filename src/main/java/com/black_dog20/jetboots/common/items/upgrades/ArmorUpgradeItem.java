package com.black_dog20.jetboots.common.items.upgrades;

import com.black_dog20.bml.utils.translate.ITranslation;
import com.black_dog20.jetboots.common.items.upgrades.api.IArmorUpgrade;

import static com.black_dog20.jetboots.common.util.TranslationHelper.*;

public class ArmorUpgradeItem extends UpgradeItem implements IArmorUpgrade {

    protected final double armor;
    protected final double toughness;

    public ArmorUpgradeItem(double armor, double toughness, ITranslation tooltip) {
        super(Type.ARMOR, tooltip, Tooltips.ARMOR_INFO);
        this.armor = armor;
        this.toughness = toughness;
    }

    public ArmorUpgradeItem(double armor, double toughness, ITranslation tooltip, Properties builder) {
        super(Type.ARMOR, tooltip, Tooltips.ARMOR_INFO, builder);
        this.armor = armor;
        this.toughness = toughness;
    }

    @Override
    public double getArmor() {
        return armor;
    }

    @Override
    public double getToughness() {
        return toughness;
    }

    @Override
    public ArmorType getArmorUpgradeType() {
        return ArmorType.NORMAL;
    }
}
