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
import com.refinedmods.refinedstorage.util.NetworkUtils;
import com.refinedmods.refinedstorage.util.WorldUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import static com.black_dog20.jetboots.common.compat.refinedstorage.util.RefinedStorageNBTTags.*;
import static com.black_dog20.jetboots.common.util.TranslationHelper.Compat.*;

public class RefinedStorageCompatUtil {

    public static void openNetwork(MinecraftServer server, ItemStack helmet, PlayerEntity player) {
        RegistryKey<World> dimension = getDimension(helmet);
        if(!isValid(helmet)) {
            player.sendMessage(NOT_LINKED_TO_NETWORK.get(), player.getUniqueID());
            return;
        }

        if (isValid(helmet) && dimension != null && server.getWorld(dimension) != null){
            World nodeWorld = server.getWorld(dimension);
            INetwork network = NetworkUtils.getNetworkFromNode(NetworkUtils.getNodeFromTile(nodeWorld.getTileEntity(new BlockPos(getX(helmet), getY(helmet), getZ(helmet)))));

            if (network != null) {
                if (!network.getSecurityManager().hasPermission(Permission.MODIFY, player)) {
                    WorldUtils.sendNoPermissionMessage(player);
                    return;
                }

                boolean inRange = network.getNodeGraph().all().stream()
                        .map(INetworkNodeGraphEntry::getNode)
                        .filter(IWirelessTransmitter.class::isInstance)
                        .anyMatch(n ->sameDimensionAndInRange(n, network, helmet, player));

                if (!inRange) {
                    player.sendMessage(RS_OUT_OF_RANGE.get(), player.getUniqueID());
                    return;
                }

                API.instance().getGridManager().openGrid(WirelessCraftingUpgradeGridFactory.ID, (ServerPlayerEntity) player, helmet, -1);
                return;
            }
        }

        player.sendMessage(RS_NETWORK_NOT_FOUND.get(), player.getUniqueID());
    }

    private static boolean sameDimensionAndInRange(INetworkNode node, INetwork network, ItemStack helmet, PlayerEntity player) {
        if (!(node instanceof IWirelessTransmitter))
            return false;
        if (!node.isActive() || !network.canRun())
            return false;
        IWirelessTransmitter transmitter = (IWirelessTransmitter) node;

        if (transmitter.getDimension() != DimensionUtil.getDimension(player.getEntityWorld()))
            return false;

        Vector3d pos = player.getPositionVec();
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
        CompoundNBT compoundNBT = stack.getOrCreateTag();
        return compoundNBT.contains(TAG_NODE_X)
                && compoundNBT.contains(TAG_NODE_Y)
                && compoundNBT.contains(TAG_NODE_Z)
                && compoundNBT.contains(TAG_NODE_DIM);
    }

    public static RegistryKey<World> getDimension(ItemStack stack) {
        CompoundNBT compoundNBT = stack.getOrCreateTag();
        if (compoundNBT.contains(TAG_NODE_DIM)) {
            ResourceLocation name = ResourceLocation.tryCreate(compoundNBT.getString(TAG_NODE_DIM));
            if (name != null) {
                return RegistryKey.func_240903_a_(Registry.WORLD_KEY, name);
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
