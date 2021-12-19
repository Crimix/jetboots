package com.black_dog20.jetboots.common.compat.refinedstorage.grids;

import com.black_dog20.jetboots.Jetboots;
import com.refinedmods.refinedstorage.api.network.grid.GridFactoryType;
import com.refinedmods.refinedstorage.api.network.grid.IGrid;
import com.refinedmods.refinedstorage.api.network.grid.IGridFactory;
import com.refinedmods.refinedstorage.inventory.player.PlayerSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class WirelessCraftingUpgradeGridFactory implements IGridFactory {

    public static final ResourceLocation ID = new ResourceLocation(Jetboots.MOD_ID, "wireless_crafting_grid");

    @Override
    public IGrid createFromStack(Player player, ItemStack stack, PlayerSlot slot) {
        return new WirelessCraftingGrid(stack, player.level, player.getServer(), slot);
    }

    @Override
    public IGrid createFromBlock(Player player, BlockPos pos) {
        return null;
    }

    @Override
    public BlockEntity getRelevantBlockEntity(Level world, BlockPos pos) {
        return null;
    }

    @Override
    public GridFactoryType getType() {
        return GridFactoryType.STACK;
    }
}
