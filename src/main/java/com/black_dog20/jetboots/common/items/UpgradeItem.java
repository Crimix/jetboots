package com.black_dog20.jetboots.common.items;

import com.black_dog20.bml.utils.translate.ITranslation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class UpgradeItem extends BaseItem {

    public enum Type {
        HELMET,
        JACKET,
        PANTS,
        BOOTS,
        ROCKET_BOOTS,
        SWORD
    }

    protected final Set<Type> types;
    protected final ITranslation info;
    protected final Function<ItemStack, ItemStack> applyUpgrade;
    protected final Function<ItemStack, Boolean> hasBeenAppliedAlready;


    public UpgradeItem(Type type, ITranslation info, Function<ItemStack, ItemStack> applyUpgrade, Function<ItemStack, Boolean> hasBeenAppliedAlready) {
        this(type, info, applyUpgrade, hasBeenAppliedAlready, ModItems.ITEM_GROUP.stacksTo(1));
    }

    public UpgradeItem(Type type, ITranslation info, Function<ItemStack, ItemStack> applyUpgrade, Function<ItemStack, Boolean> hasBeenAppliedAlready, Properties builder) {
        this(Set.of(type), info, applyUpgrade, hasBeenAppliedAlready, builder);
    }

    public UpgradeItem(Set<Type> types, ITranslation info, Function<ItemStack, ItemStack> applyUpgrade, Function<ItemStack, Boolean> hasBeenAppliedAlready) {
        this(types, info, applyUpgrade, hasBeenAppliedAlready, ModItems.ITEM_GROUP.stacksTo(1));
    }

    public UpgradeItem(Set<Type> types, ITranslation info, Function<ItemStack, ItemStack> applyUpgrade, Function<ItemStack, Boolean> hasBeenAppliedAlready, Properties builder) {
        super(builder);
        this.types = types;
        this.info = info;
        this.applyUpgrade = applyUpgrade;
        this.hasBeenAppliedAlready = hasBeenAppliedAlready;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltips, TooltipFlag flag) {
        tooltips.add(info.get());
    }

    public ItemStack applyUpgrade(ItemStack stack) {
        return applyUpgrade.apply(stack);
    }


    public Boolean hasBeenAppliedAlready(ItemStack stack) {
        return hasBeenAppliedAlready.apply(stack);
    }

    public Set<Type> getTypes() {
        return types;
    }
}
