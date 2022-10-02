package com.black_dog20.jetboots.common.compat.refinedstorage.events;

import com.black_dog20.bml.utils.dimension.DimensionUtil;
import com.black_dog20.bml.utils.item.NBTItemBuilder;
import com.black_dog20.bml.utils.item.NBTUtil;
import com.black_dog20.jetboots.common.items.equipment.GuardianHelmetItem;
import com.refinedmods.refinedstorage.api.network.INetwork;
import com.refinedmods.refinedstorage.util.NetworkUtils;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.black_dog20.jetboots.common.compat.refinedstorage.util.RefinedStorageNBTTags.*;
import static com.black_dog20.jetboots.common.util.TranslationHelper.Compat.NETWORK_LINKED;

public class RSCompatEventHandler {

    @SubscribeEvent
    public void onItemUse(PlayerInteractEvent.RightClickBlock event) {
        if (event.getItemStack().getItem() instanceof GuardianHelmetItem) {
            ItemStack stack = event.getItemStack();
            if (!NBTUtil.getBoolean(stack, TAG_HAS_WIRELESS_CRAFTING_UPGRADE)) {
                event.setCancellationResult(InteractionResult.PASS);
                return;
            }

            INetwork network = NetworkUtils.getNetworkFromNode(NetworkUtils.getNodeFromBlockEntity(event.getLevel().getBlockEntity(event.getPos())));
            if (network != null) {
                if (!event.getLevel().isClientSide) {
                    NBTItemBuilder.init(stack)
                            .addTag(TAG_NODE_X, network.getPosition().getX())
                            .addTag(TAG_NODE_Y, network.getPosition().getY())
                            .addTag(TAG_NODE_Z, network.getPosition().getZ())
                            .addTag(TAG_NODE_DIM, DimensionUtil.getDimensionResourceLocation(event.getLevel()).toString());
                    event.getEntity().sendSystemMessage(NETWORK_LINKED.get());
                }
                event.setCancellationResult(InteractionResult.CONSUME);
                event.setCanceled(true);
                return;
            }
            event.setCancellationResult(InteractionResult.PASS);
        }
    }
}
