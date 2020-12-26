package com.black_dog20.jetboots.common.items;

import com.black_dog20.bml.utils.translate.ITranslation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

public class UpgradeItem extends BaseItem {

    public enum Type {
        BOOTS,
        HELMET
    }

    protected final Type type;
    protected final ITranslation info;
    protected final Function<ItemStack, ItemStack> applyUpgrade;
    protected final Function<ItemStack, Boolean> hasBeenAppliedAlready;

    public UpgradeItem(Type type, ITranslation info, Function<ItemStack, ItemStack> applyUpgrade, Function<ItemStack, Boolean> hasBeenAppliedAlready) {
        super(ModItems.ITEM_GROUP.maxStackSize(1));
        this.type = type;
        this.info = info;
        this.applyUpgrade = applyUpgrade;
        this.hasBeenAppliedAlready = hasBeenAppliedAlready;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        tooltips.add(info.get());
    }

    public ItemStack applyUpgrade(ItemStack stack) {
        return applyUpgrade.apply(stack);
    }


    public Boolean hasBeenAppliedAlready(ItemStack stack) {
        return hasBeenAppliedAlready.apply(stack);
    }

    public Type getType() {
        return type;
    }
}
