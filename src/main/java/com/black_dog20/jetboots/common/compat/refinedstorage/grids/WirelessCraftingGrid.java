package com.black_dog20.jetboots.common.compat.refinedstorage.grids;

import com.black_dog20.jetboots.common.compat.refinedstorage.RefinedStorageCompat;
import com.refinedmods.refinedstorage.api.network.INetwork;
import com.refinedmods.refinedstorage.api.network.grid.GridType;
import com.refinedmods.refinedstorage.api.network.grid.ICraftingGridListener;
import com.refinedmods.refinedstorage.api.network.security.Permission;
import com.refinedmods.refinedstorage.api.util.Action;
import com.refinedmods.refinedstorage.api.util.IStackList;
import com.refinedmods.refinedstorage.blockentity.grid.WirelessGrid;
import com.refinedmods.refinedstorage.inventory.player.PlayerSlot;
import com.refinedmods.refinedstorage.util.StackUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.Set;

public class WirelessCraftingGrid extends WirelessGrid {

    private final MinecraftServer server;
    private final Level world;
    private Set<ICraftingGridListener> listeners = new HashSet<>();

    private AbstractContainerMenu craftingContainer = new AbstractContainerMenu(null, 0) {
        @Override
        public boolean stillValid(Player player) {
            return false;
        }

        @Override
        public void slotsChanged(Container inventory) {
            if (server != null) {
                onCraftingMatrixChanged();
            }
        }
    };
    private CraftingRecipe currentRecipe;
    private CraftingContainer matrix = new CraftingContainer(craftingContainer, 3, 3);
    private ResultContainer result = new ResultContainer();

    public WirelessCraftingGrid(ItemStack stack, Level world, MinecraftServer server, PlayerSlot slot) {
        super(stack, server, slot);

        this.server = server;
        this.world = world;

        if (stack.hasTag()) {
            StackUtils.readItems(matrix, 1, stack.getTag());
        }
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("gui.refinedstorage.crafting_grid");
    }

    @Override
    public GridType getGridType() {
        return GridType.CRAFTING;
    }

    @Override
    public CraftingContainer getCraftingMatrix() {
        return matrix;
    }

    @Override
    public ResultContainer getCraftingResult() {
        return result;
    }

    @Override
    public void onCraftingMatrixChanged() {
        if (currentRecipe == null || !currentRecipe.matches(matrix, world)) {
            currentRecipe = world.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, matrix, world).orElse(null);
        }

        if (currentRecipe == null) {
            result.setItem(0, ItemStack.EMPTY);
        } else {
            result.setItem(0, currentRecipe.assemble(matrix));
        }

        listeners.forEach(ICraftingGridListener::onCraftingMatrixChanged);

        if (!getStack().hasTag()) {
            getStack().setTag(new CompoundTag());
        }

        StackUtils.writeItems(matrix, 1, getStack().getTag());
    }

    @Override
    public void onCrafted(Player player, IStackList<ItemStack> availableItems, IStackList<ItemStack> usedItems) {
        RefinedStorageCompat.RSAPI.getCraftingGridBehavior().onCrafted(this, currentRecipe, player, availableItems, usedItems);

        INetwork network = getNetwork();

        if (network != null) {
            network.getNetworkItemManager().drainEnergy(player, 1);
        }
    }

    @Override
    public void onClear(Player player) {
        INetwork network = getNetwork();

        if (network != null && network.getSecurityManager().hasPermission(Permission.INSERT, player)) {
            for (int i = 0; i < matrix.getContainerSize(); ++i) {
                ItemStack slot = matrix.getItem(i);

                if (!slot.isEmpty()) {
                    matrix.setItem(i, network.insertItem(slot, slot.getCount(), Action.PERFORM));

                    network.getItemStorageTracker().changed(player, slot.copy());
                }
            }

            network.getNetworkItemManager().drainEnergy(player, 10);
        }
    }

    @Override
    public void onCraftedShift(Player player) {
        RefinedStorageCompat.RSAPI.getCraftingGridBehavior().onCraftedShift(this, player);
    }

    @Override
    public void onRecipeTransfer(Player player, ItemStack[][] recipe) {
        RefinedStorageCompat.RSAPI.getCraftingGridBehavior().onRecipeTransfer(this, player, recipe);
    }

    @Override
    public void addCraftingListener(ICraftingGridListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeCraftingListener(ICraftingGridListener listener) {
        listeners.remove(listener);
    }
}
