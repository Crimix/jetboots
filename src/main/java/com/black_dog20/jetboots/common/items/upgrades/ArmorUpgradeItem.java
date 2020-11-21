package com.black_dog20.jetboots.common.items.upgrades;

import com.black_dog20.bml.utils.translate.ITranslation;
import com.black_dog20.jetboots.common.items.upgrades.api.IArmorUpgrade;
import com.black_dog20.jetboots.common.util.TranslationHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static com.black_dog20.jetboots.common.util.TranslationHelper.*;

public class ArmorUpgradeItem extends UpgradeItem implements IArmorUpgrade {

    protected final double armor;
    protected final double toughness;
    protected final double knockbackResistance;

    public ArmorUpgradeItem(double armor, double toughness, double knockbackResistance, ITranslation tooltip) {
        super(Type.ARMOR, tooltip, Translations.ARMOR_INFO);
        this.armor = armor;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
    }

    public ArmorUpgradeItem(double armor, double toughness, double knockbackResistance, ITranslation tooltip, Properties builder) {
        super(Type.ARMOR, tooltip, Translations.ARMOR_INFO, builder);
        this.armor = armor;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
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
    public double getKnockbackResistance() {
        return knockbackResistance;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltips, flag);

        tooltips.add(TranslationHelper.translate(Translations.ARMOR_VALUE, (int) getArmor()));
        if (getToughness() > 0)
            tooltips.add(TranslationHelper.translate(Translations.TOUGHNESS_VALUE, (int) getToughness()));
        if (getToughness() > 0)
            tooltips.add(TranslationHelper.translate(Translations.KNOCKBACK_RESISTANCE_VALUE, ((int) getKnockbackResistance()) * 10));
    }
}
