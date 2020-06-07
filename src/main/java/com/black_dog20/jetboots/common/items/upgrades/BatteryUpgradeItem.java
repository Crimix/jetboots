package com.black_dog20.jetboots.common.items.upgrades;

import com.black_dog20.bml.utils.translate.ITranslation;
import com.black_dog20.jetboots.common.items.upgrades.api.IBatteryUpgrade;
import com.black_dog20.jetboots.common.util.TranslationHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static com.black_dog20.jetboots.common.util.TranslationHelper.*;

public class BatteryUpgradeItem extends UpgradeItem implements IBatteryUpgrade {

    protected final int multiplier;

    public BatteryUpgradeItem(int multiplier, ITranslation tooltip) {
        super(Type.BATTERY, tooltip, Tooltips.BATTERY_UPGRADE_INFO);
        this.multiplier = multiplier;
    }

    public BatteryUpgradeItem(int multiplier, ITranslation tooltip, Properties builder) {
        super(Type.BATTERY, tooltip, Tooltips.BATTERY_UPGRADE_INFO, builder);
        this.multiplier = multiplier;
    }

    @Override
    public int getCapacityMultiplier() {
        return multiplier;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(TranslationHelper.translate(info, multiplier));
    }
}
