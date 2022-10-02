package com.black_dog20.jetboots.common.compat.refinedstorage.util;

import com.black_dog20.bml.utils.dimension.DimensionUtil;
import com.black_dog20.bml.utils.item.NBTUtil;
import com.black_dog20.bml.utils.math.Pos3D;
import com.black_dog20.jetboots.common.compat.refinedstorage.grids.WirelessCraftingUpgradeGridFactory;
import com.refinedmods.refinedstorage.RS;
import com.refinedmods.refinedstorage.api.network.INetwork;
import com.refinedmods.refinedstorage.api.network.INetworkNodeGraphEntry;
import com.refinedmods.refinedstorage.api.network.IWirelessTransmitter;
import com.refinedmods.refinedstorage.api.network.node.INetworkNode;
import com.refinedmods.refinedstorage.api.network.security.Permission;
import com.refinedmods.refinedstorage.apiimpl.API;
import com.refinedmods.refinedstorage.inventory.player.PlayerSlot;
import com.refinedmods.refinedstorage.util.LevelUtils;
import com.refinedmods.refinedstorage.util.NetworkUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import static com.black_dog20.jetboots.common.compat.refinedstorage.util.RefinedStorageNBTTags.*;
import static com.black_dog20.jetboots.common.util.TranslationHelper.Compat.*;

public class RefinedStorageCompatUtil {

    public static void openNetwork(MinecraftServer server, ItemStack helmet, Player player) {
        ResourceKey<Level> dimension = getDimension(helmet);
        if(!isValid(helmet)) {
            player.sendSystemMessage(NOT_LINKED_TO_NETWORK.get());
            return;
        }

        if (isValid(helmet) && dimension != null && server.getLevel(dimension) != null){
            Level nodeWorld = server.getLevel(dimension);
            INetwork network = NetworkUtils.getNetworkFromNode(NetworkUtils.getNodeFromBlockEntity(nodeWorld.getBlockEntity(new BlockPos(getX(helmet), getY(helmet), getZ(helmet)))));

            if (network != null) {
                if (!network.getSecurityManager().hasPermission(Permission.MODIFY, player)) {
                    LevelUtils.sendNoPermissionMessage(player);
                    return;
                }

                boolean inRange = network.getNodeGraph().all().stream()
                        .map(INetworkNodeGraphEntry::getNode)
                        .filter(IWirelessTransmitter.class::isInstance)
                        .anyMatch(n ->sameDimensionAndInRange(n, network, helmet, player));

                if (!inRange) {
                    player.sendSystemMessage(RS_OUT_OF_RANGE.get());
                    return;
                }

                API.instance().getGridManager().openGrid(WirelessCraftingUpgradeGridFactory.ID, (ServerPlayer) player, helmet, new PlayerSlot(-1));
                return;
            }
        }

        player.sendSystemMessage(RS_NETWORK_NOT_FOUND.get());
    }

    private static boolean sameDimensionAndInRange(INetworkNode node, INetwork network, ItemStack helmet, Player player) {
        if (!(node instanceof IWirelessTransmitter))
            return false;
        if (!node.isActive() || !network.canRun())
            return false;
        IWirelessTransmitter transmitter = (IWirelessTransmitter) node;

        if (transmitter.getDimension() != DimensionUtil.getDimension(player.getCommandSenderWorld()))
            return false;

        Vec3 pos = player.position();
        Pos3D transmitterPos = new Pos3D(transmitter.getOrigin());

        int extraRange = getRange(helmet);

        return transmitterPos.distance(pos) < (transmitter.getRange() + extraRange);
    }

    public static int getRange(ItemStack helmet) {
        int baseRange = RS.SERVER_CONFIG.getWirelessTransmitter().getBaseRange();
        int rangePerUpgrade = RS.SERVER_CONFIG.getWirelessTransmitter().getRangePerUpgrade();
        int upgradeCount = Math.min(NBTUtil.getInt(helmet, TAG_WIRELESS_RANGE_UPGRADE), 4);

        return baseRange + (upgradeCount * rangePerUpgrade);
    }

    public static boolean isValid(ItemStack stack) {
        CompoundTag compoundNBT = stack.getOrCreateTag();
        return compoundNBT.contains(TAG_NODE_X)
                && compoundNBT.contains(TAG_NODE_Y)
                && compoundNBT.contains(TAG_NODE_Z)
                && compoundNBT.contains(TAG_NODE_DIM);
    }

    public static ResourceKey<Level> getDimension(ItemStack stack) {
        CompoundTag compoundNBT = stack.getOrCreateTag();
        if (compoundNBT.contains(TAG_NODE_DIM)) {
            ResourceLocation name = ResourceLocation.tryParse(compoundNBT.getString(TAG_NODE_DIM));
            if (name != null) {
                return ResourceKey.create(Registry.DIMENSION_REGISTRY, name);
            }
        }

        return null;
    }

    public static int getX(ItemStack stack) {
        return NBTUtil.getInt(stack, TAG_NODE_X);
    }

    public static int getY(ItemStack stack) {
        return NBTUtil.getInt(stack, TAG_NODE_Y);
    }

    public static int getZ(ItemStack stack) {
        return NBTUtil.getInt(stack, TAG_NODE_Z);
    }
}
