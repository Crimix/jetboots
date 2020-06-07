package com.black_dog20.jetboots.common.items.upgrades;

import com.black_dog20.bml.utils.translate.ITranslation;
import com.black_dog20.jetboots.common.items.BaseItem;
import com.black_dog20.jetboots.common.items.ModItems;
import com.black_dog20.jetboots.common.items.upgrades.api.IUpgrade;
import com.black_dog20.jetboots.common.util.TranslationHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class UpgradeItem extends BaseItem implements IUpgrade {

    protected final Type upgradeType;
    protected final ITranslation tooltip;
    protected final ITranslation info;

    public UpgradeItem(Type upgradeType, ITranslation tooltip, ITranslation info) {
        this(upgradeType, tooltip, info, ModItems.ITEM_GROUP.maxStackSize(1));
    }

    public UpgradeItem(Type upgradeType, ITranslation tooltip, ITranslation info, Properties builder) {
        super(builder.maxStackSize(1));
        this.upgradeType = upgradeType;
        this.tooltip = tooltip;
        this.info = info;
    }

    @Override
    public Type getUpgradeType() {
        return upgradeType;
    }

    @Override
    public ITextComponent getTooltip() {
        return TranslationHelper.translate(tooltip);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        tooltips.add(TranslationHelper.translate(info));
    }
}
