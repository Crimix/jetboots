package com.black_dog20.jetboots.common.items.upgrades;

import com.black_dog20.bml.utils.math.MathUtil;
import com.black_dog20.jetboots.Config;
import com.black_dog20.jetboots.common.items.upgrades.api.IArmorUpgrade;
import com.black_dog20.jetboots.common.items.upgrades.api.IEnergyCostModifier;
import com.black_dog20.jetboots.common.util.TranslationHelper;
import com.google.common.collect.ImmutableSet;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

import static com.black_dog20.jetboots.common.util.TranslationHelper.*;

public class ForcefieldArmorUpgradeItem extends UpgradeItem implements IArmorUpgrade, IEnergyCostModifier {

    private final double energyCost;
    private final double damageReduction;
    private final Set<DamageSource> sources = ImmutableSet.of(DamageSource.DRAGON_BREATH, DamageSource.ON_FIRE, DamageSource.GENERIC);

    public ForcefieldArmorUpgradeItem(double damageReduction, double energyCost) {
        super(Type.ARMOR, Tooltips.FORCEFIELD_ARMOR, Tooltips.FORCEFIELD_ARMOR_INFO);
        this.energyCost = energyCost;
        this.damageReduction = damageReduction;
    }

    @Override
    public double getEnergyCostModifier() {
        return energyCost;
    }

    @Override
    public ModifierType getType() {
        return ModifierType.ON_HURT;
    }

    @Override
    public double getArmor() {
        return 0;
    }

    @Override
    public double getToughness() {
        return 0;
    }

    @Override
    public ArmorType getArmorUpgradeType() {
        return ArmorType.PERCENTAGE_REDUCTION;
    }

    @Override
    public boolean providesProtection(ItemStack boots) {
        if(getEnergyCostModifier() == 0)
            return true;

        if(Config.USE_POWER.get()) {
            IEnergyStorage energy = boots.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
            return energy != null && energy.extractEnergy((int) getEnergyCostModifier(), true) != 0;
        }
        return true;
    }

    @Override
    public double getPercentageDamageReduction() {
        return damageReduction;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        double reduction = MathUtil.clamp(damageReduction, 0.0, 1.0);
        reduction = (1.0-reduction)*100;
        tooltips.add(TranslationHelper.translate(info, reduction));
        tooltips.add(TranslationHelper.translate(Tooltips.FORCEFIELD_ARMOR_INFO_2));
    }

    @Override
    public boolean protectAgainst(DamageSource source) {
        if (sources.contains(source))
            return true;
        if(!source.isUnblockable() && !source.isMagicDamage())
            return true;

        return false;
    }

    @Override
    public double getKnockBackReduction() {
        return 0;
    }
}
