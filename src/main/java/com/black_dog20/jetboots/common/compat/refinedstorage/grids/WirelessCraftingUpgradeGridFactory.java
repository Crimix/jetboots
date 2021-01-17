package com.black_dog20.jetboots.common.compat.refinedstorage.grids;

import com.black_dog20.jetboots.Jetboots;
import com.refinedmods.refinedstorage.api.network.grid.GridFactoryType;
import com.refinedmods.refinedstorage.api.network.grid.IGrid;
import com.refinedmods.refinedstorage.api.network.grid.IGridFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WirelessCraftingUpgradeGridFactory implements IGridFactory {

    public static final ResourceLocation ID = new ResourceLocation(Jetboots.MOD_ID, "wireless_crafting_grid");

    @Override
    public IGrid createFromStack(PlayerEntity player, ItemStack stack, int slotId) {
        return new WirelessCraftingGrid(stack, player.world, player.getServer(), slotId);
    }

    @Override
    public IGrid createFromBlock(PlayerEntity player, BlockPos pos) {
        return null;
    }

    @Override
    public TileEntity getRelevantTile(World world, BlockPos pos) {
        return null;
    }

    @Override
    public GridFactoryType getType() {
        return GridFactoryType.STACK;
    }
}
